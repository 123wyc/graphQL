package com.example.graphqlv2.type.movie;

import lombok.Data;

@Data
public class ActionMovie extends Movie {

    private Integer nrOfExplosions;
    private String title;
    private String director;

    public ActionMovie(Integer nrOfExplosions, String title, String director) {
        this.nrOfExplosions = nrOfExplosions;
        this.title = title;
        this.director = director;
    }
    
}

