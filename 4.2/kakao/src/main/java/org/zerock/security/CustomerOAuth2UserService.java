package org.zerock.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.domain.Member;

import org.zerock.domain.MemberRole;
import org.zerock.repository.MemberRepository;
import org.zerock.security.dto.MemberSecurityDTO;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("userRequest....");
        log.info(userRequest);

        log.info("oauth2 user.......................................................");

        ClientRegistration clientRegistration =userRequest.getClientRegistration();
        //userRequest에서 user는 네이버, 카카오 같은 제공자 요청사항

        String clientName = clientRegistration.getClientName();
        //ex.google

        log.info("clientname: " + clientName);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap= oAuth2User.getAttributes(); //DefaultOAuth2UserService userRequest 정보를 상속받은 OAuth2User로 받아옴
        //그 정보 중에 속성만 추출?
        paramMap.forEach((k,v)->{
            log.info("------------------");
            log.info(k + ":" + v);
        });

       String nickname = null; //nickname엔 값을 할당하지 않는다.
        switch (clientName){
            case "kakao": nickname = getKakaoNickname(paramMap)
                    //메서드의 반환 값이다.
                break;
            case "naver": break;
        }

        log.info("=======================");
        log.info(nickname);
        log.info("=======================");

        return generateDTO(nickname, paramMap);
    }

    private MemberSecurityDTO generateDTO(String nickname, Map<String,Object> params){
        Optional<Member> result = memberRepository.getWithRolesIsSocial(nickname);
        //nickname이 데이터베이스에 있고, 소셜인증 된 멤버 조회
        //멤버 조회할 때 출력 형식은 MEMBER 클래스의 ID, 패스워드, 이메일, 삭제여부, 소셜인증, 권한 종류로 출력된다.

        //기존 회원정보의 테이블에 카카오 아이디와 동일 ID가 없을 경우
        if(result.isEmpty()){
            Member member = Member.builder()
                    .mid(nickname)
                    .mpw("-")
                    .email(" ")
                    .social(true)
                    .build();
            member.addRole(MemberRole.USER);

            memberRepository.save(member);

            //로그인 이후 사용할 MemberSecurityDto 객체 생성하여 정보 전달
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                    nickname, "-", "", false, true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                    //나중에 ROLE_ADMIN 권한을 추가할 경우 이미 List 형태로 관리되고 있기 때문에
                    //별도의 코드 변경 없이 memberSecurityDTO.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    //이런 식으로 추가할 수 있음
            );
            memberSecurityDTO.setProps(params);

            return memberSecurityDTO;
        }else{ //기존의 가입 혹은 카카오 로그인이 되어서 데이터가 있는 경우
           Member member = result.get();

           //로그인 이후 사용할 MemberSecurityDTO 객체 생성하여 전달
             MemberSecurityDTO memberSecurityDTO =new MemberSecurityDTO(
                     member.getMid(),
                     member.getMpw(),
                     member.getEmail(),
                     member.isDel(),
                     member.isSocial(),
                     member.getRoleSet().stream().map(memberRole ->
                             new SimpleGrantedAuthority("ROLE_" + memberRole.name())
                     ).collect(Collectors.toList())
             );
                return memberSecurityDTO;
        }
    }


    private String getKakaoNickname(Map<String, Object> paramMap){
        log.info("kakak getKakaoNickname------------");

        Object value = paramMap.get("kakao_account");

        log.info(value);

        LinkedHashMap accountMap=(LinkedHashMap) value;
        LinkedHashMap profileMap = (LinkedHashMap) accountMap.get("profile");
        String nickname = (String)profileMap.get("nickname");

        log.info("kakao nickname.." + nickname);

        return nickname;

    }

}