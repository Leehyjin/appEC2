package org.zerock.domain;


import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity{

@Id
private String mid;

private String mpw;
private String email;
private boolean del;

private boolean social;

@ElementCollection(fetch = FetchType.LAZY) //이 테이블은 엔티티가 아닌 <값 타입 컬렉션>을 매핑할 때 사용
//데이터를 어떻게 가져올 것인가: 해당 컬렉션의 데이터는 실제로 필요할 때까지 지연로딩
//컬렉션에 접근할 때 로딩: roleSet 참조할 때 로딩된다.
@Builder.Default //빌러들 사용할 때 초기값을 설정
private Set<MemberRole> roleSet = new HashSet<>();

public void changePassword(String mpw){
    this.mpw = mpw;
}

public void changeEmail(String email){
    this.email = email;
}

public void changeDel(boolean del){
    this.del = del;
}

public void addRole(MemberRole memberRole){
    this.roleSet.add(memberRole);
}

public void clearRoles(){
    this.roleSet.clear();
}
public void changeSocial(boolean social){
    this.social = social;
}
}




