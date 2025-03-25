package org.zerock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//Spring MVC에서 정적 리스소를 처리하는데 사용되는 클래스
//리소스가 저장되어 있는 경로와 해당 리소스를 어떤 url 경로로 접근할 수 있는지 설정할 수 있다.
@Configuration
@EnableWebMvc
//http 요청을 처리할 핸들러(컨트롤러)를 찾는 설정
//뷰를 어떻게 렌더링할지 결정하는 설정
//요청/응답 데이터를 변환하는 설정
//예외 처리 관련 설정을 기본으로 적용해준다.

//Spring MVC 설정을 커스터마이즈하는 다양한 메소드를 제공한다.
// 이 인터페이스를 구현하면 Spring MVC 동작 방식을 변경하거나 기본적인 설정 추가

public class CustomerServiceConfig implements WebMvcConfigurer {

    @Override
    public void addResoureHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("js/**") //js로 시작하는 모든 요청
                .addResourceLocations("classpath:/static/js");
        registry.addResourceHandler("fonts/**")
                .addResourceLocations("classpath:/static/fonts");
        registry.addResourceHandler("css/**")
                .addResourceLocations("classpath:/static/css");
        registry.addResourceHandler("assets/**")
                .addResourceLocations("classpath:/static/assets");




    }





}









