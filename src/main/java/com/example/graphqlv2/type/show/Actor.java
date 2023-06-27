package com.example.graphqlv2.type.show;

import com.example.graphqlv2.entity.ActorEntity;
import lombok.Data;

@Data
public class Actor {
    private  Integer id;
    private  String name;
    private  Integer age;
    private  Integer showId;
    private  Show show;

    public Actor() {
    }

    public Actor(String title, Integer releaseYear) {
        this.name = title;
        this.age = releaseYear;
    }

    public static Actor fromEntity(ActorEntity entity) {
        Actor actor = new Actor();
        actor.setName(entity.getName());
        actor.setAge(entity.getAge());
        actor.setId(entity.getId());
        actor.setShowId(entity.getShowId());
        return actor;
    }


}
