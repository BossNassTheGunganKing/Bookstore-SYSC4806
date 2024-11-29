package codelook.jpa.listing.model;

import codelook.jpa.model.BookInfo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ListingInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String ISBN;
    private String name;
    private String description;
    private Date datePublished;
    private Format format;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private int remainingCopies;

    @ManyToOne(cascade = CascadeType.MERGE)
    private BookInfo book;

    public ListingInfo(String name, String ISBN, String description,
                       Date datePublished, Format format,
                       BigDecimal originalPrice,
                       BookInfo book, int remainingCopies) {
        this.name = name;
        this.ISBN = ISBN;
        this.description = description;
        this.datePublished = datePublished;
        this.format = format;
        this.originalPrice = originalPrice;
        this.book = book;
        this.remainingCopies = remainingCopies;
    }

    public ListingInfo() {
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePublished() {
        return datePublished;
    }
    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public Format getFormat() {
        return format;
    }
    public void setFormat(Format format) {
        this.format = format;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }
    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getRemainingCopies() {
        return remainingCopies;
    }

    public void setRemainingCopies(int remainingCopies) {
        this.remainingCopies = remainingCopies;
    }

    public void addRemainingCopies(int remainingCopies) {
        this.remainingCopies += remainingCopies;
    }

    public void removeRemainingCopies(int remainingCopies) {
        this.remainingCopies -= remainingCopies;
    }

    public BookInfo getBook() {
        return book;
    }
    public void setBook(BookInfo book) {
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public enum Format{
        Hardcover,
        Paperback,
        Ebook
    }

}
