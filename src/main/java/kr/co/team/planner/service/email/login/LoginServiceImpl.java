package kr.co.team.planner.service.email.login;


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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> forlogin(String email, String pw) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        boolean exists = false;
        boolean pwcollect = false;
        if(email==null){
            if(pw==null){
                throw new IllegalArgumentException("Please Insert your Information for Login");
            }
            throw new IllegalArgumentException("Please Insert Id or Email");
        }
        else if(pw==null){
            throw new IllegalArgumentException("Please Insert pw ");
        }

        String nick = null;

        String status = null;
        if(email.contains("@")) {
            List<Object[]> list = userRepository.getalldata();
            for (Object[] i : list) {
                String reemail = (String) i[0];
                String repw = (String) i[1];
                status = (String)i[3];

                if(status.equals("회원")||status.equals("7day")){
                if (CryptoUtil.decryptAES256(reemail, "Email").equals(email)) {
                    System.out.println(CryptoUtil.decryptAES256(reemail, "Email"));
                    exists = true;
                    if (BCrypt.checkpw(pw, repw)) {
                        pwcollect = true;
                        nick = (String)i[2];
                    }
                }
            }
            }

        }else {
            Object user = userRepository.findById(email);
            Object[] result = (Object[]) user;
            String fid = (String) result[0];
            String fpw = (String) result[1];
            status = (String) result[3];
            if (status.equals("회원")||status.equals("7day")) {
                if (fid != null) {
                    exists = true;
                    if (BCrypt.checkpw(pw, fpw)) {
                        pwcollect = true;
                        nick = (String) result[2];
                    }
                }
            }
        }
        System.out.println(status);
        Map<String, Object> map = new HashMap<>();
        if(status.equals("회원")||status.equals("7day")){
            map.put("exists",exists);
            map.put("pwcollect",pwcollect);
            map.put("nick",nick);
        }else{
            throw new IllegalArgumentException("It is not valid UserAccount");
        }
        return map;
    }
}

