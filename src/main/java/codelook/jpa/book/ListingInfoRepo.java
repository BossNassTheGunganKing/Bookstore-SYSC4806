package codelook.jpa.book;

import codelook.jpa.book.ListingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingInfoRepo extends JpaRepository<ListingInfo, Long> {
    List<ListingInfo> findByNameContainingIgnoreCase(String keyword);

    ListingInfo findListingInfoById(Long id);

}
