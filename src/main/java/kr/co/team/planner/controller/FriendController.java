package kr.co.team.planner.controller;

import com.google.gson.Gson;
import kr.co.team.planner.dto.FriendDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.UserDTO;
import kr.co.team.planner.service.FriendService;
import kr.co.team.planner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final UserService userService;

    @GetMapping({"/friend","/friend/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", friendService.getList(pageRequestDTO));
        return "/friend/list";
    }


    //자동완성 검색
    @RequestMapping(value = "/allid", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String json(Locale locale, Model model) {
        String[] array = userService.getAllId();
        Gson gson = new Gson();
        return gson.toJson(array);
    }


    @GetMapping("/friend/search")
    public String search(){
        return "/friend/search";
    }

    //친구요청
    @PostMapping("/friend/request")
    public String sendRequest(FriendDTO dto, RedirectAttributes redirectAttributes) throws Exception {
        Long fno = friendService.sendRequest(dto);
        UserDTO userDTO = userService.getUser(dto.getResponseCode());
        redirectAttributes.addFlashAttribute("response_user", userDTO.getNick());
        return "redirect:/friend/list";
    }
}
