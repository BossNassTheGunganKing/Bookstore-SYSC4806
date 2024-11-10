package codelook.jpa;


import codelook.jpa.objects.AuthorInfo;
import codelook.jpa.objects.BookInfo;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;
public class BookInfoTest {
    private BookInfo bookInfo1, bookInfo2;
    @Before
    public void setUp() throws Exception {
        AuthorInfo authorInfo1 = new AuthorInfo("George", "just a simple author");
        AuthorInfo authorInfo2 = new AuthorInfo("Geoff", "just a simple author");

        bookInfo1 = new BookInfo("1984", List.of(authorInfo1), "Corruption", "People", 222, "Dystopian");
        bookInfo2 = new BookInfo("MiceMen", List.of(authorInfo1, authorInfo2), "Sad", "Mice", 333, "Coming of Age");

    }

    @Test
    public void getName(){
        assertEquals("1984", bookInfo1.getName());
        assertEquals("MiceMen", bookInfo2.getName());
    }

    @Test
    public void getAuthors(){
        AuthorInfo authorInfo1 = new AuthorInfo("George", "just a simple author");
        AuthorInfo authorInfo2 = new AuthorInfo("Geoff", "just a simple author");
        assertEquals(authorInfo1.getName(), bookInfo1.getAuthorship().get(0).getName());
        assertEquals(List.of(authorInfo1, authorInfo2).get(1).getName(), bookInfo2.getAuthorship().get(1).getName());
    }


}
