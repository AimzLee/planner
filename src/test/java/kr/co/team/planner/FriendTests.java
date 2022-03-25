package kr.co.team.planner;

import kr.co.team.planner.dto.FriendDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.repository.FriendRepository;
import kr.co.team.planner.service.FriendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class FriendTests {
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendService friendService;

    //받은 요청 목록 (전체)
    @Test
    public void testWithRequest() {
        Pageable pageable = PageRequest.of(0,5, Sort.by("regDate").descending());
        Page<Object[]>result = friendRepository.getSendWithRequest(pageable);
        result.get().forEach(row->{
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }



    @Test
    public void testGetFriendByUser() {
        List<Object[]> result = friendRepository.getFriendByUser(1L);
        for(Object[] arr: result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    //친구맺기 신청
    @Test
    public void testSendRequest() {
        FriendDTO friendDTO = FriendDTO.builder()
                .msg("친구요청 테스트 버전1")
                .requestCode(3L)
                .responseCode(1L)
                .build();
        System.out.println("친구요청성공:"+friendService.sendRequest(friendDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<FriendDTO, Object[]> result = friendService.getList(pageRequestDTO);
        for(FriendDTO friendDTO : result.getDtoList()) {
            System.out.println(friendDTO);
        }
    }

    //상태변경
    @Test
    public void testModify() {
        FriendDTO friendDTO = FriendDTO.builder().fno(1L).stage("수락").build();
        friendService.modify(friendDTO);
    }
}
