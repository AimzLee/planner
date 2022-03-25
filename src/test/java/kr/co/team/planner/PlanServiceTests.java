package kr.co.team.planner;


import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.dto.PlanDTO;
import kr.co.team.planner.service.PlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class PlanServiceTests {
    @Autowired
    private PlanService planService;
    //@Test
    public void testRegister() {
        PlanDTO dto = PlanDTO.builder()
                .title("테스트일정02")
                .description("테스트로 넣어봅니다")
                .start(LocalDateTime.parse("2020-01-01T09:30:00"))
                .end(LocalDateTime.parse("2020-01-01T10:30:00"))
                .share("비공개")
                .writerCode(2L)
                .build();
        Long pno = planService.register(dto);
        System.out.println("일정등록성공 :"+pno);
    }

    @Test //여러개 넣어보기
    public void planTest() {
        IntStream.rangeClosed(1,9).forEach(i->{
            PlanDTO dto = PlanDTO.builder()
                    .title("테스트일정_12_"+i)
                    .description("테스트로 넣어봅니다")
                    .start(LocalDateTime.parse("2020-01-01T09:30:00"))
                    .end(LocalDateTime.parse("2020-01-01T10:30:00"))
                    .location("장소"+i)
                    .share("비공개")
                    .writerCode(1L)
                    .build();
            Long pno = planService.register(dto);
        });
    }

   // @Test
    public void testList() {
        //1페이지 15개
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<PlanDTO, Object[]> result = planService.getList(pageRequestDTO);
        for (PlanDTO planDTO : result.getDtoList()) {
            System.out.println(planDTO);
        }
    }
/*
    @Test
    public void testGet() {
        Long pno = 2L;
        PlanDTO planDTO = planService.get(pno);
        System.out.println(planDTO);
    }
*/
}
