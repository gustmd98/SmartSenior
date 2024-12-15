package Sidenow.Smart_SeniorCenter.user.controller;

import Sidenow.Smart_SeniorCenter.jwt.JwtGenerator;
import Sidenow.Smart_SeniorCenter.jwt.JwtToken;
import Sidenow.Smart_SeniorCenter.user.dto.LoginRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.LoginResponseDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupResponseDto;
import Sidenow.Smart_SeniorCenter.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){

        SignupResponseDto response= userService.create(signupRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto response=userService.login(loginRequestDto);

        return ResponseEntity.ok(response);
    }

}
