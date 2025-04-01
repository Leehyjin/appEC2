package org.zerock.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;



import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //메서드 수준에서 보안을 설정
//메서드 실행 전 권한 검사 수행(PostAuthorize: 메서드 실행 후에 권한 검사)
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService




}
