package org.zerock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.domain.Member;

@Mapper
public interface MemberMapper {
    void insert(Member member);  // 회원 등록

}
