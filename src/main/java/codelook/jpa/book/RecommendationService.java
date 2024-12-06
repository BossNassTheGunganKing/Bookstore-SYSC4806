package codelook.jpa.book;

import codelook.jpa.order.OrderInfoRepo;
import codelook.jpa.order.OrderItem;
import codelook.jpa.user.UserInfo;
import codelook.jpa.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.text.StringTokenizer;
import org.apache.commons.text.similarity.*;


@Service
public class RecommendationService{

    private final UserService userService;
    private final OrderInfoRepo orderInfoRepo;
    private final BookInfoRepo bookInfoRepo;
    private final ListingInfoRepo listingInfoRepo;

    @Autowired
    public RecommendationService(UserService userService, OrderInfoRepo orderInfoRepo, BookInfoRepo bookInfoRepo, ListingInfoRepo listingInfoRepo) {
        this.userService = userService;
        this.orderInfoRepo = orderInfoRepo;
        this.bookInfoRepo = bookInfoRepo;
        this.listingInfoRepo = listingInfoRepo;
    }

    public List<BookInfo> getRecommendedBooks(int maxRecommendations){
        List<AggregateBookDetails> aggregateBookDetails = getAggregatedPurchasedBookInformation();
        if(aggregateBookDetails.isEmpty()){
            return Collections.emptyList();
        }
        List<BookInfo> notPurchasedBooks = bookInfoRepo.findAllByIdNotIn(aggregateBookDetails.stream().map(AggregateBookDetails::book).map(BookInfo::getId).toList());
        List<ListingInfo> availableNotPurchasedListings = listingInfoRepo.findAllByBookInAndRemainingCopiesNot(notPurchasedBooks, 0);
        if(availableNotPurchasedListings.isEmpty()){
            return Collections.emptyList();
        }
        // lowest price distinct by book
        List<AggregateBookDetails> consideredBooks = availableNotPurchasedListings.stream()
                .map(this::getAggregateBookDetailsForListing)
                .collect(Collectors.groupingBy(AggregateBookDetails::book))
                .values()
                .stream()
                .map(bookList -> bookList.stream()
                        .min(Comparator.comparing(AggregateBookDetails::actualPrice))
                        .orElseThrow())
                .toList();

        Map<AggregateBookDetails, Double> bookScores = new HashMap<>();
        // average similarity
        for (AggregateBookDetails book2 : consideredBooks) {
            double totalSimilarity = 0.0;
            for (AggregateBookDetails book1 : aggregateBookDetails) {
                totalSimilarity += calculateBookSimilarity(book1, book2);
            }
            // Average similarity
            double averageSimilarity = totalSimilarity / aggregateBookDetails.size();
            bookScores.put(book2, averageSimilarity);
        }

        // Sort books by average similarity score in descending order and return the list
        var sortedBooksByScore = bookScores.entrySet()
                .stream()
                .sorted(Map.Entry.<AggregateBookDetails, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .map(AggregateBookDetails::book)
                .toList();

        // get best ones
        if(sortedBooksByScore.size() > maxRecommendations){
            sortedBooksByScore = sortedBooksByScore.subList(0, maxRecommendations);
        }
        return sortedBooksByScore;
    }

    private List<AggregateBookDetails> getAggregatedPurchasedBookInformation(){
        UserInfo forUser = userService.getCurrentUser();
        return orderInfoRepo.findByUser(forUser).stream()
                .flatMap(order -> order.getItems().stream())
                .map(this::getAggregateBookDetailsForOrderItem).toList();
    }

    private AggregateBookDetails getAggregateBookDetailsForListing(ListingInfo listing){
        BookInfo book = listing.getBook();
        return new AggregateBookDetails(
                book,
                book.getName(),
                book.getGenre(),
                book.getPublisher().getUsername(),
                book.getDescription(),
                book.getPageCount(),
                book.getAuthorship(),
                listing.getDiscountedPrice(),
                listing.getDatePublished()
        );
    }

    private AggregateBookDetails getAggregateBookDetailsForOrderItem(OrderItem item){
        ListingInfo listing = item.getListing();
        BookInfo book = listing.getBook();
        return new AggregateBookDetails(
                book,
                book.getName(),
                book.getGenre(),
                book.getPublisher().getUsername(),
                book.getDescription(),
                book.getPageCount(),
                book.getAuthorship(),
                item.getPriceAtPurchase(),
                listing.getDatePublished()
        );
    }

    // probably needs tuning
    private double calculateBookSimilarity(AggregateBookDetails aggregateBookDetails1, AggregateBookDetails aggregateBookDetails2){
        double authorshipSimilarity = calculateAuthorshipSimilarity(aggregateBookDetails1.authors, aggregateBookDetails2.authors);
        double priceSimilarity = calculateSimpleNumericSimilarity(aggregateBookDetails1.actualPrice.doubleValue(),aggregateBookDetails2.actualPrice.doubleValue());
        double nameSimilarity = calculateJaccardSimilarity(aggregateBookDetails1.bookName,aggregateBookDetails2.bookName);
        double publisherSimilarity = Objects.equals(aggregateBookDetails1.publisherUsername, aggregateBookDetails2.publisherUsername) ? 1 : 0;
        double pageCountSimilarity = calculateSimpleNumericSimilarity(aggregateBookDetails1.pageCount, aggregateBookDetails2.pageCount);
        double genreSimilarity = calculateJaccardSimilarity(aggregateBookDetails1.genre, aggregateBookDetails2.genre);
        double descriptionSimilarity = calculateCosineSimilarity(aggregateBookDetails1.description, aggregateBookDetails2.description);
        // why not lol
        double datePublishedSimilarity = calculateSimpleNumericSimilarity(aggregateBookDetails1.datePublished.getYear(), aggregateBookDetails2.datePublished.getYear());
        return genreSimilarity * 0.2
                + descriptionSimilarity * 0.2
                + authorshipSimilarity * 0.175
                + nameSimilarity * 0.15
                + pageCountSimilarity * 0.075
                + publisherSimilarity * 0.075
                + priceSimilarity * 0.075
                + datePublishedSimilarity * 0.05;
    }

    // calculate average author similarity between books
    private double calculateAuthorshipSimilarity(List<AuthorInfo> authors1, List<AuthorInfo> authors2) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();

        double totalSimilarity = 0.0;
        int comparisons = 0;

        for (AuthorInfo author1 : authors1) {
            for (AuthorInfo author2 : authors2) {
                totalSimilarity += calculateAuthorSimilarity(author1, author2);
                comparisons++;
            }
        }

        // return average similarity or 0 if no comparisons
        return comparisons == 0 ? 0.0 : totalSimilarity / comparisons;
    }

