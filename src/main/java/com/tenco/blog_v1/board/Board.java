package com.tenco.blog_v1.board;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "board_tb")
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 전략, DB 위임
    // 데이터베이스가 기본 키 값을 직접 관리하도록 위임
    private Integer id;
    private String title;
    private String content;

    // created_at 컬럼과 매핑하여, 이 필드는 데이터 저장시 자동으로 설정 됨
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
}
