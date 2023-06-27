package com.example.graphqlv2.dataLoader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.graphqlv2.entity.ActorEntity;
import com.example.graphqlv2.entity.ShowEntity;
import com.example.graphqlv2.mapper.ActorMapper;
import com.example.graphqlv2.mapper.ShowMapper;
import com.example.graphqlv2.type.show.Actor;
import com.example.graphqlv2.type.show.Show;
import com.netflix.graphql.dgs.DgsDataLoader;
import graphql.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoader;
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
@DgsDataLoader(name="shows")//,maxBatchSize=1)
@Component
public class ShowsDataLoader implements BatchLoader<Integer, Show> {

    @Autowired
    private ShowMapper showMapper;

    @Override
    public CompletionStage<List<Show>> load(List<Integer> keys) {
        return CompletableFuture.supplyAsync(
                () -> {
                    QueryWrapper<ShowEntity> wrapper = new QueryWrapper<>();
                    wrapper.in("id",keys);
                    List<ShowEntity> list = showMapper.selectList(wrapper);
                    log.info(list.toString());
                    return list.stream().map(ShowEntity -> Show.fromEntity(ShowEntity))
                            .collect(Collectors.toList());
                }
        );
    }
}
