package org.launchcode.models.dto;

import org.launchcode.models.Movie;
import org.launchcode.models.Tag;

import javax.validation.constraints.NotNull;

public class MovieTagDTO {

    @NotNull
    private Movie movie;

    @NotNull
    private Tag tag;

    public MovieTagDTO() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
