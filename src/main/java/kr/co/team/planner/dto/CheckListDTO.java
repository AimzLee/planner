package kr.co.team.planner.dto;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CheckListDTO {
    private Long cno;
    private String todo;
    private String done;
    private Long pno;
    private Long code;
}
