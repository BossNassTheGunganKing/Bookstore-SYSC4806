package codelook.jpa;

import codelook.jpa.objects.AuthorInfo;
import codelook.jpa.objects.AuthorInfoRepo;
import codelook.jpa.objects.BookInfo;
import codelook.jpa.objects.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepo bookRepo, AuthorInfoRepo authorInfoRepo) {
        return (args) -> {
            AuthorInfo authorInfo = new AuthorInfo("George", "just a simple author");
            // saving a few books
            BookInfo bookInfo1 = new BookInfo("1984", List.of(authorInfo), "111-111-111", "Corruption", "People", 222, "Dystopian");
            BookInfo bookInfo2 = new BookInfo("MiceMen", List.of(authorInfo), "222-222-222", "Sad", "Mice", 333, "Coming of Age");
            BookInfo bookInfo3 = new BookInfo("Twilight", List.of(authorInfo), "333-333-333", "Love", "PeopleParty", 444, "Vampires");

            authorInfoRepo.save(authorInfo);

            bookRepo.save(bookInfo1);
            bookRepo.save(bookInfo2);
            bookRepo.save(bookInfo3);


        };
    }
}
