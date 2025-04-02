package org.zerock.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( indexes = {@Index(name= "idx_reply_board_bno", columnList = "board_bno")})
//해당 클래스가 데이터베이스 테이블에 매핑된 엔티티임을 지정
// 테이블의 세부 사항을 설정
// index-columnList: 인덱스를 적용할 컬럼 지정
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;
    private String replyer;

    @Override
    public String toString(){

        return "replyText" + replyText + ", replyer: " + replyer + ", " +
                (board != null ? board.getContent()+"-" + board.getTitle(): " ");
    }

    public void changText(String text){
        this.replyText = text;
    }

    public void setBoard(Board board){
        this.board = board;
    }
}
