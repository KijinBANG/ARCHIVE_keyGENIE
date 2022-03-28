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

//    //@Test //-> 이거 안됨! PK 관련된 문제같은데...  '방금만든놈!'은 지워지지만, '꼬리가 긴 놈!'은 삭제가 안됨! 공부하자!
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
                .title("제목 변경")
                .content("내용 변경")
                .writer("사용자 변경")
                .build();
        guestBookRepository.save(guestBook);
    }

    //@Test
    public void testQuery(){
        //paging 설정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //Querydsl Entity 가져오기
        QGuestBook qGuestBook = QGuestBook.guestBook;

        //조건 생성
        BooleanBuilder builder = new BooleanBuilder();
        //title에 1을 포함한 데이터를 조회
        String keyword = "1";
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        //조건을 추가
        builder.and(expression);
        //검색 수행
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        //출력
        for(GuestBook guestBook : result){
            System.out.println(guestBook);
        }
    }

    //@Test
    //title 이나 content 에 1이 포함되어 있고
    //gno 의 값이 200보다 작은 데이터 조회
    public void testSelectQuery(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        //2개의 조건을 or로 연결해서 추가
        builder.and(exTitle.or(exContent));
        //gno가 200 보다 작은 조건
        BooleanExpression exGno = qGuestBook.gno.lt(202L);
        builder.and(exGno);

        //검색 수행
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        //출력
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
                    .title("제목..." + i)
                    .content("내용..." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        }
    }

    //@Test
    public void addMemberRole() {
        Member member = memberRepository.getById(6L);
        System.out.println(member);
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
//                    .text("댓글..." + i)
//                    .replyWriter("손님")
//                    .board(board)
//                    .build();
//            replyRepository.save(reply);
//        }
//    }
//
//    @Commit
//    @Transactional
//    //@Test -> 이것도 PK 관련 문제! 공부! 공부!!
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

    @Test
    public void insertPlanReviews() {
        //200개의 리뷰를 등록
        IntStream.rangeClosed(1, 200).forEach(i -> {
            //Plan 번호
            Long pno = (long) (Math.random() * 100) + 1;
            //리뷰어 번호
            Long mno = ((long) (Math.random() * 100) + 1);
            Member member = Member.builder().mno(mno).build();
            Review movieReview = Review.builder()
                    .member(member)
                    .plan(Plan.builder().pno(pno).build())
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 계획에 대한 느낌..." + i)
                    .build();
            reviewRepository.save(movieReview);
        });
    }
}
