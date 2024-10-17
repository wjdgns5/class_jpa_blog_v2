package com.tenco.blog_v1.reply;

import com.tenco.blog_v1.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService;
    private final HttpSession session;

    // 댓글 생성 기능 만들기
    @PostMapping("/reply/save")
    public String save(ReplyDTO.SaveDTO reqDTO) {
        // 로그인 여부 확인
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/login-form";
        }
        replyService.saveReply(reqDTO, sessionUser);
        return "redirect:/board/"+ reqDTO.getBoardId();
    }

    // 댓글 삭제

    // @DeleteMapping("")
    @PostMapping("/baord/{boardId}/reply/{replyId}/delete")
    public String delete(@PathVariable(name = "boardId") Integer boardId, @PathVariable(name = "replyId") Integer replyId) {
        // 삭제도 권한 확인
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/login-from";
        }
        replyService.deleteReply(replyId, sessionUser.getId(), boardId);
        return "redirect:/board/" + boardId;
    }

}
