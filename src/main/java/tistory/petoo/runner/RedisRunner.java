package tistory.petoo.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class RedisRunner implements ApplicationRunner {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // dataSet();

        dataKeys();

        dataScan();
    }

    private void dataSet() throws Exception {
        // 2022.12.05[프뚜]: 1~100,000 데이터 Insert
        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForValue().set(String.valueOf(i), String.valueOf(i));
        }
    }

    private void dataKeys() throws Exception {
        // 2022.12.05[프뚜]: 전체 데이터를 keys로 꺼냄
        Set<String> list = redisTemplate.keys("*");
        Iterator<String> ite = list.iterator();

        while (ite.hasNext()) {
            System.out.println("keys > " + ite.next());
        }
    }

    private void dataScan() throws Exception {
        // 2022.12.05[프뚜]: 전체 데이터를 scan으로 꺼냄
        ScanOptions options = ScanOptions.scanOptions()
                .match("*")
                .count(5000)
                .build();
        Cursor<String> cursor = redisTemplate.scan(options);

        while (cursor.hasNext()) {
            System.out.println("scan > " + cursor.next());
        }
    }

}
