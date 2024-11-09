package codelook.jpa.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired BookRepo bookRepo;

    @GetMapping("/allBooks")
    public String AllBooks(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "allBooks";
    }
}
