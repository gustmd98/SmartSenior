package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileResponseDto {
    private String username;
    private String birth;
    private String name;
    private String phonenum;
    private String favoriteplace;
    private String profileImagePath;
}
