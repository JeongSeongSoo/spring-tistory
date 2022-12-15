package tistory.petoo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tistory.petoo.service.JwtService;

@RequiredArgsConstructor
@RestController
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/create")
    public String create() throws Exception {
        return jwtService.create();
    }

}