package com.company.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile("afrika")
public class AfrikaConfig extends AbstractConfig{

    @Override
    public boolean showErrorStr() {
        return false;
    }

    @Override
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(
                "jdbc:mysql://127.0.0.1:3306/example?autoReconnect=true&useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(15000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);
        dataSource.setFilters("stat,wall");
        dataSource.setConnectionProperties("druid.stat.mergeSql=true");
        return dataSource;
    }

    @Override
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

}
