package codelook.jpa;

import codelook.jpa.objects.AuthorInfo;
import codelook.jpa.objects.BookInfo;
import codelook.jpa.objects.ListingInfo;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

public class ListingInfoTest {
    private ListingInfo listingInfo;
    @Before
    public void setUp() throws Exception {
        AuthorInfo authorInfo = new AuthorInfo("George", "just a simple author");

        BookInfo bookInfo1 = new BookInfo("1984", List.of(authorInfo), "Corruption", "People", 222, "Dystopian");

        listingInfo = new ListingInfo("1984", "111-111-111" ,"Corruption", new Date(1949,6,8), ListingInfo.Format.Hardcover, new BigDecimal(19.99), bookInfo1, 10);
    }

    @Test
    public void getISBN() {
        assertEquals(listingInfo.getISBN() , "111-111-111");
    }

    @Test
    public void getName() {
        assertEquals(listingInfo.getName(), "1984");
    }
}
