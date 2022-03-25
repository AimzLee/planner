package kr.co.team.planner.dto;
//from SSW
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@ToString
public class AnswerDTO {
    private Long ano;
    private Long code;
    private Long qno;

    @NotBlank(message ="please insert Answer by the context")
    private String answer;
}
