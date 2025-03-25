package org.zerock.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.security.CustomerDetailsSerivce;
import org.zerock.security.handler.CustomerSocialLoginSuccessHandler;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
//클래스나 메소드 단위에서 보안 관련 설정
//
public class CustomerSecurityConfig {

    private final DataSource dataSource;
    private final CustomerDetailsSerivce userDetialsSerivce;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(); //암호된 비밀번호와 사용자 입력 비밀번호 비교하는 객체 생성
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomerSocialLoginSuccessHandler(passwordEncoder());
    }

}
