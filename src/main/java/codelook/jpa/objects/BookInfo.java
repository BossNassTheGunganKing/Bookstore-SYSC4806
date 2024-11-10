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

    private String name;
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<AuthorInfo> authorship;
    private String description;
    private String publisher;
    private int pageCount;
    private String genre;
    //private BufferedImage picture;

    public BookInfo(String name, List<AuthorInfo> authorship, String description, String publisher, int pageCount, String genre) {
        this.name = name;
        this.authorship = authorship;
        this.description = description;
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.genre = genre;
    }

    public BookInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public List<AuthorInfo> getAuthorship() {
        return authorship;
    }

    public void setAuthorship(List<AuthorInfo> authors) {
        this.authorship = authors;
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
