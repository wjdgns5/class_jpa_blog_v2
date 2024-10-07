package com.tenco.blog_v1.board;

import jakarta.servlet.http.HttpServletRequest;
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
    private final BoardNativeRepository boardNativeRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Board> boardList = boardNativeRepository.findAll();
        model.addAttribute("boardList", boardList);
        log.warn("여기까지 오나?");
        log.info("boardList : " + boardList);
        System.out.println("boardList.toString() : " + boardList.toString());
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
    public String save(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content) {

        // 파라미터가 올바르게 전달 되었는지 확인하기 위해서 사용
        log.warn("save 실행: 제목={}, 내용={}", title, content);

        boardNativeRepository.save(title, content);
        return "redirect:/";
    }

    // 특정 게시글 요청 확인
    // 주소설계 - http://localhost:8080/board/10
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);
        return "board/detail";
    }

    // 주소설계 - http://localhost:8080/board/10/delete (form 활용하기 때문에 delete 선언)
    // form 태그에서는 GET, POST 방식만 지원하기 때문이다.
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id) {
        boardNativeRepository.deleteById(id);
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
