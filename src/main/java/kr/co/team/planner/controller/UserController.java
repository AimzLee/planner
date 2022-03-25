package kr.co.team.planner.controller;
//from SSW

import kr.co.team.planner.service.edit.EditService;
import kr.co.team.planner.service.unscribe.UnscribeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/checkplan/forUser/")
public class UserController {
    @GetMapping("editUserInfo")
    public void editUserInfo(){
    }
    @Autowired
    private EditService editService;

    @GetMapping("editUser")
    public void editUser(Model model , HttpSession session) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String nick = (String) session.getAttribute("nick");
        Map<String, Object> result = editService.bringUserInfo(nick);
        String userId = (String) result.get("userId");
        String userEmail = (String)result.get("userEmail");
        String userGender = null;
        userGender = (String)result.get("userGender");
        Long userCode = (Long)result.get("userCode");
        String userAnswer = editService.bringAnswerInfo(userCode);
        System.out.println(userAnswer);
        String userContext = editService.bringQuestionInfo(userCode);

        if(userGender.equals("m")==true){
            userGender="Male";
        }else if(userGender.equals("f")==true){
            userGender="Female";
        }
        LocalDate userBirthday = (LocalDate) result.get("userBirthday");
        model.addAttribute("userId",userId);
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userBirthday",userBirthday);
        model.addAttribute("userGender",userGender);
        model.addAttribute("userCode",userCode);
        model.addAttribute("userAnswer",userAnswer);
        model.addAttribute("userContext",userContext);

    }
    @GetMapping("retire/retire")
    public void retire(){
    }
    @PostMapping("retire/UnscribingChecking")
    public String unscribingChecking(String pw, HttpSession session){
        String url = null;
        String userNick = (String) session.getAttribute("nick");
        boolean userPwCollectOrNot = editService.bringPwForRetire(pw, userNick);
        if(userPwCollectOrNot==true){
            url = "checkplan/forUser/retire/UnscribingDoing";
        }
        return url;
    }
    @Autowired
    private UnscribeService unscribeService;


    @GetMapping("retire/UnscribingComplete")
    public void unscribingComplete(HttpSession sessioin){
        String userNick = (String) sessioin.getAttribute("nick");
        String userStatus = "7day";
        boolean userUnscribeComplete = unscribeService.userUnscribecomplete(userStatus, userNick);
        if(userUnscribeComplete==true){
            unscribeService.userModDateUpdate(userNick);
            sessioin.invalidate();
        }
    }

}
