package com.example.graphqlv2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.graphqlv2.type.show.ShowInput;
import lombok.Data;


@Data
@TableName("`show`")
public class ShowEntity {
    @TableId(type = IdType.AUTO)
    private  Integer id;
    private  String title;
    private  Integer releaseYear;

    public static ShowEntity fromUserInput(ShowInput input) {
        ShowEntity showEntity = new ShowEntity();
        showEntity.setTitle(input.getTitle());
        showEntity.setReleaseYear(input.getReleaseYear());
        return showEntity;
    }
}
