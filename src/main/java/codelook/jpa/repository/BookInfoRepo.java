package codelook.jpa.repository;

import codelook.jpa.model.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInfoRepo extends JpaRepository<BookInfo, Long> {

}
