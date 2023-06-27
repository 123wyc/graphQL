package com.example.graphqlv2.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.graphqlv2.type.show.Actor;
import lombok.Data;

@Data
@TableName("actor")
public class ActorEntity {

    @TableId(type = IdType.AUTO)
    private  Integer id;
    private  String name;
    private  Integer age;
    private  Integer showId;

    public ActorEntity() {
    }

    public ActorEntity(String title, Integer releaseYear) {
        this.name = title;
        this.age = releaseYear;
    }

    public static ActorEntity fromActor(Actor actor) {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName(actor.getName());
        actorEntity.setAge(actor.getAge());
        return actorEntity;
    }

}
