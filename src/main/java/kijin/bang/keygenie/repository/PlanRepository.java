package kijin.bang.keygenie.repository;

import kijin.bang.keygenie.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("select p, max(pi), avg(coalesce(r.grade,0)), count(distinct r) from Plan p " +
            "left outer join PlanImage pi on pi.plan = p left outer join Review r on r.plan = p group by p ")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select p, pi, avg(coalesce(r.grade,0)), count(r) " +
            "from Plan p left outer join PlanImage pi on pi.plan = p left outer join Review r on r.plan = p " +
            "where p.pno = :pno group by pi")
    List<Object[]> getPlanWithAll(Long pno);
}
