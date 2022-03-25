package kr.co.team.planner.service.find;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface FindService {

    // nick name 으로 아이디 찾기
    public String[] findByNick(String nick) throws Exception;

    // question 으로 아이디 찾기 - 1.
    public Map<String, Object> findUserIdAndEmailByQuestionAndAnswer(String answer, String context, String birthday) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    // 비밀번호 찾기 ---->  ID 와 Email 이 존재하는지
    Map<String, Object> pwFindResult(String userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    // 아이디와 이메일이 존재한다면 찾는 조건이랑 일치하는지 확인 ---> 비밀번호 변경하고 비밀번호 변경 되면 변경된 비밀번호 암호화해서 table 에 Input
    Map<String , Object> resultOfPwfind(String userInfo, String answer,String context, String birthday) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException;

    // 모든 조건이 충족 되었다면 설정된 변경할 비밀번호로 암호화 하여 변경하고 비밀번호를 해당 email 로 전송
    void PwChangeResult(String userInfo, String answer, String context, String birthday) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;
}
