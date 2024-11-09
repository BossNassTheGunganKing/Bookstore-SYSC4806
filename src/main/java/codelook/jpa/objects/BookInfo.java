package codelook.jpa.objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<AuthorInfo> authorship;
    private String ISBN;
    private String description;
    private String publisher;
    private int pageCount;
    private String genre;
    //private BufferedImage picture;

    public BookInfo(String title, List<AuthorInfo> authorship, String ISBN, String description, String publisher, int pageCount, String genre) {
        this.title = title;
        this.authorship = authorship;
        this.ISBN = ISBN;
        this.description = description;
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.genre = genre;
    }

    public BookInfo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorInfo> getAuthorship() {
        return authorship;
    }

    public void setAuthorship(List<AuthorInfo> authors) {
        this.authorship = authors;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pages) {
        this.pageCount = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

//    public BufferedImage getPicture() {
//        return picture;
//    }
//
//    public void setPicture(BufferedImage picture) {
//        this.picture = picture;
//    }
}
