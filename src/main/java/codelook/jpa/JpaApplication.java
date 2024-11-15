package codelook.jpa;

import codelook.jpa.model.AuthorInfo;
import codelook.jpa.model.BookInfo;
import codelook.jpa.model.ListingInfo;
import codelook.jpa.model.UserInfo;

import codelook.jpa.repository.*;
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
    public CommandLineRunner demo(BookInfoRepo bookInfoRepo, AuthorInfoRepo authorInfoRepo, ListingInfoRepo listingInfoRepo, UserInfoRepo userInfoRepo, OrderInfoRepo orderInfoRepo, OrderItemRepo orderItemRepo) {
        return (args) -> {
            AuthorInfo authorInfo1 = StaticData.authorInfo1;
            AuthorInfo authorInfo2 = StaticData.authorInfo2;


            UserInfo publisher1 = StaticData.somePublisher;
            UserInfo publisher2 = StaticData.anotherPublisher;

            // saving a few books
            BookInfo bookInfo1 = StaticData.bookInfo1;
            BookInfo bookInfo2 = StaticData.bookInfo2;
            BookInfo bookInfo3 = StaticData.bookInfo3;

            ListingInfo listingInfo1 = StaticData.listing1;
            ListingInfo listingInfo2 = StaticData.listing2;
            ListingInfo listingInfo3 = StaticData.listing3;

            authorInfoRepo.save(authorInfo1);
            authorInfoRepo.save(authorInfo2);

            userInfoRepo.save(publisher1);
            userInfoRepo.save(publisher2);

            bookInfoRepo.save(bookInfo1);
            bookInfoRepo.save(bookInfo2);
            bookInfoRepo.save(bookInfo3);

            listingInfoRepo.save(listingInfo1);
            listingInfoRepo.save(listingInfo2);
            listingInfoRepo.save(listingInfo3);


        };
    }
}
