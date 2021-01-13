package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity{

    @NotBlank
    @Size(min = 1, max = 25)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private final List<Movie> movies = new ArrayList<>();

    public Tag(@NotBlank @Size(min = 1, max = 25) String name) {
        this.name = name;
    }

    public Tag() {
    }

    public List<Movie> getmovies() {
        return movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName(){
        return "#" + name + " ";
    }
}
