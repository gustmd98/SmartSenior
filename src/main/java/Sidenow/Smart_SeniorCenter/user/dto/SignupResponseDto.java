package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.Data;

@Data
public class SignupResponseDto {

    private Long  userId;
    private String message;

    public SignupResponseDto(Long userId,String message){
        this.userId = userId;
        this.message=message;
    }

}
