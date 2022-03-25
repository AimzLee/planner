package kr.co.team.planner.controller;

import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PlanDTO;
import kr.co.team.planner.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/plan")
@Log4j2
@RequiredArgsConstructor
public class PlanController {

    @GetMapping("/register")
    public void register() {
    }

    private final PlanService planService; //final

    @PostMapping("/register")
    public String register(PlanDTO planDTO, RedirectAttributes redirectAttributes) {
        log.info("planDTO: " + planDTO);
        Long pno = planService.register(planDTO);
        redirectAttributes.addFlashAttribute("msg", pno);
        return "redirect:/plan/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", planService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long pno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("working?!@#$#@??pno: " + pno);
        PlanDTO planDTO = planService.getPlan(pno);
        System.out.println("여기까진 문제가 없는지 확인!");
        model.addAttribute("dto", planDTO);
    }

}
