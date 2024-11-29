package codelook.jpa.book.repository;

import codelook.jpa.book.model.ListingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ListingInfoRepo extends JpaRepository<ListingInfo, Long> {
    List<ListingInfo> findByNameContainingIgnoreCase(String keyword);

    ListingInfo findListingInfoById(Long id);

}
