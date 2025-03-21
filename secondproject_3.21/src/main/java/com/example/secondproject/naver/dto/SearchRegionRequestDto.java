package com.example.secondproject.naver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRegionRequestDto {
    private String query;
    private Integer display = 1;
    private Integer start = 1;
    private String sort = "random";
}
