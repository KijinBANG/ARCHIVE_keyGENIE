package kijin.bang.keygenie;

import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.MemberRole;
import kijin.bang.keygenie.repository.MemberRepository;
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
                    .nickName(name)
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
    public void testMno() {
        Optional<Member> result = memberRepository.findByMno(94L, false);
        System.out.println(result.get());
    }

    @Test
    public void testEmail() {
        Optional<Member> result = memberRepository.findByEmail("akdazzy@naver.com", false);
        System.out.println(result.get());
    }
}
