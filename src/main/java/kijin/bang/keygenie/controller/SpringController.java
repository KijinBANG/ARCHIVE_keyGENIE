package kijin.bang.keygenie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/spring")
public class SpringController {

    @GetMapping("/main")
    public void main() {}

    @GetMapping("/planner/main")
    public void planner() {}


}
