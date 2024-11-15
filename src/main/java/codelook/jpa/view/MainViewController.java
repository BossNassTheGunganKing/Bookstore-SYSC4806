package codelook.jpa.view;

import codelook.jpa.StaticData;
import codelook.jpa.model.AuthorInfo;
import codelook.jpa.model.BookInfo;

import codelook.jpa.model.ListingInfo;
import codelook.jpa.model.UserInfo;
import codelook.jpa.repository.AuthorInfoRepo;
import codelook.jpa.repository.BookInfoRepo;

import codelook.jpa.repository.ListingInfoRepo;

import codelook.jpa.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        // Fetch authors based on the list of IDs provided in the form
        List<AuthorInfo> authors = authorInfoRepo.findAllById(authorIds);
        // Create and save the BookInfo with the selected authors
        BookInfo book = new BookInfo(name, authors, description, user, pageCount, genre);
        bookInfoRepo.save(book);

        return "redirect:/allBooks";  // Redirect to the all books page after saving
    }


}
