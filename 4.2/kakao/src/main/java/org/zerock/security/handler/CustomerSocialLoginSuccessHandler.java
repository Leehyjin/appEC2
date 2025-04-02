package org.zerock.security.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.security.dto.MemberSecurityDTO;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomerSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    //Authentication 객체를 매개변수로 사용하는가?
    //인증된 사용자에 대한 정보를 담고 있다. 로그인 성공 후에 사용자의 정보를 확인하거나 처리하는데 필요
    {
        log.info("------------------------------------------------------");
        log.info("CustomLoginSuccessHandler  onAuthenticationSuccess....");
        log.info(authentication.getPrincipal());
        //authentication 이 객체는 현재 로그인된 사용자의 인증정보를 담고 있다.
        //getPrincipal() 메서드는 로그인한 사용자 상제 정보를 담고 있는 객체를 반환한다. 보통 UserDetails 객체를 반환한다.

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
        String plainPw = memberSecurityDTO.getMpw();

        if (memberSecurityDTO.isSocial() &&
                ("-".equals(memberSecurityDTO.getMpw()) || passwordEncoder.matches("-", memberSecurityDTO.getMpw()))
        ) {
            log.info("Should Change Password");
            log.info("Redirect toMember Modify");
            response.sendRedirect("/member/modify");
        } else {
            response.sendRedirect("/board/list");
        }


    }

}






