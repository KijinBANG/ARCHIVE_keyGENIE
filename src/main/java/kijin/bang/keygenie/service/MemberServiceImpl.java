package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.MemberRole;
import kijin.bang.keygenie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    @Override
    public Long register(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        Member registeredMember = memberRepository.save(member);
        return registeredMember.getMno();
    }

    @Override
    public void modify(MemberDTO memberDTO) {

    }

    @Override
    public Member dtoToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickname(memberDTO.getNickname())
                .fromSocial(false)
                .birthday(LocalDate.parse(memberDTO.getBirthday()))
                .build();
        member.addMemberRole(MemberRole.GUEST);
        return member;
    }
}
