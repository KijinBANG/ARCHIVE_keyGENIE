package kijin.bang.keygenie.controller;

import kijin.bang.keygenie.dto.BoardDTO;
import kijin.bang.keygenie.dto.MemberDTO;
import kijin.bang.keygenie.dto.PageRequestDTO;
import kijin.bang.keygenie.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
//공통 URL 설정
@RequestMapping("/spring/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO: " + pageRequestDTO);
        log.info("boardService.getList(pageRequestDTO): " + boardService.getList(pageRequestDTO));
        model.addAttribute(
                "result",
                boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
    }

    @PostMapping("/register")
    public String register(@AuthenticationPrincipal User user, BoardDTO boardDTO, RedirectAttributes rattr){
        log.info("boardDTO: " + boardDTO);
        log.info("user: " + user);
        boardDTO.setWriterEmail(user.getUsername());
        Long bno = boardService.register(boardDTO);
        rattr.addFlashAttribute("msg", bno + " 등록");
        return "redirect:/spring/board/list";
    }

    @GetMapping({"/read", "/modify"})
    //ModelAttribute 를 작성한 파라미터는 아무런 작업을 하지 않아도 뷰로 전달 됩니다.
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){
        BoardDTO dto = boardService.get(bno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("remove")
    public String remove(long bno, RedirectAttributes rattr){
        boardService.removeWithReplies(bno);
        //출력할 메시지 저장
        rattr.addFlashAttribute("msg", bno + " 삭제");
        return "redirect:/spring/board/list";
    }

    @PostMapping("modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rattr){
        boardService.modify(dto);

        rattr.addAttribute("page", requestDTO.getPage());
        rattr.addAttribute("type", requestDTO.getType());
        rattr.addAttribute("keyword", requestDTO.getKeyword());
        rattr.addAttribute("bno", dto.getBno());

        return "redirect:/spring/board/read";
    }
}
