package org.zerock.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.Repository.BoardRepository;

@Service //실제 로직이 있는 페이지
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BaordService{

    private final ModelMapper modelMapper; //mid, mpw, email, del, social 필드 안에 데이터 삽입하는 mapper
    private final BoardRepository boardRepository;














}
