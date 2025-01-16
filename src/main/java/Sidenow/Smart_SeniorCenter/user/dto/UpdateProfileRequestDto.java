package Sidenow.Smart_SeniorCenter.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileRequestDto {
    private String password;

   // @Pattern(regexp = "^[0-9]{11}$", message = "Invalid phone number format")
    private String phonenum;

  //  private MultipartFile profileImage;
    private String favoriteplace;
}
