package Sidenow.Smart_SeniorCenter.user.controller;

import Sidenow.Smart_SeniorCenter.jwt.JwtGenerator;
import Sidenow.Smart_SeniorCenter.jwt.JwtProvider;
import Sidenow.Smart_SeniorCenter.jwt.JwtToken;
import Sidenow.Smart_SeniorCenter.user.dto.*;
import Sidenow.Smart_SeniorCenter.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtGenerator jwtGenerator;
    private final UserService userService;
    private final JwtProvider jwtProvider;

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

    @PostMapping("/refresh")//refrehapi 구현 아직안됨
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();


        if (jwtProvider.validateToken(refreshToken)) {

            Claims claims = (Claims) jwtProvider.getAuthentication(refreshToken).getPrincipal();  // getAuthentication을 호출하여 Claims 얻기
            Long userId = Long.valueOf(claims.getSubject());

            JwtToken newJwtToken = jwtGenerator.generateToken(userId);

            return ResponseEntity.ok(new JwtToken("Bearer", newJwtToken.getAccessToken(), newJwtToken.getRefreshToken()));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh Token이 유효하지 않습니다.");
        }
    }


    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @ModelAttribute UpdateProfileRequestDto updateProfileRequestDto,
            Principal principal) {

        userService.updateProfile(principal, updateProfileRequestDto);  // 인증된 사용자 정보와 DTO 전달
        return ResponseEntity.ok("Profile updated successfully");
    }
}
