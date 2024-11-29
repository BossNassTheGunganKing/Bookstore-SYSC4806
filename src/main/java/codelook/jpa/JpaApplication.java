package codelook.jpa;

import codelook.jpa.user.model.*;
import codelook.jpa.book.model.*;
import codelook.jpa.order.model.*;

import codelook.jpa.book.repository.*;
import codelook.jpa.order.repository.*;
import codelook.jpa.user.repository.*;
import codelook.jpa.user.request.UserRegistrationRequest;
import codelook.jpa.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "codelook.jpa")
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookInfoRepo bookInfoRepo, AuthorInfoRepo authorInfoRepo, ListingInfoRepo listingInfoRepo, UserInfoRepo userInfoRepo, OrderInfoRepo orderInfoRepo, OrderItemRepo orderItemRepo, UserService userService, AvailableGenresRepo availableGenresRepo) {
        return (args) -> {
            AuthorInfo authorInfo1 = StaticData.authorInfo1;
            AuthorInfo authorInfo2 = StaticData.authorInfo2;


            userService.registerUser(new UserRegistrationRequest("admin","Hello123","test@email.com", UserRole.ADMIN));
            UserInfo publisher1 = userService.registerUser(new UserRegistrationRequest(StaticData.somePublisher.getUsername(),StaticData.somePublisher.getPassword(),StaticData.somePublisher.getEmail(), StaticData.somePublisher.getRole()));
            UserInfo publisher2 = userService.registerUser(new UserRegistrationRequest(StaticData.anotherPublisher.getUsername(),StaticData.anotherPublisher.getPassword(),StaticData.anotherPublisher.getEmail(), StaticData.anotherPublisher.getRole()));

            UserInfo user1 = userService.registerUser(new UserRegistrationRequest(StaticData.someUser.getUsername(),StaticData.someUser.getPassword(),StaticData.someUser.getEmail(), StaticData.someUser.getRole()));
            UserInfo user2 = userService.registerUser(new UserRegistrationRequest(StaticData.someUser2.getUsername(),StaticData.someUser2.getPassword(),StaticData.someUser2.getEmail(), StaticData.someUser2.getRole()));

            // saving a few books
            BookInfo bookInfo1 = StaticData.bookInfo1;
            BookInfo bookInfo2 = StaticData.bookInfo2;
            BookInfo bookInfo3 = StaticData.bookInfo3;

            ListingInfo listingInfo1 = StaticData.listing1;
            ListingInfo listingInfo2 = StaticData.listing2;
            ListingInfo listingInfo3 = StaticData.listing3;

            availableGenresRepo.saveAll(StaticData.availableGenresDefaultList);

            authorInfoRepo.save(authorInfo1);
            authorInfoRepo.save(authorInfo2);

            userInfoRepo.save(user1);
            userInfoRepo.save(user2);

            bookInfo1.setPublisher(publisher1);
            bookInfo2.setPublisher(publisher1);
            bookInfo3.setPublisher(publisher2);

            bookInfoRepo.save(bookInfo1);
            bookInfoRepo.save(bookInfo2);
            bookInfoRepo.save(bookInfo3);

            listingInfoRepo.save(listingInfo1);
            listingInfoRepo.save(listingInfo2);
            listingInfoRepo.save(listingInfo3);

            for(BookInfo bookInfo : StaticData.manyBooks) {
                bookInfo.setPublisher(publisher1);
                bookInfoRepo.save(bookInfo);
            }
            //bookInfoRepo.saveAll(StaticData.manyBooks);
            listingInfoRepo.saveAll(StaticData.manyListings);


        };
    }
}
