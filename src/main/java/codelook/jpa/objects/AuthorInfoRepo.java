package codelook.jpa.objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorInfoRepo extends JpaRepository<AuthorInfo, Integer> {
    List<AuthorInfo> findByName(String name);

    AuthorInfo findById(Long id);
}
