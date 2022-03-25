package kijin.bang.keygenie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
