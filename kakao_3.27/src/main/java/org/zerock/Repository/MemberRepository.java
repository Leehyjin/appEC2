package org.zerock.Repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(String mid);

    @EntityGraph(attributePaths = "roleSets")
    Optional<Member> findByEmail(String email);


    @Modifying //데이터를 수정하는 쿼리
    @Transactional //메서드 안의 작업들을 하나의 묶음으로 처리(데이터베이스 작업 단위)
    @Query("update Member m set m.mpw =:mpw where m.mid = :mid")
    void updatePassword(@Param("mpw") String password, @Param("mid") String mid);


}
