package codelook.jpa.objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInfoRepo extends JpaRepository<BookInfo, Integer> {
}
