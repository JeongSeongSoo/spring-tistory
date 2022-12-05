package tistory.petoo.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class AuthService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    log.info("{\"id: \"2\", \"name\": \"auth\"}");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
