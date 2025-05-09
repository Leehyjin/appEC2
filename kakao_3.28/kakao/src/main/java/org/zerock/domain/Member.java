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

@ElementCollection(fetch = FetchType.LAZY)
@Builder.Default
//기본값 HashSet<>() 안에 빈 집합(객체 묶음)로 초기화 된다.
//로딩: 데이터를 실제로 읽어와서 객체로 변환
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




