package com.example.secondproject.controller;

import com.example.secondproject.wishlist.ResultListVO;
import com.example.secondproject.wishlist.WishListDto;
import com.example.secondproject.wishlist.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private WishListService wishListService;

    // 1. 검색 API(GET)
    @GetMapping("/search")
    public WishListDto search(@RequestParam String searchQuery) {
        // Naver 검색 지역 API를 통해서 조회
        WishListDto wishListDto = wishListService.search(searchQuery);

        return wishListDto;
    }

    // 2. 검색리스트 추가 API(POST)
    @PostMapping("/resultadd")
    public List<ResultListVO> resultAdd(@RequestBody WishListDto wishListDto) {
        // 검색결과 DB에 등록하고 추가 등록한 검색결과 정보까지 포함하여 저장된 검색결과 리스트를 전달
        List<ResultListVO> resultListVOList
                = wishListService.addResult(wishListDto);

        return resultListVOList;
    }

    // 3. 검색리스트 목록 가져오기 API(GET)
    @GetMapping("/resultall")
    public List<ResultListVO> resultAll() {
        List<ResultListVO> resultListVOList = wishListService.allResult();
        return resultListVOList;
    }

    // 4. 방문 추가 API(POST)
    @PostMapping("/resultvisit/{resultId}")
    public boolean resultVisit(
            @PathVariable("resultId") Integer resultId) {
        boolean result = wishListService.addVisitCount(resultId);
        return result;
    }

    // 5. 검색리스트 삭제 API(POST)
    @PostMapping("/resultdelete/{resultId}")
    public boolean resultDelete(
            @PathVariable("resultId") Integer resultId) {
        boolean result = wishListService.deleteResult(resultId);
        return result;
    }
}
