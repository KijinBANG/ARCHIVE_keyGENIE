package kijin.bang.keygenie;

import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.MemberRole;
import kijin.bang.keygenie.repository.MemberRepository;
import kijin.bang.keygenie.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;

    //@Test
    public void insertDummyData() {
        Random r = new Random();
        for (int i = 1; i <= 100; i++) {
            String name = "member" + i;
            int year = 1980 + r.nextInt(42);
            int month = 1 + r.nextInt(9);
            int day = 10 + r.nextInt(18);
            String birthday = String.valueOf(year) + "-0" + String.valueOf(month) + "-" + String.valueOf(day);
            Member member = Member.builder()
                    .email(name + "@study.hard")
                    .password(passwordEncoder.encode(name))
                    .nickname(name)
                    .fromSocial(false)
                    .birthday(LocalDate.parse(birthday))
                    .build();
            member.addMemberRole(MemberRole.GUEST);
            if (i > 80) {
                member.addMemberRole(MemberRole.MEMBER);
                if (i > 90) {
                    member.addMemberRole(MemberRole.ADMIN);
                }
            }
            memberRepository.save(member);
        }
    }

    //@Test
    public void testGetByMno() {
        Optional<Member> result = memberRepository.findByMno(94L, false);
        System.out.println(result.get());
    }

    //@Test
    public void testGetByEmail() {
        Optional<Member> result = memberRepository.findByEmail("akdazzy@naver.com");
        System.out.println(result.get());
    }

    //@Test
    public void testRegisterMember() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("may@puppy.cute")
                .password("1234")
                .nickname("할미개")
                .birthday("2008-07-23")
                .build();
        Long result = memberService.register(memberDTO);
        System.out.println("registered Member\'s mno: " + result);
    }

    //@Test
    public void emailToNickname() {
        String email = "akdazzy@naver.com";
        String nickname = email.substring(0, email.indexOf("@"));
        System.out.println(nickname);
    }
}
