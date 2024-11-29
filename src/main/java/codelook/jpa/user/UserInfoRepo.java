package codelook.jpa.user;

import codelook.jpa.user.*;
import codelook.jpa.order.*;
import codelook.jpa.book.*;
import codelook.jpa.image.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    public boolean existsUserInfoByUsername(String username);

    public boolean existsUserInfoById(long id);

    public Optional<UserInfo> findByUsername(String username);
    public List<UserInfo> findAllByRole(UserRole role);
}
