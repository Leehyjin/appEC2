package org.zerock.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.backoff.BackOff;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {
// User 클래스릀 상속받고 OAuth2user 인터페이스를 구현하여 OAuth2인증을 통한 사용자 정보 처리

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

    private Map<String, Object> props; //String타입의 키와 Object 타입의 값을 갖는 Map 객체

    public MemberSecurityDTO(String username, String password, String email, Boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities){
            super(username, password, authorities); //부모 클래스의 생성자 호출(부모 클래스에 전달할 인자)
        //user 클래스 생성자 호출

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }

    public Map<String, Object> getAttributes(){
        return this.getProps();
    }

    @Override
    public String getName(){
        return this.mid;
    }


}
