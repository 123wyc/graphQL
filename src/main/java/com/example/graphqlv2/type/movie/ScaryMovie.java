package com.example.graphqlv2.type.movie;

import lombok.Data;

@Data
public class ScaryMovie extends  Movie {
    private String title;
    private String director;
    private Boolean gory;
    private Integer scareFactor;

    public ScaryMovie(String title, String director, Boolean gory, Integer scareFactor) {
        this.title = title;
        this.director = director;
        this.gory = gory;
        this.scareFactor = scareFactor;
    }
}
