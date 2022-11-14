package tistory.petoo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    private String[] hosts = new String[] {
        "127.0.0.1",
        "localhost"
    };
    private int connectTimeout = 5000;
    private int maxTotal = 50;
    private int minIdle = 10;
    private int maxIdle = 30;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() throws Exception {
        // return jedis();
        return lettuce();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() throws Exception {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

    private RedisConnectionFactory jedis() throws Exception {
        // 2022.11.14[프뚜]: Jedis Pool Config
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        // 2022.11.14[프뚜]: Jedis Client Config
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofMillis(connectTimeout))
                .usePooling().poolConfig(jedisPoolConfig)
                .build();

        // 2022.11.14[프뚜]: hosts 개수 처리
        if (hosts.length == 1) {
            return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration);
        } else {
            return new JedisConnectionFactory(redisClusterConfiguration(), jedisClientConfiguration);
        }
    }

    private RedisConnectionFactory lettuce() throws Exception {
        // 2022.11.14[프뚜]: config
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setTestOnBorrow(testOnBorrow);
        genericObjectPoolConfig.setTestOnReturn(testOnReturn);

        // 2022.11.14[프뚜]: poolingClientConfiguration
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(genericObjectPoolConfig)
                .commandTimeout(Duration.ofMillis(connectTimeout))
                .shutdownTimeout(Duration.ofMillis(connectTimeout))
                .build();

        // 2022.11.14[프뚜]: hosts 개수 처리
        if (hosts.length == 1) {
            return new LettuceConnectionFactory(redisStandaloneConfiguration(), lettucePoolingClientConfiguration);
        } else {
            return new LettuceConnectionFactory(redisClusterConfiguration(), lettucePoolingClientConfiguration);
        }
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        // 2022.11.14[프뚜]: hosts가 1개
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hosts[0]);
        redisStandaloneConfiguration.setPort(6379);

        return redisStandaloneConfiguration;
    }

    private RedisClusterConfiguration redisClusterConfiguration() {
        // 2022.11.14[프뚜]: hosts가 N개
        return new RedisClusterConfiguration(Arrays.asList(hosts));
    }

}