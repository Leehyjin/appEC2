package org.zerock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    private String link;

    private String type;
    private String keyword; //검색할 문자들

    private boolean isToggle;

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }   return type.split("");
    }

    public Pageable getPageable(String...props){
        return  PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }   // 2페이지를 10개 사이즈로 보여주고 내림차순으로 할건데 실제 구현할 때 수정날짜를 기준으로 내림차순 할건데
        // 구현할 때 매개변수로 넣는다?

    public String getLink(){
        if(link == null){
            StringBuilder builder  = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);


            if(type !=null && type.length()>0){
                builder.append("&type=" + type);
            }
            if(keyword !=null){
                try{builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            //키워드에 값이 있으면
            builder.append("&isToggle=" +this.isToggle);
            link = builder.toString();


        }
        //link에 값이 있으면
            return link;
    }

}
