package kr.co.team.planner.controller;

import kr.co.team.planner.entity.Member;
import kr.co.team.planner.entity.MemberRole;
import kr.co.team.planner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("data", "This article is not just a sentence but a String type(Object Type?? What is the reason that this sentence does not react to both command '<br />' and  '\\n'? Is that related with data type?) data contained by variable named 'data'. The fact that you can read this article on this page means the controller you made works well right now.");
        return "/member/home";
    }

    @GetMapping("/list")
    public void main(Model model) {
        //format 에 맟추어 랜덤하게 사용자를 정보를 생성한 후 List 에 담아보자!
        List<Member> list = new ArrayList<>();
        for (int i = 69; i <= 92; i++) {
            String email = "member" + i + "@study.hard";
            Member member = memberRepository.findById(email).get();
            list.add(member);
        }
        model.addAttribute("list", list);
    }

    @GetMapping("/ex")
    public void ex(Model model) {
        List<Member> list = new ArrayList<>();
        for (int i = 76; i <= 91; i++) {
            Member member = Member.builder()
                    .email("member"+i+"@study.hard")
                    .name("name"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("name"+i))
                    .build();
            member.addMemberRole(MemberRole.USER);
            if(i > 80) member.addMemberRole((MemberRole.MANAGER));
            if(i > 90) member.addMemberRole((MemberRole.ADMIN));
            list.add(member);
        }
        model.addAttribute("list", list);
    }

    @GetMapping("/inlineToEx1")//transfer data from here to below(inline -> ex)
    public String inlineToEx1(Model model, HttpSession session, RedirectAttributes rattr) {

//        String email = "member100@study.hard";
//        Member member = memberRepository.findById(email).get();

        Member member = Member.builder()
                .email("member101@study.hard")
                .name("name101")
                .fromSocial(false)
                .password(passwordEncoder.encode("1111"))
                .build();

        //model is used when it be forwarded -> doesn't work!
        model.addAttribute("vo1", member);
        //if you store data in session, it can be lased until the session is demolished -> doesn't work!
        session.setAttribute("vo2", "What is the data TYPE that can be stored in Session");
        //model is used when it be forwarded -> doesn't work!
        model.addAttribute("vo1", member);
        //if you store data in session, it can be lased until the session is demolished -> doesn't work!
        session.setAttribute("vo2", member);
        //this is maintained just once, and then it will be disappeared
        //use this method whenever you use 'redirect'
        rattr.addFlashAttribute("vo3", member);

        return "redirect:/member/ex1";
    }
    //why change this way?
    @GetMapping({"/inline","/link", "/format"})
    public void inline(Model model) {

        Member member = Member.builder()
                .email("member101@study.hard")
                .name("name101")
                .fromSocial(false)
                .password(passwordEncoder.encode("1111"))
                .build();
        //model is used when it be forwarded
        model.addAttribute("vo", member);
    }

    @GetMapping("/ex1")
    public void ex(Model model, HttpSession session, RedirectAttributes rattr) {

        String email = "member90@study.hard";
        Member member = memberRepository.findById(email).get();

        model.addAttribute("vo1", member);
        //if you store data in session, it can be lased until the session is demolished -> doesn't work!
        session.setAttribute("vo2", member);
        //this is maintained just once, and then it will be disappeared
        //use this method whenever you use 'redirect'
        rattr.addFlashAttribute("vo3", member);
    }
}
