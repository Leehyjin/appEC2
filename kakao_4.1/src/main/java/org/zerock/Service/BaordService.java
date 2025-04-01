package org.zerock.Service;

import org.zerock.domain.Board;
import org.zerock.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public interface BaordService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    //댓글 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    //인터페이스 안에서만 사용할 수 있다.
    //인터페이스를 구현하는 클래스가 해당 메서드를 구현하지 않더라도 자동으로
    //사용할 수 있는 구현을 제공(defalut 키워드를 사용하면 메서드에 구현내용도 제공할 수 있다. )
    default Board dtoToEntity(BoardDTO boardDTO){
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if(boardDTO.getFileNames() !=null){
            boardDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.substring(8).split("_");
                board.addImage(arr[0], arr[1]);
            });
        } //값이 있으면
        return  board;
    }

    default BoardDTO entityToDTO(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames =
                board.getImageSet().stream().sorted().map(boardImage ->
                        boardImage.getUuid() + "_" + boardImage.getFileName()).collect(Collectors.toList());

        //스트림 요소들을 정렬한다.(기본 오름차순)
        //boardImage getImageSet()의 요소들
        //map() 스트림의 각 요소를 다른 형태로 변환하는 함수





        boardDTO.setFileNames(fileNames);

        return  boardDTO;
    }

        PageResponseDTO<BoardListAllDTO> listWithAll(
                PageRequestDTO pageRequestDTO);

    }


