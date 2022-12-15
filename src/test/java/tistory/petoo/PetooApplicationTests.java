package tistory.petoo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tistory.petoo.service.JwtService;

@SpringBootTest
class PetooApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    void create() throws Exception {
        String token = jwtService.create();
        System.out.println("[LOG] 토큰이 발생되었습니다. > " + token);

        String body = jwtService.check(token);
        System.out.println("[LOG] 토큰의 바디 값입니다. > " + body);
    }

}
