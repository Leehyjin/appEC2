package org.zerock.security.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {
//MemberSecurity가 User 상속받고 OAuth2User를 구현한다.

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
    private Map<String, Object> props;

    public MemberSecurityDTO(String username,
                             String password,
                             String email,
                             boolean del,
                             boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        //super는 상위 클래스(USER)의 생성자나 메서드를 호출하는 키워드
        //상위 클래스의 생성자에 넘겨지는 인수

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName(){
        return  this.mid;
    }


}
