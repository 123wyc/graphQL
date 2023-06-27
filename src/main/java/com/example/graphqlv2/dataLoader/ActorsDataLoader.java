package com.example.graphqlv2.dataLoader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.graphqlv2.entity.ActorEntity;
import com.example.graphqlv2.mapper.ActorMapper;
import com.example.graphqlv2.type.show.Actor;
import com.netflix.graphql.dgs.DgsDataLoader;
import graphql.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Slf4j
@DgsDataLoader(name="actors")//,maxBatchSize=1)
@Component
public class ActorsDataLoader implements MappedBatchLoader<Integer, List<Actor>> {

    @Autowired
    private  ActorMapper actorMapper;


    @Override
    public CompletionStage<Map<Integer, List<Actor>>> load(Set<Integer> set) {

        return CompletableFuture.supplyAsync(
                () -> {
                    QueryWrapper<ActorEntity> wrapper = new QueryWrapper<>();
                    wrapper.in("show_id",set);

                    List<ActorEntity> list = actorMapper.selectList(wrapper);
                    log.info(list.toString());

                    Map<Integer, List<Actor>> result = Maps.newHashMapWithExpectedSize(set.size());
                    for (Integer id: set
                         ) {
                        result.put(id,list.stream().filter(x->x.getShowId()==id).
                                map(ActorEntity -> Actor.fromEntity(ActorEntity)).
                                collect(Collectors.toList()));
                    }
                    return  result;
                }
        );
    }

   /* @Override
    public CompletionStage<List<Actor>> load(List<Integer> ids) {

        log.info("actorsDataLoader loading ids: {}", ids);
        return CompletableFuture.supplyAsync(
                () -> {
                    QueryWrapper<ActorEntity> wrapper = new QueryWrapper<>();
                    wrapper.in("show_id",ids);
                    List<Actor> list = actorMapper.selectList(wrapper)
                            .stream().map(ActorEntity -> Actor.fromEntity(ActorEntity))
                            .collect(Collectors.toList());
                    log.info(list.toString());
                    return  list;
                }
        );
    }*/
}
