package com.example.secondproject.naver;

import com.example.secondproject.naver.dto.SearchImageRequestDto;
import com.example.secondproject.naver.dto.SearchImageResponseDto;
import com.example.secondproject.naver.dto.SearchRegionRequestDto;
import com.example.secondproject.naver.dto.SearchRegionResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverAPIClient {
    // 1. 지역검색 API Call 메소드
    public SearchRegionResponseDto searchRegion(SearchRegionRequestDto searchRegionRequestDto) {
        // spring boot에서 API Call을 하는 라이브러리
        // 비슷한 기능을 하는 자바스크립트 -> axios, fetch, ajax 등
        SearchRegionResponseDto response = new RestTemplate().exchange(
            getURI("/v1/search/local.json", searchRegionRequestDto.getQuery()),
            HttpMethod.GET,
            getHttpEntity(),
            SearchRegionResponseDto.class
        ).getBody();

        return response;
    }

    // 2. 네이버 이미지 검색 API Call 메소드
    public SearchImageResponseDto searchImage(SearchImageRequestDto searchImageRequestDto) {
        // spring boot에서 API Call을 하는 라이브러리
        // 비슷한 기능을 하는 자바스크립트 -> axios, fetch, ajax 등
        SearchImageResponseDto response = new RestTemplate().exchange(
                getURI("/v1/search/image", searchImageRequestDto.getQuery()),
                HttpMethod.GET,
                getHttpEntity(),
                SearchImageResponseDto.class
        ).getBody();

        return response;
    }

    // 공통화 리팩토링
    // 1. HttpHeader관련 공통화
    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "vugnjD8b0jNfx8TPyF7x");
        headers.set("X-Naver-Client-Secret", "WZaS9ayqeP");
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    // 2. URI관련 공통화
    private URI getURI(String path, String query) {
        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com")
                .path(path)
                .queryParam("query", query)
                .encode()
                .build()
                .toUri()
                ;

        return uri;
    }

    // 3. HttpEntity 관련 공통화
    private HttpEntity getHttpEntity() {
        HttpEntity httpEntity = new HttpEntity(getHttpHeaders());
        return httpEntity;
    }
}
