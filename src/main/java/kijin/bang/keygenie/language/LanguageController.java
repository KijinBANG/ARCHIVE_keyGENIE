package kijin.bang.keygenie.language;

//import kijin.bang.keygenie.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/language")
public class LanguageController {
    private final HttpSession httpSession;

    @GetMapping("/main")
    public void main(Model model) {
    }

    @GetMapping("/css/main")
    public void css() {

    }

    @GetMapping("/html/main")
    public void html() {

    }

    @GetMapping("/java/main")
    public void java() {

    }

    @GetMapping("/js/main")
    public void js() {

    }

    @GetMapping("/python/main")
    public void python() {

    }

}

