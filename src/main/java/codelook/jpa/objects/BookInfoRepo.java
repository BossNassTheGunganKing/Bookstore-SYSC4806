package codelook.jpa.objects;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInfoRepo extends CrudRepository<BookInfo, Integer> {

}
