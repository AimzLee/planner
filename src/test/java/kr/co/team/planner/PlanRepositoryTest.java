package kr.co.team.planner;

import kr.co.team.planner.entity.Member;
import kr.co.team.planner.entity.Plan;
import kr.co.team.planner.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class PlanRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PlanRepository planRepository;


    //Plan 목록 가져오는 메서드
    //@Test


    //특정 Plan 에 대한 정보를 가져오는 메서드
    //@Test
    public void testGetMovie(){
        List<Object []> result = planRepository.getPlanWithAll(38L);
        for(Object [] r : result){
            System.out.println(Arrays.toString(r));
        }
    }

}
