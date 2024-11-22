package codelook.jpa;

import codelook.jpa.model.*;
import codelook.jpa.request.UserRegistrationRequest;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class to contain reusable example objects for testing to reduce code duplication
 */
public class StaticData {
    public static final AuthorInfo authorInfo1 = new AuthorInfo("George", "just a simple author");
    public static final AuthorInfo authorInfo2 = new AuthorInfo("Geoff", "just a simpleton");

    public static final UserInfo somePublisher = new UserInfo("Penguin LTD", "pubpwd","publisheremail@email.com", UserRole.PUBLISHER);
    public static final UserInfo anotherPublisher = new UserInfo("PeopleParty", "pubpwd2","publisheremail2@email.com", UserRole.PUBLISHER);


    public static final UserInfo someUser = new UserInfo("someGuy", "userpwd","useremail@email.com", UserRole.DEFAULT);

    public static final BookInfo bookInfo1 = new BookInfo("1984", List.of(authorInfo1), "Corruption", somePublisher, 222, "Dystopian");
    public static final BookInfo bookInfo2 = new BookInfo("Of Mice and Men", List.of(authorInfo1, authorInfo2), "Sad", somePublisher, 333, "Coming of Age");
    public static final BookInfo bookInfo3 = new BookInfo("Twilight", List.of(authorInfo1), "Love", anotherPublisher, 444, "Vampires");
    public static final List<BookInfo> manyBooks = new ArrayList<>(Arrays.asList(
            new BookInfo("IT", List.of(authorInfo2), "Scary Clown", somePublisher, 120, "Horror"),
            new BookInfo("GooseBumps", List.of(authorInfo2), "Scary stories to read in the dark", somePublisher, 300, "Horror"),
            new BookInfo("Marvel's Ironman", List.of(authorInfo1), "Ironman's origins", somePublisher, 50, "Action"),
            new BookInfo("Mission is Possible", List.of(authorInfo1), "Tom cruises", somePublisher, 10, "Action"),
            new BookInfo("Blade 3", List.of(authorInfo1), "Vampire Killer", somePublisher, 140, "Vampires"),
            new BookInfo("Blade Jogger", List.of(authorInfo1), "Bad robots on the loose", somePublisher, 250, "Dystopian"),
            new BookInfo("Terminate", List.of(authorInfo1), "Bad Robots from future", somePublisher, 65, "Dystopian")

    ));

    public static final ListingInfo listing1 =  new ListingInfo("1984, Hardcover", "111-111-111" ,"Listing for paperback copies of '1984'", new Date(1949,6,8), ListingInfo.Format.Hardcover, new BigDecimal("19.99"), bookInfo1, 10);
    public static final ListingInfo listing2 =  new ListingInfo("Of Mice and Men, Ebook", "123-456-789" ,"Listing for ebook licences of 'Of Mice and Men'", new Date(1949,6,8), ListingInfo.Format.Ebook, new BigDecimal("19.99"), bookInfo2, 8);
    public static final ListingInfo listing3 = new ListingInfo("Twilight", "333-333-333" ,  "Listing for paperback copies of 'Twilight'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), bookInfo3,30);
    public static final List<ListingInfo> manyListings = new ArrayList<>(Arrays.asList(
            new ListingInfo("IT", "333-333-333" ,  "Listing for paperback copies of 'IT'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(0),30),
            new ListingInfo("GooseBumps", "333-333-333" ,  "Listing for paperback copies of 'GooseBumps'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(1),30),
            new ListingInfo("Marvel's Ironman", "333-333-333" ,  "Listing for paperback copies of 'Marvel's Ironman'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(2),30),
            new ListingInfo("Mission is Possible", "333-333-333" ,  "Listing for paperback copies of 'Mission is Possible'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(3),30),
            new ListingInfo("Blade 3", "333-333-333" ,  "Listing for paperback copies of 'Blade 3'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(4),30),
            new ListingInfo("Blade Jogger", "333-333-333" ,  "Listing for paperback copies of 'Blade Jogger'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(5),30),
            new ListingInfo("Terminate", "333-333-333" ,  "Listing for paperback copies of 'Terminate'", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), manyBooks.get(6),30)
    ));

    public static final OrderItem orderItem1 = new OrderItem(listing1, 1);
    public static final OrderItem orderItem2 = new OrderItem(listing2, 2);

    public static final OrderInfo orderInfo1 = new OrderInfo(OrderStatus.IN_CART, List.of(orderItem1));
    public static final OrderInfo orderInfo2 = new OrderInfo(OrderStatus.PENDING, List.of(orderItem1,orderItem2));

    public static final UserRegistrationRequest validRegistrationRequest = new UserRegistrationRequest("newUser", "validPassword123", "email@example.com", UserRole.ADMIN);
    public static final UserRegistrationRequest allBlankRegistrationRequest = new UserRegistrationRequest("", "", "", UserRole.DEFAULT);
    public static final UserRegistrationRequest allNullRegistrationRequest = new UserRegistrationRequest(null, null, null,UserRole.DEFAULT);
    public static final UserRegistrationRequest badPasswordAndEmailRegistrationRequest = new UserRegistrationRequest("newUser", "invalid", "notanemail",UserRole.DEFAULT);

    public static final List<AvailableGenres> availableGenresDefaultList = new ArrayList<AvailableGenres>(Arrays.asList(
            new AvailableGenres("Horror"),
            new AvailableGenres("Action"),
            new AvailableGenres("Non-fiction"),
            new AvailableGenres("Dystopian"),
            new AvailableGenres("Coming of Age"),
            new AvailableGenres("Vampires")));
}