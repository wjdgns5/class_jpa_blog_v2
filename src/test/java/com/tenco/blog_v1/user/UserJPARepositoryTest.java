package com.tenco.blog_v1.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

/**
 * 패키지명이 동일 해야 한다.
 * UserJPARepository 기능을 테스트 하는 클래스 입니다.
 *
 * DataJpaTest : 어노테이션은 JPA 관련 컴포넌트를 로드하여 테스트 환경을 만들어 준다.
 */

@DataJpaTest
public class UserJPARepositoryTest {

    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    @DisplayName("사용자 이름으로 조회하는 테스트")
    public void findByUsername_test() {

        // given - 테스트에 필요한 초기 조건 설정
        String username = "카리나";

        // when - 테스트 대상 메서드 실행
        Optional<User> userOpt = userJPARepository.findByUsername(username);

        // eye ~
        System.out.println(username);
        System.out.println(userOpt.toString());

        // then

    }

}
