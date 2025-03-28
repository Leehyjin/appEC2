package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;
import org.zerock.Repository.search.BoardSearch;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
//BoardSeach와  JpaRepository<Board는 데이터베이스 테이블과 매핑되는 클래스, ID타입>인터페이스 상속받아

    List<Board> findByTitleWriter(String title, String writer);
    List<Board> findByWriterIn(List<String> writers);
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    //jpa > jpql
    @Query("select b from Board b where title =:title and writer =:writer")
    List<Board> findFromTitleWriter(@Param("title") String title, @Param("writer")  String writer);

    @Query("select b from Board b where writer in :writers")
    List<Board> findFromWriters(@Param("writers") List<String> writers);

    @Query("select b from Board b where where b.title like concat('%', :keyword, '%')")
    //concat(): 문자열을 이어붙이는 함수
    // like: title 필드가 특정 문자열을 포함하는지 확인할 때 사용
    Page<Board> findKeyword(@Param("keyword") String keyword, Pageable pageable);
    // 1. @Query는 Board <엔티티>에서 title 필드가 Keyword를 포함하는 게시물들을 찾는다.
    // 2. 페이징 처리: 결과가 Page<Board> 형태로 반환된다. 검색된 게시물 뿐만 아니라 Page로 인해
    //총 페이지 수, 현재 페이지 등 페이지에 필요한 정보도 함께 반환된다.
    //Pageable를 매개변수로 넣는 이유는 페이징 처리를 위해서

    // N개수 만큼 select하는 것이 아니라 한꺼번에 imageSet 가져오는 방법
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board B where b.bno =:bno")
    Optional<Board> findByWithImages(Long bno);






}
