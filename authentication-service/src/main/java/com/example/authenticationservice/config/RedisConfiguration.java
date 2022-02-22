package com.example.authenticationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

//    private static String REDIS_PROTOCOL_PREFIX = "redis://";
//
//    @Bean
//    public RedissonClient redissonClient(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port){
//
//        Config config = new Config();
//        config.useSingleServer().setAddress(String.format("%s%s:%d",REDIS_PROTOCOL_PREFIX,  host, port));
//        return Redisson.create(config);
//    }

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.password}")
    private String redisPass;

    @Bean
    @Primary
    JedisConnectionFactory jedisConnectionFactory() throws Exception {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        if (redisPass != null) {
            factory.setPassword(redisPass);
        }
        factory.setUsePool(true);

        return factory;
    }
//Redis Client
    //Central class that interact with Redis data.
    //Auto serialization and deserialization between object and redis binary data
    @Bean
    RedisTemplate<String, Object> redisTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        //Untyped HashMap instances -> Jackson2JsonSerializer
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));

        return template;
    }
}
