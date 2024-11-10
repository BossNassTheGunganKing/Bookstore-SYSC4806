package codelook.jpa;

import codelook.jpa.objects.AuthorInfo;
import codelook.jpa.objects.BookInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorInfoTest {
    private AuthorInfo authorInfo1, authorInfo2;
    @Before
    public void setUp() throws Exception {
         authorInfo1 = new AuthorInfo("George", "just a simple author");
         authorInfo2 = new AuthorInfo("Geoff", "just a simpleton");
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
