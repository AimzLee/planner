package kr.co.team.planner.controller;



import kr.co.team.planner.service.email.EmailSenderService;
import kr.co.team.planner.service.find.FindService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Controller
@Log4j2
public class FindController {
    @Autowired
    private FindService findService;

    @GetMapping("/find/emailPage")
    public void emailFindPage(){
    }

    @GetMapping("/find/byNickName")
    public void byNick(){
    }
    @PostMapping("/find/result/byNick")
    public String findingByNick(String nick, Model model) throws Exception {
        String[] result = findService.findByNick(nick);

        model.addAttribute("email",result[0]);
        model.addAttribute("id",result[1]);
        return "/find/result/byNick";
    }


    @GetMapping("/find/byQuestion")
    public void byQuestion(){
    }
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/find/result/byQuestion")
    public String temp(String answer, String context, String birthday) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException {
        Map<String, Object> result = findService.findUserIdAndEmailByQuestionAndAnswer(answer,context, birthday);
        String message = "Your Id is : "+result.get("id")+"\n" +
                "Your Email is : " +result.get("email")+
                "\n Plaese Insert BlanK For Join Us";
        String userEmail = (String) result.get("email");
        System.out.println(userEmail);
        emailSenderService.sendMail("Your Id From Making a Plan", userEmail,message);
        return "/find/result/byQuestion";
    }

    @GetMapping("/find/pwByInfo")
    public void findPwByInfo(){
    }
    @PostMapping("/find/result/byEmailOrId")
    public String findPwByInfoQuestion(String userInfo, String answer, String context, String birthday) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        findService.PwChangeResult(userInfo, answer ,context, birthday);
        return "/find/result/byEmailOrId";
    }
}
