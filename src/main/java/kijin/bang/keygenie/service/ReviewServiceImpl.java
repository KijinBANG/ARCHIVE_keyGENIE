package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.ReviewDTO;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.Plan;
import kijin.bang.keygenie.entity.Review;
import kijin.bang.keygenie.repository.MemberRepository;
import kijin.bang.keygenie.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getList(Long id) {
        log.info(id);
        Plan plan = Plan.builder().pno(id).build();
        List<Review> result = reviewRepository.findByPlan(plan);
        return result.stream().map(planReview -> entityToDTO(planReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        log.info("reviewDTO: " + reviewDTO);
        Review review = dtoToEntity(reviewDTO);
        log.info("review: " + review);
        reviewRepository.save(review);
        return review.getReviewNum();
    }

    @Override
    public void remove(Long reviewNum) {
        log.info("remove " + reviewNum);
        reviewRepository.deleteById(reviewNum);
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewNum());
        if(result.isPresent()){
            Review planReview = result.get();
            planReview.changeGrade(reviewDTO.getGrade());
            planReview.changeText(reviewDTO.getText());
            reviewRepository.save(planReview);
        }
    }

    Review dtoToEntity(ReviewDTO reviewDTO){
        String email = reviewDTO.getEmail();
        Member member = memberRepository.findByEmail(email).get();
        Review review = Review.builder()
                .reviewNum(reviewDTO.getReviewNum())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .plan(Plan.builder().pno(reviewDTO.getId()).build())
                .member(member)
                .build();

        return review;
    }

    ReviewDTO entityToDTO(Review review) {
        ReviewDTO dto = ReviewDTO.builder()
                .reviewNum(review.getReviewNum())
                .id(review.getPlan().getPno())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();

        return dto;
    }
}
