package kijin.bang.keygenie.service;

import kijin.bang.keygenie.dto.ReviewDTO;
import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.Plan;
import kijin.bang.keygenie.entity.Review;

import java.util.List;

public interface ReviewService {
    //Plan에 해당하는 리뷰를 가져오기
    List<ReviewDTO> getList(Long pno);

    //리뷰 등록
    Long register(ReviewDTO reviewDTO);

    //리뷰 삭제
    void remove(Long reviewNum);

    //리뷰 수정
    void modify(ReviewDTO reviewDTO);

}
