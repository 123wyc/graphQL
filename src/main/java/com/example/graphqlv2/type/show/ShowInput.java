package com.example.graphqlv2.type.show;

import lombok.Data;

import java.util.List;


@Data
public class ShowInput {
    private  String title;
    private  Integer releaseYear;
    private List<Actor> actors;
}
