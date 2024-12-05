package codelook.jpa.model;

import codelook.jpa.StaticData;
import codelook.jpa.book.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorInfoTest {
    private AuthorInfo authorInfo1, authorInfo2;
    @Before
    public void setUp() throws Exception {
         authorInfo1 = StaticData.authorInfo1;
         authorInfo2 = StaticData.authorInfo2;
    }

    @Test
    public void getName() {
        assertEquals("George", authorInfo1.getName());
        assertEquals("Geoff", authorInfo2.getName());
    }

    @Test
    public void getBio() {
        assertEquals("just a simple author", authorInfo1.getBio());
        assertEquals("just a simpleton", authorInfo2.getBio());
    }
}
