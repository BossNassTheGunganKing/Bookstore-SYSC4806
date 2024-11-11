package codelook.jpa.view;

import codelook.jpa.repository.AuthorInfoRepo;
import codelook.jpa.repository.BookInfoRepo;
import codelook.jpa.repository.ListingInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
                             @RequestParam Date DatePublished,
                             @RequestParam ListingInfo.Format format,
                             @RequestParam BigDecimal OriginalPrice,
                             @RequestParam BookInfo book,
                             @RequestParam int CopiesRemaining) {
        ListingInfo listingInfo = new ListingInfo(name, ISBN, description, DatePublished, format, OriginalPrice, book, CopiesRemaining);
        listingInfoRepo.save(listingInfo);
        return "redirect:/allBooks";
    }

    // New Book Page
    @GetMapping("/newBook")
    public String newBookPage(Model model) {
        model.addAttribute("authors", authorInfoRepo.findAll());
        return "newBook";
    }

    @PostMapping("/books")
    public String addBook(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam String publisher,
                          @RequestParam int pageCount,
                          @RequestParam String genre,
                          @RequestParam List<Integer> authorIds) {  // List of author IDs from the form

        // Fetch authors based on the list of IDs provided in the form
        List<AuthorInfo> authors = authorInfoRepo.findAllById(authorIds);

        // Create and save the BookInfo with the selected authors
        BookInfo book = new BookInfo(name, authors, description, publisher, pageCount, genre);
        bookInfoRepo.save(book);

        return "redirect:/allBooks";  // Redirect to the all books page after saving
    }


}
