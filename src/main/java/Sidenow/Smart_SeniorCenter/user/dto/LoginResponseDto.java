package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String message;
    private String username;
    private String accessToken;
    private String refreshToken;
}
