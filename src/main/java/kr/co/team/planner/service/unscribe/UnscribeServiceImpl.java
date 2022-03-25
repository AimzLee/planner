package kr.co.team.planner.service.unscribe;

import kr.co.team.planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnscribeServiceImpl implements UnscribeService{
    private final UserRepository userRepository;
    @Override
    public boolean userUnscribecomplete(String status, String nick) {
        boolean successOrNot =false;
        int unscribeResult = userRepository.unSubscribe(status, nick);
        if(unscribeResult>0){
            successOrNot = true;
        }
        return successOrNot;
    }


    @Override
    public void userModDateUpdate(String nick) {
        LocalDateTime localDateTime = LocalDateTime.now();
        userRepository.unScribeTime(localDateTime, nick);
    }
}
