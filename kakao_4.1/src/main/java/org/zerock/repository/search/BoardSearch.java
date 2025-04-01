package org.zerock.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.domain.Board;
import org.zerock.dto.BoardListAllDTO;
import org.zerock.dto.BoardListReplyCountDTO;


public interface BoardSearch {
    //검색 조건으로 게시물 리스트 전부 조회
    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    //검색 조건으로 게시물 리스트 및 댓글 수 전부 조회
    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    //검색 조건으로 게시물 리스트 및 댓글, 첨부파일 리스트를 전부 조회
    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

    //검색 조건으로 게시물 리스트 및 댓글, 첨부파일 리스트를 전부 조회 (new)
    Page<BoardListAllDTO> searchWithAllNew(String[] types, String keyword, Pageable pageable);


}
