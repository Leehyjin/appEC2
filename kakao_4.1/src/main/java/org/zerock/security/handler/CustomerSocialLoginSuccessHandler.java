package org.zerock.security.handler;


import org.springframework.security.core.Authentication;
//로그인, 로그아웃, 토큰 관리, 권한 부여, 다단계 인즈
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomerSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
//AuthenticationSuccessHandler: 로그인 성공 후 처리할 동작을 정의

    private final PasswordEncoder passwordEncoder;
    //Spring Security에서 제공하는 인터페이스로, 비밀번호를 암호화하거나 검증하는데 사용
    //비밀번호 암호화: 사용자 비밀번호를 원래 형태에서 안전하게 변형하는 과정
    //검증: 입력한 비밀번호와 저장된 암호화된 비밀번호가 일치하는지 확인

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{}


}
