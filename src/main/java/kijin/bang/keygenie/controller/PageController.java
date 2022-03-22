package kijin.bang.keygenie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about/main")
    public void about() {

    }

    @GetMapping("/architecture/main")
    public void architecture() {

    }

}

