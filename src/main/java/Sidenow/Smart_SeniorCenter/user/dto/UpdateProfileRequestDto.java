package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileRequestDto {
    private String password;
    private String phonenum;
    private MultipartFile profileImage;
    private String favoriteplace;
}
