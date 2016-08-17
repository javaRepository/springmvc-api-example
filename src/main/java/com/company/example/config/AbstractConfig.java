package com.company.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

public abstract class AbstractConfig {

    @Bean
    public abstract boolean showErrorStr();

    @Bean
    public String uploadImgPath() {
        return "/userdata1/example/img";
    }

    //------------------------------文件上传----------------------------------

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(20485760);
        return commonsMultipartResolver;
    }

    //------------------------------mysqlConfig----------------------------------

    @Bean(initMethod = "init", destroyMethod = "close")
    public abstract DataSource dataSource() throws SQLException;

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    //------------------------------redisConfig----------------------------------

    @Bean
    public abstract JedisConnectionFactory jedisConnectionFactory();

    @Bean
    public StringRedisSerializer stringSerializer(){
        return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory, StringRedisSerializer stringRedisSerializer){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

}
