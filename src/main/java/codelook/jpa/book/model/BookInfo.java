package codelook.jpa.book.model;

import codelook.jpa.book.model.AuthorInfo;
import codelook.jpa.user.model.UserInfo;
import jakarta.persistence.*;

import java.util.List;

@Entity
class BookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<AuthorInfo> authorship;
    private String description;

    @ManyToOne
    private UserInfo publisher;
    private int pageCount;
    private String genre;
    private String coverImage;

    public BookInfo(String name, List<AuthorInfo> authorship, String description, UserInfo publisher, int pageCount, String genre) {
        this.name = name;
        this.authorship = authorship;
        this.description = description;
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.genre = genre;
        this.coverImage = null;
    }

    public BookInfo() {
    }

    public Integer getId() {
        return id;
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

    public UserInfo getPublisher() {
        return publisher;
    }

    public void setPublisher(UserInfo publisher) {
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

    public String coverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
