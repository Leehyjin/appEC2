package org.zerock.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.domain.Board;
import org.zerock.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    List<Board> findByTitleAndWriter(String title, String writer);
    List<Board> findByWriterIn(List<String> writers);
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    //jpa @quert methord -> jpal
    @Query("select b from Board b where title = :title and writer = :writet")
    List<Board> findFromTitleWriter(@Param("title") String title, @Param("writer") String writer);
    @Query("select b from Board b where writer in :writers")
    List<Board> findFromWriters(@Param("writers") List<String> writers);
    @Query("select b from Board b where b.title like concat('%', :keyword, '%')")
    Page<Board> findKeyword(@Param("keyword")String keyword, Pageable pageable);

    //N개수만 만큼 select 하는 것이 아니라 한꺼번에 imageSet로 가져오는 방법
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> findByWithImages(Long bno);

}
