package org.zerock.Service;

import org.zerock.dto.PageResponseDTO;
import org.zerock.dto.PageRequestDTO;
import org.zerock.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    //특정 게시물의 댓글 목록 조회
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
