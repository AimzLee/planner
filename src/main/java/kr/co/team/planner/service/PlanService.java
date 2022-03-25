package kr.co.team.planner.service;
//from LDJ
import kr.co.team.planner.dto.PageRequestDTO;
import kr.co.team.planner.dto.PageResponseDTO;
import kr.co.team.planner.dto.PlanDTO;
import kr.co.team.planner.entity.Plan;
import kr.co.team.planner.entity.User;


public interface PlanService {
    //일정등록
    Long register(PlanDTO dto);
    //임시메서드(일정전체보기)
    PageResponseDTO<PlanDTO,Object[]> getList(PageRequestDTO pageRequestDTO);
    //일정상세보기
    PlanDTO getPlan(Long pno);
    //일정수정
    void modify(PlanDTO planDTO);
    //일정삭제
    void removeWithCheckList(Long pno);

    default Plan dtoToEntity(PlanDTO dto){
        User user = User.builder().code(dto.getWriterCode())
                .nick(dto.getWriterNick())
                .build();
        Plan plan = Plan.builder()
                .priority(dto.getPriority())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .start(dto.getStart())
                .end(dto.getEnd())
                .category(dto.getCategory())
                .share(dto.getShare())
                .writer(user)
                .build();
        return plan;
    }

    default PlanDTO entityToDTO(Plan plan, User user, Long checkListCount, Long shareCount) {
        PlanDTO dto = PlanDTO.builder()
                .pno(plan.getPno())
                .priority(plan.getPriority())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .location(plan.getLocation())
                .category(plan.getCategory())
                .share(plan.getShare())
                .start(plan.getStart())
                .end(plan.getEnd())
                .writerCode(user.getCode())
                .writerNick(user.getNick())
                .checkListCount(checkListCount.intValue())
                .shareCount(shareCount.intValue())
                .build();
        return dto;
    }

}