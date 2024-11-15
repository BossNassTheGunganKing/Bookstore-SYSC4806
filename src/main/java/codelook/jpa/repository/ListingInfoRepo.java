package codelook.jpa.repository;

import codelook.jpa.model.ListingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingInfoRepo extends JpaRepository<ListingInfo, Long> {
    ListingInfo findListingInfoById(Long id);

}
