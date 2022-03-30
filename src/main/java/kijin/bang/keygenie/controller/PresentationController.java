package kijin.bang.keygenie.controller;

//import kijin.bang.keygenie.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/presentation")
public class PresentationController {
    private final HttpSession httpSession;

    @GetMapping("/main")
    public void presentation() {

    }

    @GetMapping("/panel/main")
    public void panel() {

    }

    @GetMapping("/poster/main")
    public void poster() {

    }

    @GetMapping("/ppt/main")
    public void ppt() {

    }

    @GetMapping("/CInBI/main")
    public void ciNbi() {

    }

    @GetMapping("/threeD/main")
    public void threeD() {

    }

    @GetMapping("/etc/main")
    public void etc() {

    }

}

