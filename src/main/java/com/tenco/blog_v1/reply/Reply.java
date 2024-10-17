package com.tenco.blog_v1.reply;

import com.tenco.blog_v1.board.Board;
import com.tenco.blog_v1.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reply_tb")
@ToString(exclude = {"user", "board"}) // 연관된 엔티티를 제외하여 순환 참조 방지 및 보안 강화 때문에 사용한다.
public class Reply {

    // 일반적으로 id는 Long 타입을 사용하는 것을 권장한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // null 값이 들어올 수 없어! - 기본 값을 null 허용
    @Column(nullable = false)
    private String comment;

    // 단방향 관계 설계 -> User 엔티티에는 Reply 정보가 없다!!!
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "user_id")
    private User user;

    // 양방향 매핑 (FK 주인은 댓글(Reply 이다)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "board_id")
    private Board board;

    // JPA 엔티티에서 데이터베이스에 저장할 필요가 없는 필드를 정의할 때 사용한다.
    @Transient
    private boolean isReplyOwner;

    @Builder.Default
    private String status = "ACTIVE"; // 댓글 상태 (ACTIVE, DELETED)

    @CreationTimestamp // 엔티티가 생성될 때 자동으로 현재 시간으로 설정
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 엔티티가 데이터베이스에 영속화 되기 전에 호출 되는 메서드가 있다면 사용한다.
     * @PrePersist 어노테이션은 JPA 라이프 사이클 이벤트 중 하나로 엔티가 영속화 되기전에 실행 된다.
     */
    @PrePersist
    protected void onCreate() {
        if(this.status == null) {
            this.status = "ACTIVE";
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}

