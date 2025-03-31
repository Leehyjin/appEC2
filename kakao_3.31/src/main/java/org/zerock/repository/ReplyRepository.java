package org.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Reply;

//Reply는 jpa엔티티 클래스
//Reply 엔티티와 매핑된 데이터베이스 테이블에서 데이터를 처리하는 리포지토리
public interface ReplyRepository extends JpaRepository<Reply, Long>{
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(@Param("bno") Long bno, Pageable pageable);


    //게시물 번호로 댓글 삭제
    //반환 값이 없다.
    void deleteByBoard_Bno(Long bno);
}




