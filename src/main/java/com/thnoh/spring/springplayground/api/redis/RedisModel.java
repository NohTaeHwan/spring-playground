package com.thnoh.spring.springplayground.api.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@Getter
@RedisHash("redisModel")
public class RedisModel {

    @Id
    private Long id;
    private Long code;
    private Long addTime;

    public RedisModel(){}

    @Builder
    public RedisModel(Long id,Long code,Long addTime){
        this.id = id;
        this.code = code;
        this.addTime = addTime;
    }

}
