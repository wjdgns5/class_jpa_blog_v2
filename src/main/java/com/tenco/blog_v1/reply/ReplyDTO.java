package com.tenco.blog_v1.reply;

import com.tenco.blog_v1.board.Board;
import com.tenco.blog_v1.user.User;
import lombok.*;


public class ReplyDTO {

    @Getter
    @Setter
    public static class SaveDTO {
        private Integer boardId;
        private String comment;

        // DTO --> JPA 영속성 컨텍스트로 저장 한다.. 엔티티로 변환 해야 한다.
        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .comment(comment)
                    .board(board)
                    .user(sessionUser)
                    .build();
        }

    }
}

