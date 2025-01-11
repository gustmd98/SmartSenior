package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.Data;

@Data
public class RefreshTokenRequestDto {
    private String refreshToken;
    public String getRefreshToken() {
        return refreshToken;
    }
}
