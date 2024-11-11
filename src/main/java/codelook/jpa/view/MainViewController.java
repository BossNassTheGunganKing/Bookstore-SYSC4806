package codelook.jpa.view;

import codelook.jpa.repository.AuthorInfoRepo;
import codelook.jpa.repository.BookInfoRepo;
import codelook.jpa.repository.ListingInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

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
}
