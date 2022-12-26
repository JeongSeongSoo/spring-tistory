package tistory.petoo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> main() throws Exception {
        return ResponseEntity.ok().body("main");
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody Map param) throws Exception {
        return ResponseEntity.ok().body(param);
    }

    @PostMapping("/exclude1/test")
    public ResponseEntity<?> exclude1(@RequestBody Map param) throws Exception {
        return ResponseEntity.ok().body(param);
    }

    @PostMapping("/exclude2/t/e/s/t/double/test")
    public ResponseEntity<?> exclude2(@RequestBody Map param) throws Exception {
        return ResponseEntity.ok().body(param);
    }

}