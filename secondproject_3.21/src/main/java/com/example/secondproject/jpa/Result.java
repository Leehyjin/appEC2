package com.example.secondproject.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     // 검색결과저장 번호

    private String title;  // 검색결과 제목
    private String category; // 검색결과 카테고리
    private String jibunAddress; // 검색결과 지번주소
    private String roadAddress;  // 검색결과 도로명주소
    private String homepageLink; // 검색결과 홈페이지 주소
    private String imageLink;    // 검색결과 이미지 주소

    private boolean visitIs;    // 방문여부
    private int visitCount;     // 방문횟수
    private LocalDateTime lastVisitDate; // 마지막 방문날짜

    public boolean getVisitIs() {
        return this.visitIs;
    }
}
