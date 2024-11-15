package codelook.jpa.repository;

import codelook.jpa.model.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    public boolean existsUserInfoByUsername(String username);

    public boolean existsUserInfoById(long id);

    public Optional<UserInfo> findByUsername(String username);
}
