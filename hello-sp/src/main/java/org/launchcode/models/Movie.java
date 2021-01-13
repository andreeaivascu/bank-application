package org.launchcode.models;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Movie extends AbstractEntity{

    @NotBlank
    @Size(min = 3, max = 50,message = "Size should be between 3 and 50 characters!")
    private String name;

    @ManyToOne
    @NotNull(message = "Category is required")
    private MovieCategory category;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private MovieDetails movieDetails;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

    public Movie(String name, MovieCategory category) {
        this.name = name;
        this.category = category;

    }

    public Movie() {
    }

    public List<Tag> getTags() {
        return tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }
    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    public MovieCategory getCategory() {
        return category;
    }

    public void setCategory(MovieCategory movieCategory) {
        this.category = movieCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
