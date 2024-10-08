package com.tenco.blog_v1.user;

import lombok.Data;

@Data
public class UserDTO {

    // 정적 내부 클래스로 모으자
    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

    // 정적 내부 클래스로 모으자
    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
    }
}
