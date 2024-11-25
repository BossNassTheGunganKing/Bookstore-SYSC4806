package codelook.jpa.view;

import codelook.jpa.StaticData;
import codelook.jpa.controller.UserController;
import codelook.jpa.model.*;
import codelook.jpa.model.*;

import codelook.jpa.repository.AuthorInfoRepo;
import codelook.jpa.repository.BookInfoRepo;
import codelook.jpa.repository.*;

import codelook.jpa.repository.ListingInfoRepo;

import codelook.jpa.repository.UserInfoRepo;
import codelook.jpa.request.UserRegistrationRequest;
import codelook.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;


@org.springframework.stereotype.Controller
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
    @Autowired
    OrderInfoRepo orderInfoRepo;
    @Autowired
    OrderItemRepo orderItemRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;



    private final RestTemplate restTemplate;

    private static String BASE_URL;
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;

    public MainViewController(RestTemplate restTemplate, @Value("${api.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        BASE_URL = baseUrl;
    }

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

    @GetMapping("/users/view")
    public String usersPage(Model model) {
        model.addAttribute("users", userInfoRepo.findAll());
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest("", "", "", "DEFAULT"));
        return "allUsers";
    }

    @PostMapping("/users/add")
    public String registerUser(@ModelAttribute UserRegistrationRequest registrationRequest) {
        userController.createUser(registrationRequest);
        return "redirect:/users/view";
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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        UserInfo currentUser = userService.getCurrentUserInfo();
        model.addAttribute("user", currentUser);
        return "account";
    }

    //SearchBar query REQUESTS

    @GetMapping("/search-results")
    public String searchBooks(@RequestParam(defaultValue = "") String keyword, Model model) {

        List<ListingInfo> books = listingInfoRepo.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        if (books.isEmpty()) {
            model.addAttribute("message", "no such results...");
        }

        return "search-results";
    }
//    public String searchResults(@RequestParam(defaultValue = "") String keyword, Model model) {
//        List<ListingInfo> books = listingInfoRepo.findByNameContainingIgnoreCase(keyword);
//        model.addAttribute("books", books);
//        model.addAttribute("keyword", keyword);
//        return "search-results";
//    }



    @GetMapping("/account/edit")
    public String editAccount(Model model) {
        try {
            UserInfo currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
            return "editAccount";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load account details.");
            return "account";
        }
    }

    @PostMapping("/account/edit")
    public String updateAccount(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) {
        try {
            UserInfo currentUser = userService.getCurrentUser();

            // Update the current user's information
            userService.updateUser(currentUser, username, password, email);

            model.addAttribute("success", "Account updated successfully.");
            return "redirect:/account";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update account. " + e.getMessage());
            return "editAccount";
        }
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout"; // Return the logout confirmation page
    }

    @GetMapping("/403")
    public String permissionDenied() {
        return "403"; // This maps to the 403.html template
    }

    @GetMapping("/admin/orders")
    public String viewAllOrders(Model model) {
        List<OrderInfo> orders = orderInfoRepo.findAll();
        model.addAttribute("orders", orders);
        return "allOrders";
    }

}
