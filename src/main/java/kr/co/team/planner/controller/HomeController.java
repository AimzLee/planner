package kr.co.team.planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/","/main"})
    public String home(){
        return "home";
    }

    @GetMapping("/planner/main")
    public void planner() { }

}
