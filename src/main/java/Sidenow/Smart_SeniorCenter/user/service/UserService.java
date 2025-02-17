package Sidenow.Smart_SeniorCenter.user.service;

import Sidenow.Smart_SeniorCenter.user.dto.LoginRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.LoginResponseDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupResponseDto;

public interface UserService {

   SignupResponseDto create(SignupRequestDto signupRequestDto);

   LoginResponseDto login(LoginRequestDto loginRequestDto);

}
