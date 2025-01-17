package Sidenow.Smart_SeniorCenter.user.service;

import Sidenow.Smart_SeniorCenter.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface UserService {

   SignupResponseDto create(SignupRequestDto signupRequestDto);

   LoginResponseDto login(LoginRequestDto loginRequestDto);

   // 중복 아이디 확인 메서드 추가
   boolean isUsernameAvailable(String username);
   String updateProfile(Principal principal, UpdateProfileRequestDto updateProfileRequestDto);

  // String saveProfileImage(MultipartFile profileImage);
   UserProfileDto getUserProfile(String userid);


   String saveProfileImage(String username, MultipartFile file) throws IOException;
}

