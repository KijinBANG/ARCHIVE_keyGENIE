package kijin.bang.keygenie.controller;

import kijin.bang.keygenie.dto.AuthMember;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
        log.info("모든 유저가 접근 가능");
    }
    @GetMapping("/guest")
    public void exGuest(@AuthenticationPrincipal AuthMember authMember) {
        log.info("Guest 이상의 로그인 권한을 가진 유저만 접근 가능");
        log.info(authMember);
    }
    @GetMapping("/member")
    public void exMember() {
        log.info("Member 이상의 로그인 권한을 가진 유저만 접근 가능");
    }
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("관리자만 접근 가능");
    }
}
