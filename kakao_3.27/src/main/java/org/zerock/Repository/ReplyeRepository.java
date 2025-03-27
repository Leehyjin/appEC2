package org.zerock.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Reply;
import org.springframework.data.domain.Page;


public interface ReplyeRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listBoard(@Param("bno") Long bno, Pageable pageable);

    //게시물 번호로 댓글 삭제
    void deleteBoard_Bno(Long bno);



}
