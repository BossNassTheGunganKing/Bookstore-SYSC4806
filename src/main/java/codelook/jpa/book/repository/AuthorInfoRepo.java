package codelook.jpa.book.repository;

import codelook.jpa.model.AuthorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorInfoRepo extends JpaRepository<AuthorInfo, Long> {
    List<AuthorInfo> findByName(String name);
}
