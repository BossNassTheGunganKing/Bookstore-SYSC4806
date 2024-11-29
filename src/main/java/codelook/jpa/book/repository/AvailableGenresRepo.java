package codelook.jpa.book.repository;

import codelook.jpa.book.model.AvailableGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AvailableGenresRepo extends JpaRepository<AvailableGenres, Long> {



}
