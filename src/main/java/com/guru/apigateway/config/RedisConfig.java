package com.guru.apigateway.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

@Configuration
public class RedisConfig {

    private final Environment env;

    public RedisConfig(Environment env){

        this.env = env;
    }


    private String redisHostName ="localhost";

    private int redisPort=6379;

    @Bean
    public JedisConnectionFactory createJedisConnectionFactory(){

        RedisStandaloneConfiguration redisStandaloneConfiguration= new RedisStandaloneConfiguration(redisHostName,redisPort);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(createJedisConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }
}
