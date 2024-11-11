package codelook.jpa.model;

import codelook.jpa.StaticData;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    private UserInfo userInfo;
    @BeforeEach
    void setUp() {
        userInfo = StaticData.someUser;
    }

    @Test
    void verifyPasswordHash() {
        assertEquals(DigestUtils.shaHex("userpwd"), userInfo.getPasswordHash());
        assertNotEquals(DigestUtils.shaHex("wrongpassword"), userInfo.getPasswordHash());
    }
}