package kr.co.team.planner.service;

import kr.co.team.planner.dto.FriendDTO;
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.entity.Friend;
import kr.co.team.planner.entity.User;
import kr.co.team.planner.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;

    @Override
    public Long sendRequest(FriendDTO dto) {
        Friend friend = dtoToEntity(dto);
        friendRepository.save(friend);
        return friend.getFno();
    }

    @Override
    public PageResponseDTO<FriendDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], FriendDTO> fn = (en -> entityToDTO((Friend)en[0],(User)en[1],(User)en[2] ));
        Page<Object[]> result = friendRepository.getSendWithRequest(pageRequestDTO.getPageable(Sort.by("regDate").descending()));
        return new PageResponseDTO<>(result, fn);
    }

    @Transactional
    @Override
    public void modify(FriendDTO friendDTO) {
        Optional<Friend> friend = friendRepository.findById(friendDTO.getFno());
        if(friend.isPresent()) {
            friend.get().changeStage(friendDTO.getStage());
            friendRepository.save(friend.get());
        }
    }


}
