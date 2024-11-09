package codelook.jpa.objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.*;
import java.awt.image.BufferedImage;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String author;
    private String ISBN;
    private String description;
    private String publisher;
    private int pages;
    private int inventory;
    //private BufferedImage picture;

    public Book(String title, String author, String ISBN, String description, String publisher, int pages, int inventory) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.publisher = publisher;
        this.pages = pages;
        this.inventory = inventory;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

//    public BufferedImage getPicture() {
//        return picture;
//    }
//
//    public void setPicture(BufferedImage picture) {
//        this.picture = picture;
//    }
}
