package org.zerock.dto;

import java.util.List;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResponseDTO<T> {

    //페이징 내부 정보들
    private  int page; //페이지 쪽
    private  int size;

    //페이징 화면에 보여줄 정보
    private  int total;
    private int start;
    private  int end;
    private boolean prev;
    private boolean next;

    //실제 할일 목록 정보들
    private List<T> dtoList;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<T> dtoList, int total){

        this.page = pageRequestDTO.getPage();// 페이지 현재 정보
        this.size = pageRequestDTO.getSize(); //10

        this.dtoList = dtoList;
        this.total = total;


        this.end =  (int)(Math.ceil(this.page / 10.0 )) *  10;
        this.start = this.end - 9;
        int last =  (int)(Math.ceil((total/(double)size)));
        this.end =  end > last ? last: end;
        this.prev = this.start > 1;
        this.next =  total > this.end * this.size;
    }


}
