package kr.co.team.planner.service;

import kr.co.team.planner.dto.CheckListDTO;
import kr.co.team.planner.entity.CheckList;
import kr.co.team.planner.entity.Plan;
import org.hibernate.annotations.Check;

import java.util.List;

public interface CheckListService {
    //영화에 해당하는 리뷰를 가져오기
    List<CheckListDTO> getList(Long pno);

    //리뷰 등록
    Long register(CheckListDTO checkListDTO);

    //리뷰 삭제
    void remove(Long cno);

    //리뷰 수정
    void modify(CheckListDTO checkListDTO);

    default CheckList dtoToEntity(CheckListDTO checkListDTO){
        CheckList checkList = CheckList.builder()
                .cno(checkListDTO.getCno())
                .todo(checkListDTO.getTodo())
                .done(checkListDTO.getDone())
                .plan(Plan.builder().pno(checkListDTO.getPno()).build())
                .build();

        return checkList;
    }

    default CheckListDTO entityToDTO(CheckList checkList) {
        CheckListDTO dto = CheckListDTO.builder()
                .cno(checkList.getCno())
                .pno(checkList.getPlan().getPno())
                .todo(checkList.getTodo())
                .done(checkList.getDone())
                .build();

        return dto;
    }
}
