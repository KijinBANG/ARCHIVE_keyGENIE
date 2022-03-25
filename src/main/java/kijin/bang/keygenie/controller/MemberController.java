package kijin.bang.keygenie.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.oauth2.sdk.client.ClientRegistrationRequest;
import kijin.bang.keygenie.service.MemberOAuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {
    private final MemberOAuthUserService memberOAuthUserService;

    @GetMapping("/members/login")
    public void login() {

    }

    @GetMapping("/members/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인 해 주세요.");
        return "/members/login";
    }

//    @GetMapping("/login/oauth2/naver")
//    public void naverOauthRedirect(@RequestParam String code, @RequestParam String state) throws Exception {
//        // RestTemplate 인스턴스 생성
//        RestTemplate rt = new RestTemplate();
//        HttpHeaders accessTokenHeaders = new HttpHeaders();
//        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");
//        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
//        accessTokenParams.add("grant_type", "authorization_code");
//        accessTokenParams.add("client_id", "WuglNjdFjDU3Wq27jFSn");
//        accessTokenParams.add("client_secret", "gj1ziRmoWw");
//        accessTokenParams.add("code", code); // 응답으로 받은 코드
//        accessTokenParams.add("state" , state); // 응답으로 받은 상태
//        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);
//        ResponseEntity<String> accessTokenResponse = rt.exchange(
//                "https://nid.naver.com/oauth2.0/token",
//                HttpMethod.POST,
//                accessTokenRequest,
//                String.class
//        );
//        log.info("Data contains accessToken: " + accessTokenResponse.getBody());
//        log.info("typeOf(accessToken): " + accessTokenResponse.getBody().getClass().getName());
////        return "accessToken: " + accessTokenResponse.getBody();
////        // 이전에 받았던 Access Token 응답
////        ObjectMapper objectMapper = new ObjectMapper();
////        // json -> 객체로 매핑하기 위해 NaverOauthParams 클래스 생성
////        NaverOauthParams naverOauthParams = null;
//        HashMap<String,Object> jsonMap = new ObjectMapper().readValue(accessTokenResponse.getBody(), HashMap.class);
//        log.info("jsonMap: " +  jsonMap);
//
//        // header를 생성해서 access token을 넣어줍니다.
//        HttpHeaders profileRequestHeader = new HttpHeaders();
//        profileRequestHeader.add("Authorization", "Bearer " + jsonMap.get("access_token"));
//        HttpEntity<HttpHeaders> profileHttpEntity = new HttpEntity<>(profileRequestHeader);
//        // profile api로 생성해둔 헤더를 담아서 요청을 보냅니다.
//        ResponseEntity<String> profileResponse = rt.exchange(
//                "https://openapi.naver.com/v1/nid/me", HttpMethod.POST, profileHttpEntity, String.class );
//        log.info("profileResponse.getBody(): " + profileResponse.getBody());
//        log.info("profileResponse.getBody(): " + profileResponse.getBody().getClass().getName());
////        return "profile response : " + profileResponse.getBody();
//        HashMap<String,Object> naverResponse = new ObjectMapper().readValue(profileResponse.getBody(), HashMap.class);
//        log.info("naverResponse: " + naverResponse);
//        log.info("naverResponse.get(\"email\") is null: " + naverResponse.get("email"));
//        log.info("naverResponse: " + naverResponse.get("response"));
//        log.info("type of naverResponse: " + naverResponse.get("response").getClass().getName());
//        System.out.println("naverResponse.get(\"response\"): " + naverResponse.get("response"));
//        Map<String, Object> map = new LinkedHashMap<>();
//        map = (Map<String, Object>) naverResponse.get("response");
//        String email = (String) map.get("email");
//
//        memberOAuthUserService.loadUser(email);
//
//    }


//    @GetMapping("/login/kakao")
//    public void kakaoCallback(@RequestParam String code) throws Exception {
//        System.out.println(code);
//        getKaKaoAccessToken(code);
//    }
//
//    public String getKaKaoAccessToken(String code){
//        String access_Token="";
//        String refresh_Token ="";
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
//        try{
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            StringBuilder sb = new StringBuilder();
//            sb.append("grant_type=authorization_code");
//            sb.append("&client_id=e0ef89c264a8cdc43a98b07902a43b0e"); // TODO REST_API_KEY 입력
//            sb.append("&redirect_uri=http://localhost/login/kakao"); // TODO 인가코드 받은 redirect_uri 입력
//            sb.append("&code=" + code);
//            bw.write(sb.toString());
//            bw.flush();
//
//            //결과 코드가 200이라면 성공
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
//            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("response body : " + result);
//
//            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//
//            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//            System.out.println("access_token : " + access_Token);
//            System.out.println("refresh_token : " + refresh_Token);
//
//            br.close();
//            bw.close();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return access_Token;
//    }

}
