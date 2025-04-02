package org.zerock.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.repository.MemberRepository;
import org.zerock.domain.Member;
import org.zerock.security.dto.MemberSecurityDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private  final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {log.info("loadUserByUsername: " + username);

        Optional<Member> optionalMember = memberRepository.getWithRoles(username);
        if(optionalMember.isEmpty()){
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
            // Spring Security에서 UserDetailsService 구현체에서 자주 사용되는 예외입니다.
            //new를 붙여 는 UsernameNotFoundException의 인스턴스를 생성
        }
        Member member = optionalMember.get();
        // get()메서드는 Optional 객체에서 값을 가져오는 메서드
        // 값이 없을 때는 NoSuchElementException 예외 던진다.

        return new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getEmail(),
                member.isDel(),
                false,
                member.getRoleSet().stream().map(
                        memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
                //SimpleGrantedAuthority는 사용자 권한 표현하는 객체 생성

        );
    }

}
