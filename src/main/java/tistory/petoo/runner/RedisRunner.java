package tistory.petoo.runner;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 2022.11.14[프뚜]: redis Connection Check
        RedisClient client = RedisClient.create(RedisURI.create("127.0.0.1", 6379));
        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport.createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig());
        StatefulRedisConnection<String, String> connection = pool.borrowObject();
    }

}
