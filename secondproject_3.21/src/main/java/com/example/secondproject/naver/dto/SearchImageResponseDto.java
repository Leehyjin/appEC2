package com.example.secondproject.naver.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchImageResponseDto {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    // items
    private List<SearchImageResponseDto.SearchImageItem> items;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class SearchImageItem {
        private String title;
        private String link;
        private String thumbnail;
        private String sizeheight;
        private String sizewidth;
    }
}

