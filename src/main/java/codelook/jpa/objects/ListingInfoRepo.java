package codelook.jpa.objects;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingInfoRepo extends CrudRepository<ListingInfo, Integer> {
}
