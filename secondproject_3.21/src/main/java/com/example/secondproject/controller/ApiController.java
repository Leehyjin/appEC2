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

    //1. 검색 API(GET)
    @GetMapping
    public WishListDto search(@RequestParam String searchQuery) {
        //naver 검색 지역 api를 통해서 조회
        WishListDto wishListDto = WishListService.search(searchQuery);

        return wishListDto;
    }

    //2. 검색리스트 추가 api(post)
    @PostMapping("/resultadd")
    public List<ResultListVO> resultAdd(@RequestBody WishListDto wishListDto) {
        //wishListDto는 Http 요청 본문에서 전달된 데이터를 wishListDtO 객체로 매핑한다.
        List<ResultListVO> resultListVOList
                = wishListService.addResult(wishListDto);
        //wishListDto 매개변수 값을 가져온다.

        return resultListVOList;


        //검색리스트를 DB에 저장하는 POST 방식의 메소드
    }

    //3. 검색리스트 목록 가져오기 api(get)
    @GetMapping("/resultall")
    public List<ResultListVO> resultAll() {
        List<ResultListVO> resultListVOList = wishListService.allResult();
        return resultListVOList;
    }


    //4. 방문 추가 api(post)
    @PostMapping("resultvisit/{resultId}")
    public boolean resultVisit(@PathVariable("resultId") Integer resultId) {
        boolean result = wishListService.addVisitCount(resultId);

        return result;
    }

    //5. 검색리스트 삭제 Api(post)
    @PostMapping("/resultdelete/{resultId}")
    public boolean resultDelete(
            @PathVariable("resultId") Integer resultId) {

        boolean result = wishListService.deleteResult(resultId);
        return result;
    }

}



















