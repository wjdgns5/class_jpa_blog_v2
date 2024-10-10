package com.tenco.blog_v1.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    
    // DI 처리
    private final UserRepository userRepository;
    private final HttpSession session;

    /**
     * 회원 정보 수정
     * @param reqDTO
     * @return
     */
    @PostMapping("/user/update")
    public String update(@ModelAttribute(name = "updateDTO") UserDTO.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        // 유효성 검사는 생략
        // 사용자 정보 수정
        User updatedUser = userRepository.updateById(sessionUser.getId(), reqDTO.getPassword(), reqDTO.getEmail());
        // 세션 정보 동기화 처리
        session.setAttribute("sessionUser", updatedUser);
        return "redirect:/";
    }

    /**
     * 회원 정보 페이지 요청
     * 주소 설계 : http://localhost:8080/user/update-form
     *
     * @param
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하면
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {

        log.info("회원 수정 페이지");
       // model.addAttribute("name", "회원 수정 페이지");

        User sessionUser = (User)session.getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/login-form";
        }
        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);

        return "user/update-form"; // 템플릿 경로: user/update-form/mustache
    }

    /**
     * 회원 가입 기능 요청
     * @param reqDto
     * @return
     */
    @PostMapping("/join")
    public String join(@ModelAttribute(name = "joinDTO") UserDTO.JoinDTO reqDto) {
        userRepository.save(reqDto.toEntity());

        return "redirect:/login-form";
    }

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
     * 자원에 요청은 GET 방식이지만 보안의 이유로 예외!
     * 로그인 처리 메서드
     * 요청 주소 : POST  http://localhost:8080/login
     * @param reqDTO
     * @return
     */
    @PostMapping("/login")
    public String login(UserDTO.LoginDTO reqDTO) {
        try {
            User sessionUser = userRepository.findByUserNameAndPassword(reqDTO.getUsername(), reqDTO.getPassword());
            session.setAttribute("sessionUser", sessionUser);
            return "redirect:/";
        } catch (Exception e) {
            // 쿼리 스트링으로 error 이 들어오면 따로 처리하는 방식도 있다.
            // 로그인 실패
            return "redirect:/login-form?error";
        }
    }

    /**
     * 로그아웃
     * 주소 : http://localhost:8080/logout
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션을 무효화 한다. (로그아웃)
        return "redirect:/";
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

}
