package codelook.jpa.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
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
