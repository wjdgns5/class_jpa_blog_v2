package com.tenco.blog_v1.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    /**
     * 회원가입 페이지 요청
     * 주소 설계 : http://localhost:8080/join-form
     *
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하면
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @GetMapping("/join-form")
    public String joinForm(Model model) {

        log.info("회원가입 페이지");
        model.addAttribute("name", "회원가입 페이지");
        return "user/join-form"; // 템플릿 경로: user/join-form/mustache
    }

    /**
     * 로그인 페이지 요청
     * 주소 설계 : http://localhost:8080/login-form
     *
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하면
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @GetMapping("/login-form")
    public String loginForm(Model model) {

        log.info("로그인 페이지");
        model.addAttribute("name", "로그인 페이지");
        return "user/login-form"; // 템플릿 경로: user/login-form/mustache
    }

    /**
     * 회원 정보 페이지 요청
     * 주소 설계 : http://localhost:8080/user/update-form
     *
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하면
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @PostMapping("/user/update-form")
    public String updateForm(Model model) {

        log.info("회원 수정 페이지");
        model.addAttribute("name", "회원 수정 페이지");
        return "user/update-form"; // 템플릿 경로: user/update-form/mustache
    }

}
