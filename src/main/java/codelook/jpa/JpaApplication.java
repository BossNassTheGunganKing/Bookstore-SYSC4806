package codelook.jpa;

import codelook.jpa.objects.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookInfoRepo bookInfoRepo, AuthorInfoRepo authorInfoRepo, ListingInfoRepo listingInfoRepo) {
        return (args) -> {
            AuthorInfo authorInfo = new AuthorInfo("George", "just a simple author");
            // saving a few books
            BookInfo bookInfo1 = new BookInfo("1984", List.of(authorInfo), "Corruption", "People", 222, "Dystopian");
            BookInfo bookInfo2 = new BookInfo("MiceMen", List.of(authorInfo), "Sad", "Mice", 333, "Coming of Age");
            BookInfo bookInfo3 = new BookInfo("Twilight", List.of(authorInfo), "Love", "PeopleParty", 444, "Vampires");

            ListingInfo listingInfo1 = new ListingInfo("1984", "111-111-111" ,"Corruption", new Date(1949,6,8), ListingInfo.Format.Hardcover, new BigDecimal(19.99), bookInfo1, 10);
            ListingInfo listingInfo2 = new ListingInfo("MiceMen", "222-222-222", "Sad", new Date(1989,7,7), ListingInfo.Format.Ebook, new BigDecimal(20.99), bookInfo2,20);
            ListingInfo listingInfo3 = new ListingInfo("Twilight", "333-333-333" ,  "Love", new Date(2000,10,10), ListingInfo.Format.Paperback, new BigDecimal(9.99), bookInfo3,30);


            authorInfoRepo.save(authorInfo);
            bookInfoRepo.save(bookInfo1);
            bookInfoRepo.save(bookInfo2);
            bookInfoRepo.save(bookInfo3);
            listingInfoRepo.save(listingInfo1);
            listingInfoRepo.save(listingInfo2);
            listingInfoRepo.save(listingInfo3);


        };
    }
}
