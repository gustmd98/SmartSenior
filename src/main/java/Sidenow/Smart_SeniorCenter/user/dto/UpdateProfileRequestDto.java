package Sidenow.Smart_SeniorCenter.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateProfileRequestDto {

    private String password;
    private String phonenum;
    private MultipartFile profileImage;
    private String favoriteplace;
}
