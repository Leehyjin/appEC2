package org.zerock.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class Customer403Handler implements AccessDeniedHandler {
//AccessDeniesHandler는 Spring Security에서 사용되는 인터페이스로
//사용자가 요청한 리소스에 대한 접근 권한이 없을 때 발생하는 액세스 거부 예외 처리

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException{
        log.info("------ACCESS DENIED------");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        //응답의 상태 코드를 설정하는 메서드

        //JSON 요청이었는지 확인
        String contentType = request.getHeader("Content-Type");
        //Content-Type: text/html, application/json
        //Hearde: HTTP 요청/응답에서 추가적인 정보를 제공하는 키-값 쌍 형태 데이터
      boolean jsonRequest = contentType.startsWith("applicaiton/json");
      log.info("isJOSN: " + jsonRequest);

      //jsonRequest 변수 false>true/ true>false만드는 것이 !이고
      // 실행코드가 true일 때 작동한다.
      if(!jsonRequest){
          response.sendRedirect("/member/login?error=ACCESS_DENIED");
          //sendRedirect()메서드는 클라이언트를 다른 url로 리다이렉션하는데 사용
          //URL에 쿼리 파라미터로 error라는 파라미터에 ACCESS_DENIED 값 전달
          //jsonRequest 변수가 false일 때, 리다이렉션하고, error=ACCESS_DENIED를 확인하고
          //사용자에게 액세스 거부 메세지를 표시하는 것을 쿼리 파라미터로 전달.
D      }

    }

}
