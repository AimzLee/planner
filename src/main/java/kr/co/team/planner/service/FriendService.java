package kr.co.team.planner.service;

import kr.co.team.planner.dto.FriendDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.entity.Friend;
import kr.co.team.planner.entity.User;

public interface FriendService {
    //친구요청
    Long sendRequest(FriendDTO friendDTO);
    //친구요청목록
    PageResponseDTO <FriendDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    //요청상태 변경
    void modify(FriendDTO friendDTO);


    default Friend dtoToEntity(FriendDTO dto) {
        User request = User.builder()
                .code(dto.getRequestCode())
                .build();
        User response = User.builder()
                .code(dto.getResponseCode())
                .build();
        Friend friend = Friend.builder()
                .fno(dto.getFno())
                .stage(dto.getStage())
                .msg(dto.getMsg())
                .request(request)
                .response(response).build();
        return friend;
    }

    default FriendDTO entityToDTO(Friend friend, User request, User response) {
        FriendDTO dto = FriendDTO.builder()
                .fno(friend.getFno())
                .stage(friend.getStage())
                .msg(friend.getMsg())
                .regDate(friend.getRegDate())
                .modDate(friend.getModDate())
                .requestCode(request.getCode())
                .responseCode(response.getCode())
                .requestNick(request.getNick())
                .responseNick(response.getNick())
                .build();
        return dto;
    }
}
