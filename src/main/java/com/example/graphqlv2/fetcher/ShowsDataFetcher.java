package com.example.graphqlv2.fetcher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.graphqlv2.entity.ActorEntity;
import com.example.graphqlv2.entity.ShowEntity;
import com.example.graphqlv2.mapper.ActorMapper;
import com.example.graphqlv2.mapper.ShowMapper;
import com.example.graphqlv2.type.show.Actor;
import com.example.graphqlv2.type.show.Show;
import com.example.graphqlv2.type.show.ShowInput;
import com.netflix.graphql.dgs.*;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsComponent
@Slf4j
public class ShowsDataFetcher {

    @Autowired
    private ShowMapper showMapper;

    @Autowired
    private ActorMapper actorMapper;


    /**
     *  带参 简单查询
     * @param titleFilter
     * @return
     */
    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {

        QueryWrapper<ShowEntity> wrapper = new QueryWrapper<>();
        log.info("shows load");
        if (titleFilter != null) {
            wrapper.like("title",titleFilter);
        }

        List<ShowEntity> entities = showMapper.selectList(wrapper);

        return entities.stream()
                .map(Show::fromEntity).collect(Collectors.toList());
    }

    /**
     *  关联查询-show对象中的actor属性 ，但会产生n+1问题
     */
/*  @DgsData(parentType = "Show", field = "actors")
    public List<Actor> actors(DataFetchingEnvironment dataFetchingEnvironment) {
        log.info("actors load");
        Show show = dataFetchingEnvironment.getSource();

        QueryWrapper<ActorEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("show_id",show.getId());
        return actorMapper.selectList(wrapper).stream()
                .map(Actor::fromEntity).collect(Collectors.toList());
    }*/

    /**
     * 如何解决关联查询中的n+1问题
     * @param dataFetchingEnvironment
     * @return
     */
   @DgsData(parentType = "Show", field = "actors")
    public CompletableFuture<List<Actor>> actors(DataFetchingEnvironment dataFetchingEnvironment) {
        log.info("actors load");
        Show show = dataFetchingEnvironment.getSource();
        DataLoader<Integer,List<Actor>> dataLoader = dataFetchingEnvironment.
                getDataLoader("actors");
        return dataLoader.load(show.getId());
    }
    /**
     *  变更操作- 创建一个show
     * @param showInput
     * @return
     */
    @DgsMutation
    public Show create(@InputArgument(name="show") ShowInput showInput) {

        QueryWrapper<ShowEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("title",showInput.getTitle());
        ShowEntity entity = showMapper.selectOne(wrapper);
        if (null !=entity){
            throw new RuntimeException("show name repeat");
       }
        ShowEntity show = ShowEntity.fromUserInput(showInput);
        showMapper.insert(show);
        entity = showMapper.selectOne(wrapper);


        List<ActorEntity>  actorList = new ArrayList<>();
        for (Actor actor: showInput.getActors()
             ) {
            ActorEntity actorEntity = ActorEntity.fromActor(actor);
            actorEntity.setShowId(entity.getId());
            actorList.add(actorEntity);
            //actorMapper.insert(actorEntity);
        }
        actorMapper.BatchSava(actorList);
        return Show.fromEntity(show);
    }
    @DgsQuery
    public List<Actor> actors(@InputArgument String name) {
        QueryWrapper<ActorEntity> wrapper = new QueryWrapper<>();
        log.info("shows load");
        if (name != null) {
            wrapper.like("title",name);
        }
        List<ActorEntity> entities = actorMapper.selectList(wrapper);
        return entities.stream()
                .map(Actor::fromEntity).collect(Collectors.toList());
    }

    @DgsData(parentType = "Actor", field = "show")
    public CompletableFuture<List<Show>> shows(DataFetchingEnvironment dataFetchingEnvironment) {
        log.info("actors load");
        Actor actor = dataFetchingEnvironment.getSource();
        DataLoader<Integer,List<Show>> dataLoader = dataFetchingEnvironment.
                getDataLoader("shows");
        return dataLoader.load(actor.getShowId());
    }
}