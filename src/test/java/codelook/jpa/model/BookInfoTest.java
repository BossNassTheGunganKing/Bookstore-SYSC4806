package codelook.jpa.model;


import codelook.jpa.StaticData;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;
public class BookInfoTest {
    private BookInfo bookInfo1, bookInfo2;
    @Before
    public void setUp() throws Exception {
        bookInfo1 = StaticData.bookInfo1;
        bookInfo2 = StaticData.bookInfo2;
    }

    @Test
    public void getName(){
        assertEquals("1984", bookInfo1.getName());
        assertEquals("Of Mice and Men", bookInfo2.getName());
    }

    @Test
    public void getAuthors(){
        AuthorInfo authorInfo1 = StaticData.authorInfo1;
        AuthorInfo authorInfo2 = StaticData.authorInfo2;
        assertEquals(authorInfo1.getName(), bookInfo1.getAuthorship().get(0).getName());
        assertEquals(List.of(authorInfo1, authorInfo2).get(1).getName(), bookInfo2.getAuthorship().get(1).getName());
    }


}
