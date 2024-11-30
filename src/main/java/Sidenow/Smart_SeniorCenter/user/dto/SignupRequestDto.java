package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequestDto {

    private String name;

    private String username;

    private String password;

    private String password2;

    private String birth;


}