    private double calculateAuthorSimilarity(AuthorInfo author1, AuthorInfo author2) {
        if(author1 == author2){
            return 1;
        }
        return calculateCosineSimilarity(author1.getBio(), author2.getBio());
    }

    private double calculateCosineSimilarity(String a, String b) {
        return new CosineSimilarity().cosineSimilarity(tokenizeText(a), tokenizeText(b));
    }

    private double calculateSimpleNumericSimilarity(double number1, double number2) {
        double difference = Math.abs(number1-number2);
        return 1 - difference/Math.max(number1, number2);
    }

    private double calculateJaccardSimilarity(String a, String b) {
        return new JaccardSimilarity().apply(a, b);
    }

    private Map<CharSequence, Integer> tokenizeText(String text) {
        Map<CharSequence, Integer> frequencyMap = new HashMap<>();
        if (text == null || text.isEmpty()) {
            return frequencyMap;
        }

        StringTokenizer tokenizer = new StringTokenizer(text.toLowerCase(), " \\W+");
        while (tokenizer.hasNext()) {
            String token = tokenizer.next();
            frequencyMap.put(token, frequencyMap.getOrDefault(token, 0) + 1);
        }
        return frequencyMap;
    }

    // record so it's easier to work with
    public record AggregateBookDetails(
            BookInfo book,
            String bookName,
            String genre,
            String publisherUsername,
            String description,
            int pageCount,
            List<AuthorInfo> authors,
            BigDecimal actualPrice,
            Date datePublished
    ) {}
}