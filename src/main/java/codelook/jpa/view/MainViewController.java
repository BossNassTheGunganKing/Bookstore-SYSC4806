package codelook.jpa.view;

import codelook.jpa.StaticData;
import codelook.jpa.model.*;

import codelook.jpa.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Controller
public class MainViewController {
    @Autowired
    ListingInfoRepo listingInfoRepo;
    @Autowired
    BookInfoRepo bookInfoRepo;
    @Autowired
    AuthorInfoRepo authorInfoRepo;
    @Autowired
    UserInfoRepo userInfoRepo;
    @Autowired
    AvailableGenresRepo availableGenresRepo;

    @GetMapping("/allBooks")
    public String AllBooks(Model model) {
        model.addAttribute("books", bookInfoRepo.findAll());
        return "allBooks";
    }

    // Add New Author Page
    @GetMapping("/authors")
    public String authorsPage(Model model) {
        model.addAttribute("authors", authorInfoRepo.findAll());
        return "author";
    }

    @PostMapping("/authors")
    public String addAuthor(@RequestParam String name, @RequestParam String bio) {
        System.out.println("Name: " + name);
        System.out.println("Bio: " + bio);
        AuthorInfo author = new AuthorInfo();
        author.setName(name);
        author.setBio(bio);
        authorInfoRepo.save(author);
        return "redirect:/authors";
    }

    // New Listing Page
    @GetMapping("/newListing")
    public String newListingPage(Model model) {
        model.addAttribute("books", bookInfoRepo.findAll());
        return "newListing";
    }

    @PostMapping("/newListing")
    public String addListing(@RequestParam String name,
                             @RequestParam String ISBN,
                             @RequestParam String description,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date DatePublished,
                             @RequestParam String format,
                             @RequestParam BigDecimal OriginalPrice,
                             @RequestParam Long bookId,  // Use Long for bookId
                             @RequestParam int CopiesRemaining) {

        // Convert the format string to enum
        ListingInfo.Format form;
        switch (format) {
            case "HARDCOVER":
                form = ListingInfo.Format.Hardcover;
                break;
            case "PAPERBACK":
                form = ListingInfo.Format.Paperback;
                break;
            default:
                form = ListingInfo.Format.Ebook;
                break;
        }

        // Fetch the BookInfo entity by ID
        BookInfo book = bookInfoRepo.findById(bookId).orElse(null);
        if (book == null) {
            // Handle case where the book is not found, e.g., return an error or redirect
            return "redirect:/errorPage";
        }

        // Create and save the new ListingInfo
        ListingInfo listingInfo = new ListingInfo(name, ISBN, description, DatePublished, form, OriginalPrice, book, CopiesRemaining);
        listingInfoRepo.save(listingInfo);
        return "redirect:/allListings";
    }

    @GetMapping("/allListings")
    public String allListings(Model model) {
        model.addAttribute("listings", listingInfoRepo.findAll());
        return "allListings";
    }

    @GetMapping("/listing/{id}")
    public String listingPage(@PathVariable Long id, Model model) {
        model.addAttribute("Listing", listingInfoRepo.findListingInfoById(id));
        return "listing";
    }

    // New Book Page
    @GetMapping("/newBook")
    public String newBookPage(Model model) {
        model.addAttribute("authors", authorInfoRepo.findAll());
        return "newBook";
    }

    @PostMapping("/books")
    public String addBook(@RequestParam String name,
                          @RequestParam List<Long> authorIds,
                          @RequestParam String description,
                          @RequestParam String publisher,
                          @RequestParam int pageCount,
                          @RequestParam String genre
                          ) {  // List of author IDs from the form
        UserInfo user = StaticData.somePublisher;
        userInfoRepo.save(user);

        boolean newGenre = true;
        List<AvailableGenres> availableGenres = availableGenresRepo.findAll();
        for (AvailableGenres ag : availableGenres) {
            if(ag.genre.equalsIgnoreCase(genre)){
                newGenre = false;
                break;
            }
        }
        if(newGenre){
            AvailableGenres newGenreType = new AvailableGenres(genre);
            availableGenresRepo.save(newGenreType);
            System.out.println("New Genre Added: " + newGenreType.genre);
        }
        // Fetch authors based on the list of IDs provided in the form
        List<AuthorInfo> authors = authorInfoRepo.findAllById(authorIds);
        // Create and save the BookInfo with the selected authors
        BookInfo book = new BookInfo(name, authors, description, user, pageCount, genre);
        bookInfoRepo.save(book);

        return "redirect:/allBooks";  // Redirect to the all books page after saving
    }

    // Available Genres Pages
    @GetMapping("/allGenres")
    public String allGenresPage(Model model) {
        model.addAttribute("genres", availableGenresRepo.findAll());
        return "allGenres";
    }

    @GetMapping("/genre/{type}")
    public String genrePage(@PathVariable String type, Model model) {
        model.addAttribute("genres", type);
        List<BookInfo> booksToShow = new java.util.ArrayList<>(List.of());

        for (BookInfo b : bookInfoRepo.findAll()) {
            if (b.getGenre().equalsIgnoreCase(type)) {
                booksToShow.add(b);
            }
        }
        model.addAttribute("books", booksToShow);
        return "genre";
    }


}
