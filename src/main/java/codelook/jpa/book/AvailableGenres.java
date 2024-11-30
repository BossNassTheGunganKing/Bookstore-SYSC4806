package codelook.jpa.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AvailableGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String genre;

    public AvailableGenres(String genre) {
        this.genre = genre.substring(0,1).toUpperCase() + genre.substring(1).toLowerCase();
    }

    public AvailableGenres() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
