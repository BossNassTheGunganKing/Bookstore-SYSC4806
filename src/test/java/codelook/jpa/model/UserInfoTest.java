package codelook.jpa.model;

import codelook.jpa.StaticData;
import codelook.jpa.user.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    private UserInfo userInfo;


    @BeforeEach
    void setUp() {
        userInfo = StaticData.someUser;
    }

    @Test
    void getPassword() {
        assertEquals("userpwd", userInfo.getPassword());
    }
}