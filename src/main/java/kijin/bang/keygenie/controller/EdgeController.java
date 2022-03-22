package kijin.bang.keygenie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class EdgeController {

    @GetMapping("/getUserNickname")
    public ResponseEntity<String> getUserNickname() {
        String nickName = "";
        return new ResponseEntity<>(nickName, HttpStatus.OK);
    }
}
