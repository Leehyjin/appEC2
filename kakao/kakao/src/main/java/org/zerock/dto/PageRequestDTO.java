package org.zerock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest; //페이지 번호와 페이지 크기를 지정하여 원하는 범위의 데이터만 가져올 수 있다.
import org.springframework.data.domain.Pageable; //PageRequest는 Pageable를 구현한 클래스이며, 주로 데이터베이스에서 페이징된 결과를 가져오는데 사용
import org.springframework.data.domain.Sort; //정렬 조건(데이터를 오름차순 또는 내림차순)

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;

    private  String keyword;

    public String[] getTypes() {
        if(type == null || type.isEmpty()){
            return  null;
        } //
        return  type.split("");}

    public Pageable getPageable(String...props){
        //Pagealbe 타입의 값을 반환하는 메서드
        return  PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink(){

        if(link == null){
            //StrinBuilder는 문자열을 결합할 수 있는 클래스
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

        if(type != null && type.length()>0){
            builder.append("&type=" + type);
        }

        if(keyword != null){
            try{
                builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
            }catch (UnsupportedEncodingException e){}
        }
        return link = builder.toString();

        } return  link;
    }

}











