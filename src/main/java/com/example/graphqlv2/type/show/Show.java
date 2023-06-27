package com.example.graphqlv2.type.show;

import com.example.graphqlv2.entity.ShowEntity;
import lombok.Data;
import java.util.List;


@Data
public class Show {

    private Integer id;
    private  String title;
    private  Integer releaseYear;
    private List<Actor> actors;
    public Show(){

    }

    public Show(String title, Integer releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public Show(String title, Integer releaseYear, List<Actor> actors) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.actors = actors;
    }


    public static Show fromEntity(ShowEntity entity) {
        Show show = new Show();
        show.setId(entity.getId());
        show.setTitle(entity.getTitle());
        show.setReleaseYear(entity.getReleaseYear());
        return show;
    }
}
