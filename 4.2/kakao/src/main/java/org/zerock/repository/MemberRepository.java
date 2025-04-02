package org.zerock.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    // mid값을 가진 member정보와 role의 정보를 모두 조회
    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(@Param("mid") String mid);

    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select m from Member m where m.mid = :mid and m.social = true")
    Optional<Member> getWithRolesIsSocial(@Param("mid") String mid);

    @EntityGraph(attributePaths = {"roleSet"})
    Optional<Member> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Member m set m.mpw = :mpw where m.mid = :mid")
    void updatePassword(@Param("mpw") String password, @Param("mid") String mid);
}
