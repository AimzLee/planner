package kr.co.team.planner.service.find;





import kr.co.team.planner.entity.Question;
import kr.co.team.planner.entity.User;
import kr.co.team.planner.repository.AnswerRepository;
import kr.co.team.planner.repository.QuestionRepository;
import kr.co.team.planner.repository.UserRepository;
import kr.co.team.planner.service.CryptoUtil;
import kr.co.team.planner.service.email.EmailSenderService;
import kr.co.team.planner.service.email.serialNumberFactory.ForFindPw;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class FindServiceImpl implements FindService{
    private final UserRepository userRepository;

    private final EmailSenderService emailSenderService;
    @Override
    public String[] findByNick(String nick) throws Exception {
        Object getIdAndEmail = userRepository.findByNick(nick);

        // id ,  값과  email 값을 받을 것이기 때문에 배열로 저장
        Object[] result = (Object[]) getIdAndEmail;

        String email = (String) result[1];

        // Email 은 암호화가 되어있음으로 복호화를 실행
        email = CryptoUtil.decryptAES256(email, "Email");

        // 복호화를 확인
        System.out.println("마스킹 되기 전 Email : "+email);

        // @를 중심으로 나누어 배열로 저장
        String[] emailFront = email.split("@");
        int len_f = emailFront[0].length();

        String maskingComplete = null;


        if(len_f>3){
            String noMask = emailFront[0].substring(0,3);
            // 값을 확인
//            System.out.println(noMask);
            String Masking = "*".repeat(len_f-3);

            // 값을 확인
//            System.out.println(Masking);
            maskingComplete = noMask+Masking;
        }

        int len_b = emailFront[1].indexOf(".");
        String masking_b = "*".repeat(len_b);
        String other = emailFront[1].substring(len_b);

        maskingComplete = maskingComplete+"@"+masking_b+other;
//        System.out.println("마스킹 되고 난 후 Email : "+maskingComplete);


        String id = (String) result[0];
        int idlen = id.length();

        String nomasking_id = id.substring(0,3);
        String masking_id = "*".repeat(idlen-3);

        String idmaskingresult = nomasking_id+masking_id;

        String [] completeresult = new String[2];

        completeresult[0] = maskingComplete;
        completeresult[1] = idmaskingresult;

        return completeresult;
    }


    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Map<String, Object> findUserIdAndEmailByQuestionAndAnswer(String answer, String context, String birthday) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        List<Long> getQnoListFromQuestion = questionRepository.findByContext(context);

        Question codeResult = answerRepository.getQnoByUseContext(answer);
        Long resultQnoByAnswer = codeResult.getQno();


        String id = null;
        String email = null;
        for(Long qnoResult:getQnoListFromQuestion) {
            if(qnoResult==resultQnoByAnswer){
                Question question = Question.builder()
                        .qno(resultQnoByAnswer)
                        .build();

                User userInfoByUsingQno = answerRepository.getCodeByUseQno(question);
                Long userCode = userInfoByUsingQno.getCode();

                LocalDate userBirthday = userInfoByUsingQno.getBirthday();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate date = LocalDate.parse(birthday, formatter);
                if(userBirthday.equals(date)) {
                    System.out.println(date);
                    System.out.println(userBirthday);
                    System.out.println("collect");
                }
                if(userBirthday.equals(date)) {
                    Object userIdAndEmail = userRepository.findUserIdAndEmailByUserCode(userCode);
                    Object[] resultUserIdAndEmail = (Object[]) userIdAndEmail;
                    id = (String) resultUserIdAndEmail[0];
                    email = (String) resultUserIdAndEmail[1];
                    email = CryptoUtil.decryptAES256(email, "Email");
                }else if(!userBirthday.equals(date)){
                    throw new IllegalArgumentException("birth day is wrong");
                }
            }
        }
        Map<String, Object > result = new HashMap<>();
        result.put("id",id);
        result.put("email",email);
        return result;
    }

    @Override
    public Map<String, Object> pwFindResult(String userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        boolean userCodeExists = false;
        if(userInfo.contains("@")){
            List<Object[]> emailAndCode = userRepository.useEmailReturnCode();
            for(Object[] i:emailAndCode){
                String userEmail = (String) i[0];
                if (CryptoUtil.decryptAES256(userEmail, "Email").equals(userInfo)){
                    userCodeExists = true;
                }
            }
            if(userCodeExists == false){
                throw new IllegalArgumentException("This is not available Email");
            }
        }else{
            String idandCode = userRepository.userIdRuturnCode(userInfo);
            if(idandCode.equals(userInfo)){
                userCodeExists = true;
            }else{

                throw new IllegalArgumentException("This is not available Id");
            }

        }
        Map<String, Object> userInfoConfirm = new HashMap<>();
        if(userCodeExists==true) {
            userInfoConfirm.put("userInfo", userInfo);
        }
        return userInfoConfirm;
    }

    @Override
    public Map<String, Object> resultOfPwfind(String userInfo, String answer, String context, String birthday) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException {
        Map<String, Object> resultUserInfo = pwFindResult(userInfo);
        Map<String, Object> questionResult = findUserIdAndEmailByQuestionAndAnswer(answer, context, birthday);
        String userResult = (String) resultUserInfo.get("userInfo");
        String questionResultId = (String) questionResult.get("id");
        boolean rightOrNot = false;
        String quesetionResultEmail = (String) questionResult.get("email");
        String newPw = null;
        if(userInfo.contains("@")){
            if(userResult.equals(quesetionResultEmail)){
                rightOrNot = true;
            }else{
                throw new IllegalArgumentException("Email and user information do not match.");
            }
        }else{
            if(userResult.equals(questionResultId)){
                rightOrNot = true;
            }else{
                throw new IllegalArgumentException("ID and user information do not match.");
            }
        }
        Map<String, Object> result = new HashMap<>();
        if(rightOrNot == true){
            ForFindPw makepw = new ForFindPw();
            newPw = makepw.excuteGenerate();
            result.put("newPassword",newPw);
            result.put("UserEmail", quesetionResultEmail);
            result.put("UserId", questionResultId);
        }
        return result;
    }

    @Override
    public void PwChangeResult(String userInfo, String answer, String context, String birthday) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Map<String, Object> result = new HashMap<>();

        result = resultOfPwfind(userInfo,answer,context,birthday);
        System.out.println(result.get("newPassword"));
        System.out.println(result.get("UserEmail"));
        System.out.println(result.get("UserId"));
        String pwResult = (String) result.get("newPassword");
        String userEmail = (String) result.get("UserEmail");
        BCrypt.hashpw(pwResult,BCrypt.gensalt());

        String message = "Your new Password  : "+result.get("newPassword")+"\n" +
                "\n Plaese Insert BlanK For Join Us";
        userRepository.changingPw(BCrypt.hashpw(pwResult,BCrypt.gensalt()), (String) result.get("UserId"));
        emailSenderService.sendMail("Here is New Password From Make Your Plan", userEmail, message);
    }
}
