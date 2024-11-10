package codelook.jpa.objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingInfoRepo extends JpaRepository<ListingInfo, Integer> {
}
