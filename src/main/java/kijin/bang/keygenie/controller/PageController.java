package kijin.bang.keygenie.controller;

//import kijin.bang.keygenie.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("posts", postsService.findAllDesc());
//
//        SessionMember member = (SessionMember) httpSession.getAttribute("member");
//
//        if(member != null) {
//            model.addAttribute("userName", member.getEmail());
//        }
        return "index";
    }

    @GetMapping("/about/main")
    public void about() {

    }

    @GetMapping("/about/thisHP/main")
    public void thisHP() {

    }

    @GetMapping("/about/theArtOfThinking/main")
    public void theArtOfThinking() {

    }

    @GetMapping("/about/keyGENIE/main")
    public void keyGENIE() {

    }

    @GetMapping("/contacts/main")
    public void contacts() {

    }

}

