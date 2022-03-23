package kijin.bang.keygenie.controller;

import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class EdgeController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Long> registerMember(@RequestBody MemberDTO memberDTO) {
        log.info("확인가능?MemberDTO: " + memberDTO);
        Long result = memberService.register(memberDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
