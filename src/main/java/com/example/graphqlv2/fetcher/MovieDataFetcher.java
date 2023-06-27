package com.example.graphqlv2.fetcher;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.graphqlv2.entity.ShowEntity;
import com.example.graphqlv2.type.movie.ActionMovie;
import com.example.graphqlv2.type.movie.Movie;
import com.example.graphqlv2.type.movie.ScaryMovie;
import com.example.graphqlv2.type.show.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
@Slf4j
public class MovieDataFetcher {


    private   List<Movie> movieList = new ArrayList<Movie>(
            Arrays.asList(
        new ActionMovie(1,"致命罗密欧","导演1号"),
                    new ScaryMovie("致命id","导演2号",true,2),
            new ActionMovie(2,"尖峰时刻","导演3号"),
                    new ScaryMovie("恐怖游轮","导演4号",true,2)
            )
    );



    @DgsQuery
    public List<Movie> movies(@InputArgument String title) {
        log.info("movies load");
        if (title != null) {
            return movieList.stream().filter(x-> x.getTitle().contains(title)).collect(Collectors.toList());
        }
        return movieList;
    }

}
