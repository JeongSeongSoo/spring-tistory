package tistory.petoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketController {

    @GetMapping("/")
    public ModelAndView index() {
        // 2022.10.26[프뚜]: view name으로 page 파일 명을 입력한다. (prefix + 파일명 + suffix)
        return new ModelAndView("index");
    }

}