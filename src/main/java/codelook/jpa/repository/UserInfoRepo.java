package codelook.jpa.repository;

import codelook.jpa.model.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    public boolean existsUserInfoByUsername(String username);
}
