package kr.co.team.planner.controller;

import kr.co.team.planner.dto.AuthMember;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/security")
public class SecurityController {
    @GetMapping("/member/main")
    public void member(@AuthenticationPrincipal AuthMember authMember) {
        log.info("로그인 한 유저만 접근 가능");
        log.info(authMember);
    }

    @GetMapping("/admin/management")
    public void admin() {
        log.info("관리자만 접근 가능");
    }

}
