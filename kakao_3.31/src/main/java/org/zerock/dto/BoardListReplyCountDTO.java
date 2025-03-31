package org.zerock.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BoardListReplyCountDTO {

    private Long bno;

    private String title;

    private String writer;

    private  LocalDateTime regDate;

    private Long replyCount; //정수형 int보다 2배 숫자 저장

}
