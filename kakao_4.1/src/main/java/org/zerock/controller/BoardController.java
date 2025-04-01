package org.zerock.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.dto.*;
import org.zerock.Service.BaordService;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;


@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {
    private final BaordService baordService;

    @Value("${org.zerock.upload.path}")
    private String uploadPath;
    //@Values는 spring에서 제공하는 어노테이션으로, 외부설정파일이나 프로퍼티
    //파일로부터 값을 주입받을 때 사용

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
       PageResponseDTO<BoardListAllDTO> pageResponseDTO
               = baordService.listWithAll(pageRequestDTO);

        log.info("responseDTO: " + pageResponseDTO);

        model.addAttribute("responseDTO", pageResponseDTO);
        // 컨트롤러에서 데이터를 뷰로 전달하는 코드 1.뷰에서 참조할 이름(변수 이름) 2. 실제 데이터 객체
    }

    @PreAuthorize("hasRole('USER')")
    //Spring Security에서 사용하는 어노테이션
    //메서드가 실행 되기 전 사용자 권한 검사
    //현재 사용자가 USER라는 역할을 가지고 있는지, hasRole(): 권한 표현식 > true가 메서드를 실행될 수 있다.


    //Post 방식은 일반적으로 데이터를 생성하거나 제출할 때 사용된다. 예를 들어 사용자가 게시글 작성할 때 POST 요청을 통해 이뤄진다.
    @PostMapping("/register")
    public String registerPOST(
            @Valid BoardDTO boardDTO,    //@NotNull, @Size(min=2)와 같은 어노테이션이 BoardDTO클래스에 정의되어 있는지
            BindingResult bindingResult, //폼 데이터를 검증(사용가자 웹 폼에 입력한 데이터가 유효한지 확인)하고 검증 오류를 BindingResult에 저장한다.
            RedirectAttributes redirectAttributes) //리다이렉트 할 때 데이터를 전달하는데 사용되는 객체

    {   log.info("Post board register....");
        log.info(boardDTO);


        if(bindingResult.hasErrors()){
            log.info("hase errors........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return  "redirect:/board/register";
        }

        Long bno = baordService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return  "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()") //메서드 호출 전에 주어진 조건 검증(isAuthenticated(): 이 조건은 사용자가 인증된 상태인지 확인하는 조건,
    //즉 로그인된 사용자만 해당 메서드를 실행할 수 있게 한다.)
    @GetMapping({"/read", "/modify"}) //사용자가 /read 또는 /modify로 Get 요청을 보낼 때 이 메서드 호출
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = baordService.readOne(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PreAuthorize("principal.usernam ==  #boardDTO.writer")
    //메서드를 실행하기 전에 주어진 고건이 참인지 체크
    //사용자(principal)와 메서드 인자로 전달된 boardDTO.writer를 비교하는 조건
    // #은 메서드 파라미터를 불러오는 Spring EXpression Language
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {log.info("board modify post...." + boardDTO);

        if(bindingResult.hasErrors()){
            log.info("has errors.....");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        baordService.modify(boardDTO); //실제 게시판 내용 수정

        //모달창을 보이기 위한 속성
        redirectAttributes.addFlashAttribute("result", "modified");
        //리다이렉트 후 일회성 속성을 설정하여 데이터 전달하는 방법
        //이 값은 리다이렉트된 페이지에만 일시적으로 사용할 수 있다.
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        //ex. /board/modify?bno=123


        return "redirect:/board/read"; // 수정 성공 후 게시판 상세조회 페이지로 이동
    }

    @PreAuthorize("principal.username == #boardDTO.writer")
    //username은 사용자 이름
    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes rttr){
       Long bno = boardDTO.getBno();
       baordService.remove(bno);

       //게시물의 첨부파일 삭제
        log.info(boardDTO.getFileNames());
        List<String> fileName = boardDTO.getFileNames();
        if(fileName !=null && fileName.size()>0){
            removeFiles(fileName);
        }

        rttr.addFlashAttribute("result", "remove");
        return "redirect:/board/list";

    }

    //첨부파일 삭제
    public void removeFiles(List<String> files) {
        for (String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete(); //원본 파일 삭제

                //썸네일이 존재한다면 썸파일 파일 삭제
                if (contentType.startsWith("image")) {
                    File thumnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumnailFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    }
}





