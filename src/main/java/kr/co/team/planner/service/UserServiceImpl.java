package kr.co.team.planner.service;


import kr.co.team.planner.dto.UserDTO;
import kr.co.team.planner.entity.User;
import kr.co.team.planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User join(UserDTO dto) throws Exception {
        User entity = dtoToEntity(dto);
        userRepository.save(entity);
        dto.setCode(entity.getCode());
        return entity;
    }

    @SneakyThrows
    @Override
    public String dec(Long code) {
        User user = userRepository.findByCode(code);
        String decryptComplete = CryptoUtil.decryptAES256(user.getId(), "Id");
        return decryptComplete;
    }

    @Override
    public Boolean checkid(String id) {
        return null;
    }

//    @Override
//    public Boolean checkid(String id) {
//        boolean exists = false;
//        User user = userRepository.findById(id);
//        if(user!=null){
//            exists = true;
//        }
//        System.out.println(exists);
//    return exists;
//    }

    @Override
    public Boolean checkEmail(String email) {
        return null;
    }

    @Override
    public User login(String email, String pw) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
//        List<Object []> list = userRepository.getalldata();
//        boolean exists = false ;
//        boolean pwcollect = false;
//        String nick = null;
//        for(Object [] i:list){
//            String reemail = (String) i[0];
//            String repw = (String) i[1];
//            nick = (String)i[2];
//            if(CryptoUtil.decryptAES256(reemail,"Email").equals(email)){
//                System.out.println(CryptoUtil.decryptAES256(reemail,"Email"));
//                exists =true;
//                if(BCrypt.checkpw(pw, repw)) {
//                    pwcollect = true;
//                }
//            }
//        }
//
        return null;
    }

    @Override
    public String[] getAllId() {
        String[] arr = userRepository.getAllId();
        return arr;
    }

    @Override
    public UserDTO getUser(Long code) throws Exception {
        User user = userRepository.findByCode(code);
        return entityToDto(user);
    }

}
