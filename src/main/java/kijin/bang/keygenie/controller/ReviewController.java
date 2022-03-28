package kijin.bang.keygenie.controller;

import kijin.bang.keygenie.dto.ReviewDTO;
import kijin.bang.keygenie.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/spring/reviews")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}/list")
    public ResponseEntity<List<ReviewDTO>> list(@PathVariable("id") Long mno){
        List<ReviewDTO> reviewDTOList = reviewService.getList(mno);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{id}")//'후기 작성을 위한 모달창'으로 부터 전달받은 데이터를 DB로 보내는 method
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO){
        log.info("----------------------add MovieReview------------------------");
        log.info("reviewDTO: " + reviewDTO);
        Long reviewNum = reviewService.register(reviewDTO);
        log.info("reviewNum: " + reviewNum);
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

    @PutMapping("/{id}/{reviewNum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewNum, @RequestBody ReviewDTO reviewDTO){
        log.info("------------------------modify MovieReview-----------------------" + reviewNum);
        log.info("reviewDTO: " + reviewDTO);
        reviewService.modify(reviewDTO);
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{reviewNum}")
    public ResponseEntity<Long> removeReview( @PathVariable Long reviewNum){
        log.info("-----------------modify removeReview-----------------------");
        log.info("reviewNum: " + reviewNum);
        reviewService.remove(reviewNum);
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

}