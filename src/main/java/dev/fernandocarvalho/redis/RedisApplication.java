package dev.fernandocarvalho.redis;

import dev.fernandocarvalho.redis.runners.CacheRunner;
import dev.fernandocarvalho.redis.runners.GeographyRunner;
import dev.fernandocarvalho.redis.runners.OrderRunner;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Log
@SpringBootApplication
@EnableCaching
public class RedisApplication {

    private final GeographyRunner geographyRunner;
    private final CacheRunner cacheRunner;
    private final OrderRunner orderRunner;

    @Autowired
    public RedisApplication(GeographyRunner geographyRunner, CacheRunner cacheRunner, OrderRunner orderRunner) {
        this.geographyRunner = geographyRunner;
        this.cacheRunner = cacheRunner;
        this.orderRunner = orderRunner;
    }

    private ApplicationRunner titledRunner(String title, ApplicationRunner rr) {
        return args -> {
            log.info(title.toUpperCase() + ":");
            rr.run(args);
        };
    }

    @Bean
    CacheManager redisCache(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .build();
    }

    @Bean
    ApplicationRunner geography() {
        return titledRunner(geographyRunner.name(), args -> geographyRunner.run());
    }

    @Bean
    ApplicationRunner order() {
        return titledRunner(orderRunner.name(), args -> orderRunner.run());
    }

    @Bean
    ApplicationRunner cache() {
        return titledRunner(cacheRunner.name(), args -> cacheRunner.run());
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}



