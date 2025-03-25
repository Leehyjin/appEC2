package org.zerock.Repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.domain.Board;
import org.zerock.dto.BoardListAllDTO;
import org.zerock.dto.BoardListReplyCountDTO;


public interface BoardSearch {

    Page<Board> search1(Pageable pagealbe);

    Page<Board> saerchAll(String[] types, String keyword, Pageable pageable);








}
