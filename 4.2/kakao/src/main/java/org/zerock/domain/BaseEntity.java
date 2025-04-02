package org.zerock.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
//JPA에서 엔티티 클래스의 속성과 데이터베이스 테이블 컬럼 간 매핑 정의
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@MappedSuperclass //이게 붙은 클래스는 데이터베이스 테이블에 매핑되지 않지만, 자식 클래스는 이 클래스를 상속받아
//테이블에 매핑될 수 있다.
@EntityListeners(value={AuditingEntityListener.class})
//엔티티가 생성되거나 수정될 때 발생하는 이벤트 처리
//
@Getter
abstract class BaseEntity {
    @CreatedDate //엔티티가 처음 생성될 때의 생성 날짜를 자동으로 기록
    @Column(name="regdate", updatable = false)
    private  LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;


}
