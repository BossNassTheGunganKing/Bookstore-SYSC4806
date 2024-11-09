package codelook.jpa;

import codelook.jpa.objects.Book;
import codelook.jpa.objects.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepo bookRepo) {
        return (args) -> {
            // saving a few books
            Book book1 = new Book("1984", "George", "111-111-111", "Corruption", "People", 222, 10);
            Book book2 = new Book("MiceMen", "Peter", "222-222-222", "Sad", "Mice", 333, 100);
            Book book3 = new Book("Twilight", "Bob", "333-333-333", "Love", "PeopleParty", 444, 5);

            bookRepo.save(book1);
            bookRepo.save(book2);
            bookRepo.save(book3);


        };
    }
}
