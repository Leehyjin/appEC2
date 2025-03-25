package org.zerock.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지 존재 여부
    private boolean prev;
    //다음 페이지 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName ="withAlL")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total<=0) {
            return; //메서드 종료
        }

        this.page = pageRequestDTO.getPage(); //1
        this.size = pageRequestDTO.getSize(); //10

        this.total = total;
        this.dtoList = dtoList;

        this.end =(int)(Math.ceil(this.page/10.0)) * 10;
        this.start = this.end - 9;

        int last = (int)(Math.ceil((total/(double)size)));

        this.end = end>last? last: end;

        this.prev = this.start > 1;

        this.next = total> this.end * this.size;
    }
}
