package codelook.jpa.view;

import codelook.jpa.user.*;
import codelook.jpa.order.*;
import codelook.jpa.book.*;
import codelook.jpa.image.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


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

    private final RestTemplate restTemplate;

    private static String BASE_URL;
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private ImageService imageService;

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
        model.addAttribute("publishers", userInfoRepo.findAllByRole(UserRole.PUBLISHER));
        return "newBook";
    }

    @PostMapping("/books")
    public String addBook(@RequestParam String name,
                          @RequestParam List<Long> authorIds,
                          @RequestParam String description,
                          @RequestParam String publisher,
                          @RequestParam int pageCount,
                          @RequestParam String genre,
                          @RequestParam(required = false, value = "coverImage") MultipartFile coverImage
                          ) {  // List of author IDs from the form
        UserInfo user = userInfoRepo.findByUsername(publisher).orElse(null);
        if (user == null) {
            return "redirect:/errorPage";
        }

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
        String imageName;
        try {
            if(coverImage != null){
                imageName = imageService.saveImage("images", coverImage);
                book.setCoverImage(imageName);
                System.out.println("Cover Image Added: " + imageName);
            }else {
                System.out.println("Couldn't find cover image");
            }
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/errorPage";
        }
        bookInfoRepo.save(book);

        return "redirect:/allBooks";  // Redirect to the all books page after saving
    }

    @GetMapping("/users/view")
    public String usersPage(Model model) {
        model.addAttribute("users", userInfoRepo.findAll());
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest("", "", "", UserRole.DEFAULT));
        return "allUsers";
    }

    @PostMapping("/users/add")
    public String registerUser(@ModelAttribute @Valid UserRegistrationRequest registrationRequest) {
        System.out.println("Registration Request: \n" + registrationRequest.username() + " " + registrationRequest.password() + " " + registrationRequest.role() + " " + registrationRequest.email());
        ResponseEntity<?> response = restTemplate.exchange(
                BASE_URL + "/users",
                HttpMethod.POST,
                new HttpEntity<>(registrationRequest),
                Void.class);
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

    // Available Genres Pages
    @GetMapping("/allAuthors")
    public String allAuthorsPage(Model model) {
        model.addAttribute("authors", authorInfoRepo.findAll());
        return "allAuthors";
    }

    @GetMapping("/author/{name}")
    public String authorsBooksPage(@PathVariable String name, Model model) {
        model.addAttribute("author", name);
        List<BookInfo> booksToShow = new java.util.ArrayList<>(List.of());
        authorInfoRepo.findByName(name);

        for (BookInfo b : bookInfoRepo.findAll()) {
            if (b.getAuthorship().contains(authorInfoRepo.findByName(name).get(0))) {
                booksToShow.add(b);
            }
        }
        model.addAttribute("books", booksToShow);
        return "authorbooks";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        UserInfo currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "account";
    }

    //SearchBar query REQUESTS

    @GetMapping("/search-results")
    public String searchBooks(@RequestParam(defaultValue = "") String keyword, Model model) {

        List<ListingInfo> books = listingInfoRepo.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        if (books.isEmpty() || keyword.isEmpty()) {
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
            userService.updateAccount(currentUser.getId(), username, password, email);

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
