package tistory.petoo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.SerializationUtils;
import tistory.petoo.config.TestConfig;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    void replaceAll() throws Exception {
        String value = "replace: *";
        value = value.replaceAll("[*]", "asterisk");
        print(value);

        value = "replace: +";
        value = value.replaceAll("[+]", "plus");
        print(value);

        value = "replace: |";
        value = value.replaceAll("[|]", "문자 연결자");
        print(value);

        value = "replace: (";
        value = value.replaceAll("\\(", "괄호");
        print(value);

        value = "replace: ^";
        value = value.replaceAll("\\^", "Shift + 6");
        print(value);
    }

    @Test
    void stream() throws Exception {
        List<TestDTO> list = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            TestDTO testDTO = new TestDTO();
            testDTO.setAge(i);
            testDTO.setJob("job" + i);
            testDTO.setHobby("hobby" + i);
            testDTO.setName("name" + i);
            list.add(testDTO);
        }

        // map, collect, filter, allMatch, anyMatch, flatMap, forEach, limit, max, min, peek, noneMatch, reduce, sorted
        list.stream()
                .filter(data -> data.getAge() % 2 == 0)
                .peek(data -> {
                    data.setName(data.getName().toUpperCase());
                    print(data.toString());
                })
                .filter(data -> data.getAge() % 2 == 1)
                .peek(data -> {
                    data.setJob(data.getJob().toUpperCase());
                    print(data.toString());
                })
                .collect(Collectors.toList());
    }

    private void print(String param) {
        System.out.println(param);
    }

}