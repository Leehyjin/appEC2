package org.zerock.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.repository.ReplyRepository;
import org.zerock.domain.Reply;
import org.zerock.dto.PageRequestDTO;
import org.zerock.dto.PageResponseDTO;
import org.zerock.dto.ReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository; //인터페이스도 필드로 사용할 수 있다고?
    private final ModelMapper modelMapper; //객체 간의 데이터 변환(dto <> 엔티티 )

    @Override
    public Long register(ReplyDTO replyDTO){

        Reply reply  = modelMapper.map(replyDTO, Reply.class);
        //엔티티: 데이터베이스와 직접적으로 연결되는 객체. 엔티티 객체가 데이터베이스의 테이블과 매핑
        //DTO : 브라우저, 서버 간 데이터를 주고 받을 때 사용하는 데이터 전달용 객체
        //DTO 객체에 있는 데이터를 Reply엔티티로 옮겨 놓는 과정

        //댓글 등록
        Reply newReply = replyRepository.save(reply);
        //replyDTO 값을 Reply 엔티티로 변환하고 그걸 매개변수로 삼아서
        //Reply가 참조하는 Board 테이블의 bno와 bno를 비교하여 리플 화면 페이지
        //Reply 조회하는 인스턴스에 저장
        Long rno= newReply.getRno();

        return rno;}
        //rno가 화면에 보여줄 값 (팝업창으로 나오는 거야? - HTML로 확인해봐)

    @Override
    public ReplyDTO read(Long rno){
        Optional<Reply> replyOptional = replyRepository.findById(rno);
        Reply reply = replyOptional.orElseThrow(); //Optional 객체에서 값이 없을 때 예외를 던지고
        //값이 있으면 그 값을 반환하는 예외 처리 코드
        //클래스 타입이 Reply이니까 클래스 메서드 사용할 수 있다.

        return  modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO){
       Optional<Reply> replyOpitonal  = replyRepository.findById(replyDTO.getRno());
        //ReplyRepository: bno랑 reply가 참조하는 board 테이블의 bno 비교해서 reply 내용 조회
        Reply reply = replyOpitonal.orElseThrow();
        reply.changText(replyDTO.getReplyText());

        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno){
        replyRepository.deleteByBoard_Bno(rno);  //deleteByBoard_Bno 이 메소드만 보고 어떻게 삭제하는지 알아? 챗gpt는 제대로 안 알려줌
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO){

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending()
        );

       Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        //reply이 참조하는 board의 bno와 파라미터 bno 비교하여 Reply 모두 조회
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                //modelMapper는 라이브러리: reply -> ReplyDTO 객체 변환
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }


}





