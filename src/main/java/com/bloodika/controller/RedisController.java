package com.bloodika.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RedisController {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @GetMapping("/listEverything")
    public List<String> listEverything(){
        try(JedisPool jedisPool = new JedisPool(redisHost,redisPort);Jedis jedis = jedisPool.getResource();){
            return jedis.
                keys("*")
                .stream()
                .map(key -> {
                    System.out.println(jedis.type(key));
                return String.format("%s:%s",key, 
                jedis.type(key).equalsIgnoreCase("string") ? jedis.get(key) : jedis.hgetAll(key));
                }
            ).toList();
        }
    }

}
