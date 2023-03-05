package com.thnoh.spring.springplayground.api.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/redis")
public class RedisController {

    private final RedisModelRepository redisModelRepository;

    private final RedisTemplate<String,RedisModel> redisTemplate;

    @GetMapping("")
    public String ok(){
        return "ok";
    }

    @GetMapping("/save")
    public String save(){
        Long randomId = generateRandomId();

        RedisModel redisModel = RedisModel.builder()
                .id(randomId)
                .code(200L)
                .addTime(System.currentTimeMillis())
                .build();

        log.info(">>>> [save] redisModel={}",redisModel);

        redisModelRepository.save(redisModel);

        return "save";

    }

    @GetMapping("/all")
    public Iterable<RedisModel> getAll(){
        return redisModelRepository.findAll();

    }

    @GetMapping("/get")
    public Long get(){
        Long id = generateRandomId();
        return redisModelRepository.findById(id)
                .map(RedisModel::getCode)
                .orElse(0L);
    }

    @GetMapping("/setsave")
    public String saveSortedSet(){

        Long randomId = generateRandomId();

        RedisModel redisModel = RedisModel.builder()
                .id(randomId)
                .code(200L)
                .addTime(System.currentTimeMillis())
                .build();

        ZSetOperations<String,RedisModel> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("testQueue",redisModel,redisModel.getAddTime());

        return "save sorted set";
    }

    @GetMapping("/getSet")
    public void getSetData(){

        ZSetOperations<String,RedisModel> zSetOperations = redisTemplate.opsForZSet();

        // Set<RedisModel> set = zSetOperations.range("testQueue",0,3);
        Set<ZSetOperations.TypedTuple<RedisModel>> set =zSetOperations.popMin("testQueue",5);
        set.forEach(element -> log.info("5개 set 값 {}",element.getValue()));

        //Set<Object> queue = redisTemplate.opsForZSet().range("testQueue",start,end);

        //queue.forEach(element -> log.info("start : ={} 부터 end : ={} 까지의 set 값 {}",start,end,element));
    }

    @GetMapping("/rank")
    public void checkRank(){
        ZSetOperations<String,RedisModel> zSetOperations = redisTemplate.opsForZSet();

        Set<RedisModel> set = redisTemplate.opsForZSet().range("testQueue",0,5);

        set.forEach(element -> {
            Long rank = zSetOperations.rank("testQueue",element);
            log.info("당신의 대기열에서 순위는? ={}",rank);
        });


    }

    private Long generateRandomId(){
        return ThreadLocalRandom.current().nextLong(1_000_000_000);
    }

}
