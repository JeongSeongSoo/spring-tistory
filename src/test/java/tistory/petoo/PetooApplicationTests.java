package tistory.petoo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.SerializationUtils;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class PetooApplicationTests {

    @Test
    void run() throws Exception {
        Map map = new LinkedHashMap<>();
        map.put("name", "pddu");

        String object = "test";

        byte[] data = SerializationUtils.serialize(object);
        Object temp = new String(data, StandardCharsets.UTF_8);
        Object object2 = SerializationUtils.deserialize(data);
        System.out.println(object2);
    }

    @Test
    void pathCheck() throws Exception {
        String asterisk = "([^/]+)";
        String doubleAsterisk = "(.+)";

        String pattern = "/users/*/man/**/name"
                .replaceAll("[*][*]", doubleAsterisk)
                .replaceAll("[*]", asterisk);
        String path = "/users/?/man/?/.../name";

        boolean result = Pattern.matches(pattern, path);
    }

    @Test
    void regex() throws Exception {
        // 2022.12.23[프뚜]: 숫자 허용 패턴
        String patternValue = "^[0-9]*$";
        Pattern pattern = Pattern.compile(patternValue);
        String value1 = "a13c69d";
        String value2 = "013679";

        // 2022.12.23[프뚜]: 패턴에 적합한지 확인
        Matcher matcher = pattern.matcher(value1);
        System.out.println(matcher.matches());

        matcher = pattern.matcher(value2);
        System.out.println(matcher.matches());

        // 2022.12.23[프뚜]: String Class 제공하는 matches
        value1.matches(patternValue);
        value2.matches(patternValue);
    }

}