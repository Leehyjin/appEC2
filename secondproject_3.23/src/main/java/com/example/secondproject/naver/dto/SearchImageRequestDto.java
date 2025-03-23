package com.example.secondproject.naver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchImageRequestDto {
    private String query;
    private Integer display = 10;
    private Integer start = 10;
    private String sort = "random";
}
