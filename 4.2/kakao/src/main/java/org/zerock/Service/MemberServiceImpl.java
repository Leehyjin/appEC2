package org.zerock.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.dto.MemberJoinDTO;
import org.zerock.repository.MemberRepository;


@RequiredArgsConstructor
@Service
@Log4j2
public class MemberServiceImpl implements org.zerock.b01.service.MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException{
        String mid = memberJoinDTO.getMid();
        memberRepository.existsById(mid);

    }


}
