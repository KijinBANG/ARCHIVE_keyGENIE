package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.AuthMember;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.MemberRole;
import kijin.bang.keygenie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberOAuthUserService extends DefaultOAuth2UserService {
    private final PasswordEncoder passwordEncoder;
    //데이터 저장을 위한 Repository
    private final MemberRepository memberRepository;

    //데이터베이스에 저장하는 메소드
    private Member saveSocialMember(String email){
        //email 에 해당하는 데이터가 있는지 확인
        Optional<Member> result = memberRepository.findByEmail(email);
        //있으면 데이터를 리턴
        if(result.isPresent()) {
            return result.get();
        }

        //데이터베이스에 없으면 추가
        Member member = Member.builder()
                .email(email)
                .nickname(email.substring(0, email.indexOf("@")))
                .password(passwordEncoder.encode("social"))
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.MEMBER);
        memberRepository.save(member);
        return member;
    }

    @Override
    //OAuth를 이용해서 로그인 했을 때 호출되는 메서드
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName:" + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info("k:v := " + k + ":" + v);
        });

        //구글에서 접속한 경우의 email을 가져오기
        String email = null;
        if(clientName.trim().toLowerCase().indexOf("google") >= 0){
            email = oAuth2User.getAttribute("email");
            log.info("email: " + email);
        }

        //저장
        Member member = saveSocialMember(email);
        log.info("member: " + member);
        log.info("member.getEmail(): " + member.getEmail());
        log.info("member.getPassword(): " + member.getPassword());
        log.info("member.getNickname(): " + member.getNickname());


        //회원 가입한 데이터를 리턴
        AuthMember authMember = new AuthMember(
                member.getEmail(),
                member.getPassword(),
                member.getRoleSet().stream().map(role ->
                                new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        log.info("authMember: " + authMember);
        authMember.setEmail(member.getEmail());
        authMember.setNickname((member.getNickname()));
        log.info("authMember_SET: " + authMember);
        return authMember;
    }

}
