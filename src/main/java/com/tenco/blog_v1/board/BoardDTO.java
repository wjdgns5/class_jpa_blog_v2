package com.tenco.blog_v1.board;

import com.tenco.blog_v1.user.User;
import lombok.Data;

public class BoardDTO {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .user(user)
                    .build();
        }
    }

    // 정적 메서드
    @Data
    public static class UpdateDTO {
        private String username;
        private String title;
        private String content;
    }

}
