package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.AuthMember;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadByUserName: " + username);
        //DB에서 username 에 해당하는 데이터 찾아오기
        Member member = memberRepository.findByEmail(username, false).get();
        //인증을 위한 클래스의 인스턴스를 생성
        AuthMember authMember = new AuthMember(
                member.getEmail(),
                member.getPassword(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority(
                                "ROLE_" + role.name()))
                        .collect(Collectors.toSet())
        );
        authMember.setNickName(member.getNickname());
        authMember.setFromSocial(member.isFromSocial());
        log.info(member);
        log.info(authMember);
        return authMember;
    }
}
