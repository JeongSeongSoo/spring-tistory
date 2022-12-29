package tistory.petoo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> main() throws Exception {
        log.info("[LOG] Method: main()");
        return ResponseEntity.ok().body("main");
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody Map param) throws Exception {
        log.info("[LOG] Method: test()");
        return ResponseEntity.ok().body(param);
    }

    @PostMapping("/exclude1/test")
    public ResponseEntity<?> exclude1(@RequestBody Map param) throws Exception {
        log.info("[LOG] Method: exclude1()");
        return ResponseEntity.ok().body(param);
    }

    @PostMapping("/exclude2/t/e/s/t/double/test")
    public ResponseEntity<?> exclude2(@RequestBody Map param) throws Exception {
        log.info("[LOG] Method: exclude2()");
        return ResponseEntity.ok().body(param);
    }

}