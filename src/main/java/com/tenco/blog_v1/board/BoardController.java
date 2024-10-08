package com.tenco.blog_v1.board;

import com.tenco.blog_v1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    // DI
    // @Autowired
    // 네이티브 쿼리 연습
    private final BoardNativeRepository boardNativeRepository;
    // JPA API, JPQL
    private final BoardRepository boardRepository;
    // 세션을 받기위한 준비
    private final HttpSession session;

    /** JPA API를 통해서 만들어보자?
     * 주소 테스트 http://localhost:8080/board/1/delete1
     * @param id
     * @return
     */
    @PostMapping("/board/{id}/delete1")
    public String deleteTest(@PathVariable(name = "id") Integer id) {
        User sessionUser = (User)session.getAttribute("sessionUser");

        // 회원정보 없으면 로그인 페이지 이동
        if(sessionUser == null) {
            return "redirect:/login-form";
        }

        // 권한 체크
        // 페이지 존재 여부 확인
        Board board = boardRepository.findById(id);
        if(board == null) {
            return "redirect:/error-404";
        }

        if( ! board.getUser().getId().equals(sessionUser.getId())) {
            return "redirect:/error-403";
        }

        boardRepository.deleteById1(id);
        // boardNativeRepository.deleteById(id);
        return "redirect:/";

    }

    // 주소설계 - http://localhost:8080/board/10/delete (form 활용하기 때문에 delete 선언)
    // form 태그에서는 GET, POST 방식만 지원하기 때문이다.
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id) {

        // 유효성, 인증검사
        // 세션에서 로그인 사용자 정보 가져오기 --> 인증(로그인 여부), 인가(권한 -> 내가 작성한 글 여부)
        User sessionUser = (User)session.getAttribute("sessionUser");

        if(sessionUser == null) {
            return "redirect:/login-form";
        }

        // 권한 체크
        Board board = boardRepository.findById(id);
        if(board == null) {
            return "redirect:/error-404";
        }

        if( ! board.getUser().getId().equals(sessionUser.getId())) {
            return "redirect:/error-403";
        }

        boardRepository.deleteById(id);
        // boardNativeRepository.deleteById(id);
        return "redirect:/";
    }

    // 특정 게시글 요청 확인
    // 주소설계 - http://localhost:8080/board/1
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id, HttpServletRequest request) {

        // JPA API 사용
        //  Board board = boardNativeRepository.findById(id);
        //  request.setAttribute("board", board);

        // JPQL FETCH join 사용
           Board board = boardRepository.findByIdJoinUser(id);
           request.setAttribute("board", board);

        return "board/detail";
    }

    /**
     * http://localhost:8080/
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
//List<Board> boardList = boardNativeRepository.findAll();
List<Board> boardList = boardRepository.findAll();

        model.addAttribute("boardList", boardList);
        log.warn("여기까지 오나?");
//        log.info("boardList : " + boardList);
        // templates 폴더 안에 있는 index.mustache 실행
        return "index";
    }

    // 주소설계 - http://localhost:8080/board/save-form
    // 게시글 작성 화면
    @GetMapping("/board/save-form")
    public String saveForm() {

        return "board/save-form";
    }
    
    // 게시글 저장
    // 주소설계 - http://localhost:8080/board/save
    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO.SaveDTO reqDto) {
        User sessionUser = (User)session.getAttribute("sessionUser");

        if(sessionUser == null) {
            return "redirect:/login-form";
        }

        // 파라미터가 올바르게 전달 되었는지 확인하기 위해서 사용
        log.warn("save 실행: 제목={}, 내용={}", reqDto.getTitle(), reqDto.getContent());

       // boardNativeRepository.save(title, content);

        // SaveDTO에서 toEntity 메서드를 사용해서 Board 엔티티로 변환하고 인수값으로 User 정보를 넣었다.
        // 결국 Board 엔티티로 반환이 된다.
        boardRepository.save(reqDto.toEntity(sessionUser));
        return "redirect:/";
    }

    // 게시글 수정 화면 요청
    @GetMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id")Integer id, HttpServletRequest request) {
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);
        // src/main/resource/templates/board/update-form.mustache
        return "board/update-form";
    }

    // 게시글 수정 요청 기능
    // board/{id}/update
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable(name = "id")Integer id, @RequestParam(name = "title") String title, @RequestParam(name = "content") String content) {
        // 업데이트 기능이 완료되면 디테일로 자기자신
        boardNativeRepository.updateById(id, title, content);
        return "redirect:/board/detail" + id;
    }
}
