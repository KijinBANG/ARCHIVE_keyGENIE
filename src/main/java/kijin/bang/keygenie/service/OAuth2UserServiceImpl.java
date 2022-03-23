//package kijin.bang.keygenie.service;
//
//import kijin.bang.keygenie.dto.OAuthAttributes;
//import kijin.bang.keygenie.dto.SessionMember;
//import kijin.bang.keygenie.entity.Member;
//import kijin.bang.keygenie.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collections;
//
//@Log4j2
//@Service
//@RequiredArgsConstructor
//public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final MemberRepository memberRepository;
//    private final HttpSession httpSession;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
////        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
////        OAuth2User oAuth2User = delegate.loadUser(userRequest);
////
////        //OAuth2 서비스 id
////        String registrationId = userRequest.getClientRegistration().getRegistrationId();
////        //OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
////        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
////        log.info("registrationId: " + registrationId);
////        log.info("userNameAttributeName: " + userNameAttributeName);
////
////        //OAuth2UserService
////        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
////        Member member = saveOrUpdate(attributes);
////        httpSession.setAttribute("member", new SessionMember(member)); // SessionMember (직렬화된 DTO 클래스 사용)
////
////        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST")),
////                attributes.getAttributes(),
////                attributes.getNameAttributeKey());
//////        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//////        oAuth2User.getAttributes().forEach((k, v) -> {
//////            log.info("k:v := " + k + ":" + v);
//////        });
//////        return oAuth2User;
//    }
//
////    //회원 가입 및 수정 서비스 로직
////    private Member saveOrUpdate(OAuthAttributes attributes) {
////        Member member = memberRepository.findByEmail(attributes.getEmail()).get();
////        member.changeNickname(attributes.getNickname());
////        return memberRepository.save(member);
////    }
//
//}
