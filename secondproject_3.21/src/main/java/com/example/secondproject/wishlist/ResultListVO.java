package com.example.secondproject.wishlist;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultListVO {
    private Integer id; // 검색리스트저장 번호
    private String title;  // 검색결과 제목
    private String category; // 검색결과 카테고리
    private String jibunAddress; // 검색결과 지번주소
    private String roadAddress;  // 검색결과 도로명주소
    private String homepageLink; // 검색결과 홈페이지 주소
    private String imageLink;    // 검색결과 이미지 주소

    private boolean visitIs;    // 방문여부
    private int visitCount;     // 방문횟수
    private LocalDateTime lastVisitDate; // 마지막 방문날짜
}
