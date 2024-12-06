package codelook.jpa.book;

import codelook.jpa.book.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookInfoRepo extends JpaRepository<BookInfo, Long> {
    List<BookInfo> findAllByIdNotIn(Collection<Integer> id);
}
