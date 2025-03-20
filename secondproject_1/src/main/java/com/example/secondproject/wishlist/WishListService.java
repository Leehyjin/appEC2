package com.example.secondproject.wishlist;

import com.example.secondproject.jpa.Result;
import com.example.secondproject.jpa.ResultRepository;
import com.example.secondproject.naver.NaverAPIClient;
import com.example.secondproject.naver.dto.SearchImageRequestDto;
import com.example.secondproject.naver.dto.SearchImageResponseDto;
import com.example.secondproject.naver.dto.SearchRegionRequestDto;
import com.example.secondproject.naver.dto.SearchRegionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WishListService {

    @Autowired
    private NaverAPIClient naverAPIClient;

    @Autowired
    private ResultRepository resultRepository;

    /**
     * Naver 지역 검색 API 호출하여 받은 응답값을 전달
     *
     * @param paramQuery
     * @return
     */
    public WishListDto search(String paramQuery) {
        WishListDto wishListDto = new WishListDto();


        // 2. NaverAPI 이미지 검색 호출해서 dto값 매핑
        SearchImageRequestDto searchImageRequestDto
                = new SearchImageRequestDto();
        searchImageRequestDto.setQuery(paramQuery);

        SearchImageResponseDto searchImageResponseDto
                = naverAPIClient.searchImage(searchImageRequestDto);
        List<SearchImageResponseDto.SearchImageItem> searchImageItemList
                = searchImageResponseDto.getItems();

        if (searchImageItemList != null && searchImageItemList.size() > 0) {
            SearchImageResponseDto.SearchImageItem searchImageItem
                    = searchImageItemList.get(0);

            wishListDto.setImageLink(searchImageItem.getLink());
        }

        return wishListDto;
    }

    public boolean deleteResult(Integer resultId) {
        boolean isSuccess = false;

        try {
            resultRepository.deleteById(resultId);
            isSuccess = true;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public boolean addVisitCount(Integer resultId){
        boolean isSuccess = false;

        try{Optional<Result> optionalResult = resultRepository.findById(resultId);

            //id 값이 1번을 optionResult에 저장

            if(optionalResult.isPresent()){
                Result result = optionalResult.get();
                result.setVisitCount(true);
                result.setLastVisitDate(LocalDateTime.now());

                resultRepository.save(result);
                //resultRepository는 리포지토리 인터페이스 객체
                //save는 resultRepository의 메서드로, result 객체를 <데이터베이스>에 저장하는 기능

            }

            isSuccess = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return isSuccess; //성공 여부 반환, 정상적으로 작업이 완료하면 true, 예외발생하면
        //resultId에 해당하는 Result가 존재하지 않으면 false를 반환
    }
}

