package com.tenco.blog_v1.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tb")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = true) // 유니크 제약조건 설정
    private String username;
    private String password;
    private String email;

    // 일반 사용자라면 "USER"
    // 관리자 사용자라면 "ADMIN"
    @Column(nullable = true)
    private String role; // 등급? 추가

    @CreationTimestamp // 엔티티(Entity) 생성시 자동으로 현재 시간 입력 어노테이션
    private Timestamp createdAt;

}
