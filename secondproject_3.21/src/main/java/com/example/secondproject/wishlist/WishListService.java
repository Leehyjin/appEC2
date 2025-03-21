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

    @Autowired //인스턴스를 만든 jpa 객체 주입
    private ResultRepository resultRepository;

    public WishListDto search(String paramQuery) {
        WishListDto wishListDto = new WishListDto(); //제목, 카테고리, 지번, 도로면 주소, 홈페이지 주소, 이미지 주소
        //를 받아서 wishListDto 인스턴스 생성


        SearchRegionRequestDto searchRegionRequestDto = new SearchRegionRequestDto();
        searchRegionRequestDto.setQuery(paramQuery); //네이버 지역 검색 API호출하여 받은 응답값을 DTO 전달

        SearchRegionResponseDto searchRegionResponseDto = naverAPIClient.searchRegion(searchRegionRequestDto);

        List<SearchRegionResponseDto.SearchRegionItem> searchRegionItemList
                = searchRegionResponseDto.getItems(); //searchRegionItemList
        if (searchRegionItemList != null && searchRegionItemList.size() > 0) {
            //첫 번째 항목을 가져옴(변수 searchRegionItem)
            SearchRegionResponseDto.SearchRegionItem searchRegionItem
                    = searchRegionItemList.get(0);

            //wishListDto에 값을 설정
            wishListDto.setTitle(searchRegionItem.getTitle());
            wishListDto.setCategory(searchRegionItem.getCategory());
            wishListDto.setJibunAddress(searchRegionItem.getAddress());
            wishListDto.setRoadAddress(searchRegionItem.getRoadAddress());
            wishListDto.setHomepageLink(searchRegionItem.getLink());

        }
    }



    // 2. NAVER 이미미 검색 호출해서 dto 값 매핑
    SearchImageRequestDto searchImageRequestDto = new SearchImageRequestDto(); //이미지 요청 인스턴스 생성
    searchImageRequestDto.setQuery(paramQuery);

    SearchImageResponseDto searchImageResponseDto
            = naverAPIClient.searchImage(searchImageRequestDto);

    List<SearchImageResponseDto.SearchImageItem> searchImageItemList
                = searchImageResponseDto.getItems();

    if(searchImageItemList != null && searchImageItemList.size()>=0){
        SearchImageResponseDto.SearchImageItem searchImageItem
                = searchImageItemList.get(0);

      wishListDto.setImageLink(searchImageItem.getLink());}

    return  wishListDto;}



    public boolean deleteResult(Integer resultId){
        boolean isSucess = false;

        try{
            resultRepository.deleteById(resultId);
            isSucess = true;}
        catch (Exception e){e.printStackTrace();}

        return  isSucess;
    }


    public boolean addVisitCount(Integer resultId){
        boolean isSucess = false;

        try{
            //실제 방문 카운트 증가 로직
            Optional<Result> optionalResult = resultRepository.findById(resultId);
            if(optionalResult.isPresent()){
                Result result = optionalResult.get(); //id값을 가져온다.
                //resultId = 123
                result.setVisitCount(result.getVisitCount()+1);
                result.setVisitIs(true);
                result.setLastVisitDate(LocalDateTime.now());

                resultRepository.save(result);
            }
                isSucess = true;}

            catch (Exception e){e.printStackTrace();}

            return  isSucess;
        }

















