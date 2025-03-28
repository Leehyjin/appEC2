package org.zerock.Repository.search;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
// JpaRepository는 Member 엔티티의 기본적인 CRUD 메서드를 자동으로 제공한다.
// save(), findByid(), findAll(), deleteByid()등과 같은 메서드 제공

    @EntityGraph(attributePaths = {"roleSet"}) //지연로딩을 즉시로딩으로 변경하는
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(@Param("mid") String mid);
    //roleSet은 Set<MemberRole>타입으로, Member엔티티와 MemberRole 값 타입 컬렉션 간의 일대다 관계
    //Member 엔티티를 조회할 때 roleSet 컬렉션의 데이터는 실제로 접근할 때 로딩되는데
    //Member 엔티티와 함께 roleSet도 함께 즉시 로딩되도록 한다.

    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select m from Member m where m.mid= :mid and m.social =true")
    Optional<Member> getWithRolesIsSocail((@Param("mid"))String  mid);

    @EntityGraph(attributePaths = {"roleSet"})
    Optional<Member> findByEmail(String email);
    //Member 엔티티에서 email를 조회하는 메서드: email을 매개변수로 받아서 해당 이메일로 회원을 조회하는 메서드

    @Modifying
    @Transactional //메서드나 클래스에 트랙잭션을 적용하여, 해당 메서드나 클래스 내에서 수행하는
    //데이터베이스 작업들이 하나의 트랙잭션으로 묶이도록 한다.
    // mpw(비밀번호)를 새로운 값으로 변경합니다.
    @Query("update Member m set m.mpw = :mpw where m.mid = :mid")
    void updatePassword(@Param("mpw") String password, @Param("mid")String mid);
}
