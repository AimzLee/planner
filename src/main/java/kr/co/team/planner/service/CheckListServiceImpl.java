package kr.co.team.planner.service;

import kr.co.team.planner.dto.CheckListDTO;
import kr.co.team.planner.entity.CheckList;
import kr.co.team.planner.entity.Plan;
import kr.co.team.planner.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CheckListServiceImpl implements CheckListService{
    private final CheckListRepository checkListRepository;

    @Override
    public List<CheckListDTO> getList(Long pno) {
        log.info(pno);
        Plan plan = Plan.builder().pno(pno).build();
        List<CheckList> result = checkListRepository.findByPlan(plan);
        return result.stream().map(planReview -> entityToDTO(planReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(CheckListDTO checkListDTO) {
        CheckList checkList = dtoToEntity(checkListDTO);
        checkListRepository.save(checkList);
        return checkList.getCno();
    }

    @Override
    public void remove(Long cno) {
        log.info("remove " + cno);
        checkListRepository.deleteById(cno);
    }

    @Override
    public void modify(CheckListDTO checkListDTO) {
        Optional<CheckList> result = checkListRepository.findById(checkListDTO.getCno());
        if(result.isPresent()){
            CheckList checkList = result.get(); checkList.changeTodo(checkListDTO.getTodo()); checkList.changeDone(checkListDTO.getDone());
            checkListRepository.save(checkList);
        }
    }
}
