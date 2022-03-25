package kr.co.team.planner.dto;
//from LDJ
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlanDTO {
    private Long pno;
    private Long priority;
    private String title;
    private String description;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;
    private String category;
    private String share;
    private String writerNick; //작성자의 닉네임
    private Long writerCode; //작성자의 code
    private int checkListCount; //체크리스트 갯수
    private int shareCount;// 공유한 사람 수
}
