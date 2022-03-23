package kijin.bang.keygenie.repository;

import kijin.bang.keygenie.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"roleSet"}, type=EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.fromSocial = :social and m.mno = :mno")
    Optional<Member> findByMno(Long mno, boolean social);

    @EntityGraph(attributePaths = {"roleSet"}, type=EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmail(String email);
}
