package kr.co.team.planner;

import kr.co.team.planner.dto.GuestBookDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.entity.GuestBook;
import kr.co.team.planner.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private GuestBookService guestBookService;

    //@Test
    public void registerTest(){
        GuestBookDTO dto = GuestBookDTO.builder()
                .title("제목")
                .content("내용")
                .writer("member")
                .build();
        Long gno = guestBookService.register(dto);
        System.out.println(gno);
    }

    @Test
    public void listTest(){
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .page(1)
                        .size(10)
                        .build();
        PageResponseDTO<GuestBookDTO, GuestBook>
                pageResponseDTO = guestBookService.getList(pageRequestDTO);
        for(GuestBookDTO dto : pageResponseDTO.getDtoList()){
            System.out.println(dto);
        }

        //이전 과 다음 링크 여부 와 전체 페이지 개수 확인
        System.out.println("===================================");
        System.out.println("이전:" + pageResponseDTO.isPrev());
        System.out.println("다음:" + pageResponseDTO.isNext());
        System.out.println("전체:" + pageResponseDTO.getTotalPage());
        //페이지 번호 목록 출력
        System.out.println("===================================");
        for(Integer i : pageResponseDTO.getPageList()){
            System.out.print(i + "\t");
        }
    }
}
