package kr.co.team.planner.controller;

import kr.co.team.planner.dto.GuestBookDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//로그 출력을 위한 어노테이션
@Log4j2
//PageController를 만들기 위한 어노테이션
@Controller
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestBookController {
    //Service 주입
    private final GuestBookService guestBookService;

//    @GetMapping("/list")
//    public String main(){
//        log.info("시작 요청");
//        //templates 에 있는 guestbook 디렉토리의 list.html을 출력
//        return "redirect:/guestbook/list";
//    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("목록 보기 .....");
        PageResponseDTO result = guestBookService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping("/register")
    public void register(){
        log.info("삽입 요청 페이지로 이동");
    }

    @PostMapping("/register")
    public String register(GuestBookDTO dto, RedirectAttributes redirectAttributes){
        log.info("삽입 처리");
        //삽입 처리
        Long gno = guestBookService.register(dto);
        //리다이렉트 할 때 한 번만 사용하는 데이터 생성
        redirectAttributes.addFlashAttribute(
                "msg", gno + " 삽입");
        //작업 후 목록보기로 리다이렉트
        return "redirect:/guestbook/list";
    }

//    @GetMapping("/guestbook/read")
//    //파라미터 중에서 gno 는 gno 에 대입되고
//    //나머지는 requestDTO 에 대입됩니다. 다음 결과 페이지에 전송됩니다.
//    public void read(long gno,
//                     @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
//                     Model model){
//        GuestBookDTO dto = guestBookService.read(gno);
//        model.addAttribute("dto", dto);
//    }

    @GetMapping({"/read", "/modify"})
    //파라미터 중에서 gno 는 gno 에 대입되고
    //나머지는 requestDTO 에 대입됩니다. 다음 결과 페이지에 전송!
    public void readOrModify(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        GuestBookDTO dto = guestBookService.read(gno);
        model.addAttribute("dto", dto);
    }
//    @GetMapping("/guestbook/modify")
//    //위 코드와 거의 흡사한데 똑 같은은 코드를 반복하는 것은 자원의 낭비!
//    public void modify(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
//        GuestBookDTO dto = service.read(gno);
//        model.addAttribute("dto", dto);
//    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify.........................................");
        log.info("dto: " + dto);
        //수정 메소드 수행
        guestBookService.modify(dto);
        //전달할 데이터 생성
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String delete(Long gno, RedirectAttributes redirectAttributes){
        //삭제 메소드 수행
        guestBookService.delete(gno);
        //전달할 데이터 생성
        redirectAttributes.addFlashAttribute("msg", gno + " 삭제");
        //목록보기로 리다이렉트
        return "redirect:/guestbook/list";
    }
}