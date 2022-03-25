package kr.co.team.planner.dto;
//from SSW
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserDTO {
    private Long code;

    @NotBlank(message = "Id value is a necessary component")
//    @Pattern(regexp = "(?=.*[a-zA-Z0-9])(?=\\\\S+$).{4,12}",
//            message = "Id Must be 2 to 10 characters in lowercase letters and  number.")
    private String id;

    @NotBlank(message = "Password is a necessary component")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "Password must be 8 to 16 characters in uppercase and lowercase letters, numbers, and special characters.")
    private String pw;

    @Email(message = "Email format is incorrect.")
    @NotBlank(message = "Email is a necessary component")
    private String email;

    @NotBlank(message = "Nickname is necessary")
    private String nick;

    @NotBlank(message = "Gender is necessary")
    private String gender;

    @NotBlank(message = "Birthday is necessary")
    private String birthday;
    private String status;
    private LocalDateTime regDate;
}