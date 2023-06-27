package com.example.graphqlv2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.graphqlv2.entity.ActorEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ActorMapper extends BaseMapper<ActorEntity> {

    void BatchSava(List<ActorEntity> actors);

}
