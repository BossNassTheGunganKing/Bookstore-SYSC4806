package codelook.jpa.objects;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorInfoRepo extends CrudRepository<AuthorInfo, Integer> {
    List<AuthorInfo> findByName(String name);

    AuthorInfo findById(Long id);
}
