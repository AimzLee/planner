package kr.co.team.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendDTO {
    private Long fno;
    private String stage;
    private String msg;   //메세지
    private Long requestCode; //요청한 사람
    private String requestNick;
    private Long responseCode; //요청받은 사람
    private String responseNick;
    private LocalDateTime regDate, modDate;
}
