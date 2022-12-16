package tistory.petoo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TestController {

    @GetMapping("/")
    public String main() throws Exception {
        System.out.println("[LOG] > main method");

        return "main";
    }

    @PostMapping("/check")
    public Map check(@RequestBody Map param) throws Exception {
        System.out.println("[LOG] > check method");
        System.out.println("[LOG] > param check : " + param);

        return param;
    }

}