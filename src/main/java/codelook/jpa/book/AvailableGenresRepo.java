package codelook.jpa.book;

import codelook.jpa.book.AvailableGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableGenresRepo extends JpaRepository<AvailableGenres, Long> {



}
