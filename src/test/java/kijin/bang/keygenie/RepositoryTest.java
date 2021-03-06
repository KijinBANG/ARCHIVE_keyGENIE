package kijin.bang.keygenie;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.entity.*;
import kijin.bang.keygenie.repository.*;
import kijin.bang.keygenie.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;
    @Autowired
    private GuestBookRepository guestBookRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanImageRepository planImageRepository;
    @Autowired
    private ReviewRepository reviewRepository;

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
                .nickname("?????????")
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

//    //@Test //-> ?????? ??????! PK ????????? ???????????????...  '???????????????!'??? ???????????????, '????????? ??? ???!'??? ????????? ??????! ????????????!
//    public void testDeleteMemberById() {
//        String email = "member200@study.hard";
//        memberRepository.deleteById(email);
//    }

    //@Test
    public void insertGuestBook(){
        for(int i=1; i<=300; i=i+1){
            GuestBook guestBook = GuestBook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("User..." + i)
                    .build();
            guestBookRepository.save(guestBook);
        }
    }

    //@Test
    public void updateGuestBook(){
        GuestBook guestBook = GuestBook.builder()
                .gno(299L)
                .title("?????? ??????")
                .content("?????? ??????")
                .writer("????????? ??????")
                .build();
        guestBookRepository.save(guestBook);
    }

    //@Test
    public void testQuery(){
        //paging ??????
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //Querydsl Entity ????????????
        QGuestBook qGuestBook = QGuestBook.guestBook;

        //?????? ??????
        BooleanBuilder builder = new BooleanBuilder();
        //title??? 1??? ????????? ???????????? ??????
        String keyword = "1";
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        //????????? ??????
        builder.and(expression);
        //?????? ??????
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        //??????
        for(GuestBook guestBook : result){
            System.out.println(guestBook);
        }
    }

    //@Test
    //title ?????? content ??? 1??? ???????????? ??????
    //gno ??? ?????? 200?????? ?????? ????????? ??????
    public void testSelectQuery(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        //2?????? ????????? or??? ???????????? ??????
        builder.and(exTitle.or(exContent));
        //gno??? 200 ?????? ?????? ??????
        BooleanExpression exGno = qGuestBook.gno.lt(202L);
        builder.and(exGno);

        //?????? ??????
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        //??????
        for(GuestBook guestBook : result){
            System.out.println(guestBook);
        }
    }

    //@Test
    public void insertBoards(){
        Random r = new Random();
        for(int i=1; i<=100; i=i+1){
            Long num = 1 + Long.valueOf(r.nextInt(5));
            Member member = memberRepository.findById(num).get();
            Board board = Board.builder()
                    .title("??????..." + i)
                    .content("??????..." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        }
    }

    @Test
    public void addMemberRole() {
        Member member = memberRepository.findByMno(2L, false).get();
        System.out.println(member);
        member.addMemberRole(MemberRole.MEMBER);
        member.addMemberRole(MemberRole.ADMIN);
        System.out.println(member);
        memberRepository.save(member);
    }

//    //@Test
//    public void insertReplys(){
//        Random r = new Random();
//        for(long i=1; i<=300; i=i+1){
//            Board board = Board.builder()
//                    .bno((long)(r.nextInt(100) + 1))
//                    .build();
//
//            Reply reply = Reply.builder()
//                    .rno(i)
//                    .text("??????..." + i)
//                    .replyWriter("??????")
//                    .board(board)
//                    .build();
//            replyRepository.save(reply);
//        }
//    }
//
//    @Commit
//    @Transactional
//    //@Test -> ????????? PK ?????? ??????! ??????! ??????!!
//    public void testDeleteMember() {
//        String email = "member11@study.hard";
//        Member member = Member.builder().email(email).build();
//        reviewRepository.deleteByMember(member);
////        memberRepository.deleteById(email);
//    }

    @Commit
    @Transactional
    //@Test
    public void insertPlans() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Plan plan = Plan.builder().title("Plan is ..." +i).build();
            System.out.println("------------------------------------------");
            planRepository.save(plan);
            int count = (int)(Math.random() * 5) + 1; //1,2,3,4
            for(int j = 0; j < count; j++){
                PlanImage planImage = PlanImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .plan(plan)
                        .imgName("test"+j+".jpg").build();
                planImageRepository.save(planImage);
            }
            System.out.println("===========================================");
        });
    }

    //@Test
    public void insertPlanReviews() {
        //200?????? ????????? ??????
        IntStream.rangeClosed(1, 200).forEach(i -> {
            //Plan ??????
            Long pno = (long) (Math.random() * 100) + 1;
            //????????? ??????
            Long mno = ((long) (Math.random() * 100) + 1);
            Member member = Member.builder().mno(mno).build();
            Review movieReview = Review.builder()
                    .member(member)
                    .plan(Plan.builder().pno(pno).build())
                    .grade((int) (Math.random() * 5) + 1)
                    .text("??? ????????? ?????? ??????..." + i)
                    .build();
            reviewRepository.save(movieReview);
        });
    }
}
