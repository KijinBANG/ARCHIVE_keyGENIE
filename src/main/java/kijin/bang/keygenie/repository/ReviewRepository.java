package kijin.bang.keygenie.repository;

import kijin.bang.keygenie.entity.Member;
import kijin.bang.keygenie.entity.Plan;
import kijin.bang.keygenie.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //Plan 정보를 가지고 모든 Plan 의 모든 리뷰를 가져오는 메소드
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByPlan(Plan plan);

    //member 에 해당하는 데이터를 삭제하는 메소드
    @Modifying
    @Query("delete from Review pr where pr.member = :member")
    void deleteByMember(Member member);

    //Plan 이 지워질 때 같이 데이터를 지우는 메소드
    void deleteByPlan(Plan plan);

}
