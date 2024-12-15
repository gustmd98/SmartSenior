package Sidenow.Smart_SeniorCenter.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequestDto {

    @NotEmpty(message = "사용자 이름은 필수항목입니다.")
    private String name;

    private String username;

    private String password;

    private String password2;

    private String birth;

    private String phonenum;

}
