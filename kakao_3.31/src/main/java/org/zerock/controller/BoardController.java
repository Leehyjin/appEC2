package org.zerock.controller;

mport org.springframework.ui.Model;
import groovy.util.logging.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.Service.BaordService;
import org.zerock.dto.PageRequestDTO;

@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {
    private final BaordService baordService;
    //이 필드는 한번 초기화 된 후 그 값을 변경할 수 없다.

    @Value("${org.zercock.upload.path}")
    private String uploadPath;
    //${} 안에 있는 값은 외부 설정파일에서 해당 값을 찾아 가져온다.
    //파일 업로드 경로를 저장하는 변수

    @GetMapping("/list")
    public void List(PageRequestDTO pageRequestDTO, Model model){
     = boa



    }






}
