package com.tenco.blog_v1.board;

import com.tenco.blog_v1.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY) // EAGER 즉시 전략
    @JoinColumn(name = "user_id")
    private User user; // 게시글 작성자 정보

    // created_at 컬럼과 매핑하여, 이 필드는 데이터 저장시 자동으로 설정 됨
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

}
