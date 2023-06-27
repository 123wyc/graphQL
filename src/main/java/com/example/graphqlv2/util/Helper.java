package com.example.graphqlv2.util;

import com.example.graphqlv2.type.show.Actor;

import java.util.Arrays;
import java.util.List;

/**
 * 模拟持久化操作
 */
public class Helper {

    public static List<Actor> Actors(){
        return  Arrays.asList(new Actor("wyc", 111));
    }
}
