package codelook.jpa.model;

import codelook.jpa.StaticData;
import codelook.jpa.book.model.ListingInfo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListingInfoTest {
    private ListingInfo listingInfo;
    @Before
    public void setUp() throws Exception {
        listingInfo = StaticData.listing1;
    }

    @Test
    public void getISBN() {
        assertEquals(listingInfo.getISBN() , "111-111-111");
    }

    @Test
    public void getName() {
        assertEquals(listingInfo.getName(), "1984, Hardcover");
    }
}
