package org.launchcode.models;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MovieCategory extends AbstractEntity{

    @Size(min = 3, message = "name must be at least 3 characters long")
    private String name;

    @OneToMany(mappedBy = "category")
    private final List<Movie> movies = new ArrayList<>();

    public MovieCategory(@Size(min = 3, message = "name must be at least 3 characters long") String name){
        this.name = name;
    }

    public MovieCategory(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
