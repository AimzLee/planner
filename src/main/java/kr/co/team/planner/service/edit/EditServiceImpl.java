package kr.co.team.planner.service.edit;

import kr.co.team.planner.entity.Question;
import kr.co.team.planner.entity.User;
import kr.co.team.planner.repository.AnswerRepository;
import kr.co.team.planner.repository.QuestionRepository;
import kr.co.team.planner.repository.UserRepository;
import kr.co.team.planner.service.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class EditServiceImpl implements EditService{
    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;
//    @Query(value = "select id, email, gender, birthday from User where id = :nick ")
    @Override
    public Map<String, Object> bringUserInfo(String nick) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Object result = userRepository.bringUserData(nick);
        Object[] userInfo = (Object[]) result;


        String userId = (String)userInfo[0];
        String userEmail = (String)userInfo[1];
        userEmail = CryptoUtil.decryptAES256(userEmail,"Email");
        String userGender = (String)userInfo[2];
        LocalDate userBirthday = (LocalDate) userInfo[3];
        Long userCode = (Long)userInfo[4];



        Map<String, Object> users = new HashMap<>();
        users.put("userId", userId);
        users.put("userEmail", userEmail);
        users.put("userGender", userGender);
        users.put("userBirthday", userBirthday);
        users.put("userCode", userCode);
        return users;
    }

    @Override
    public String bringAnswerInfo(Long code) {
        User userCode  = User.builder()
                .code(code)
                .build();
        String userAnswer = answerRepository.getAnswerForEditInfo(userCode);
        return userAnswer;
    }
//
    @Override
    public String bringQuestionInfo(Long code) {
        User userCode  = User.builder()
                .code(code)
                .build();
        Question result = answerRepository.getQnoForEditInfo(userCode);
        Long userQno = result.getQno();
        Question qno = Question.builder()
                .qno(userQno)
                .build();
        String userContext = questionRepository.getContextForUserInfo(qno.getQno());
        return userContext;
    }

    @Override
    public boolean bringPwForRetire(String pw,String nick) {
        boolean userConfirm = false;
        String userPw = userRepository.brinUserPw(nick);
        if(BCrypt.checkpw(pw, userPw)){
            userConfirm = true;
        }else{
            throw new IllegalArgumentException("Wrong Password");
        }

        return userConfirm;
    }
}
