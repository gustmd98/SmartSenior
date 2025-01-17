package Sidenow.Smart_SeniorCenter.user.controller;

import Sidenow.Smart_SeniorCenter.apiPayload.ApiResponse;
import Sidenow.Smart_SeniorCenter.user.dto.*;

import Sidenow.Smart_SeniorCenter.jwt.JwtGenerator;
import Sidenow.Smart_SeniorCenter.jwt.JwtProvider;
import Sidenow.Smart_SeniorCenter.jwt.JwtToken;

import Sidenow.Smart_SeniorCenter.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

import java.util.Map; // Map import

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtGenerator jwtGenerator;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto response = userService.create(signupRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "회원가입 성공", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = userService.login(loginRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "로그인 성공", response));
    }

    @GetMapping("/check-username")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkUsername(@RequestParam String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "400", "아이디를 입력하세요.", null));
        }

        boolean isAvailable = userService.isUsernameAvailable(username);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "200", "중복 확인 완료", Map.of("available", isAvailable))
        );
    }

//        @PostMapping("/refresh")
//        public ResponseEntity<?> refreshToken (@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
//            String refreshToken = refreshTokenRequestDto.getRefreshToken();
//
//
//            if (jwtProvider.validateToken(refreshToken)) {
//
//                Claims claims = (Claims) jwtProvider.getAuthentication(refreshToken).getPrincipal();  // getAuthentication을 호출하여 Claims 얻기
//                Long userId = Long.valueOf(claims.getSubject());
//
//                JwtToken newJwtToken = jwtGenerator.generateToken(userId,roles);
//
//                return ResponseEntity.ok(new JwtToken("Bearer", newJwtToken.getAccessToken(), newJwtToken.getRefreshToken()));
//            } else {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh Token이 유효하지 않습니다.");
//            }
//        }



    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> getProfile(Principal principal) {
        if (principal == null) {
            System.out.println("JWT 토큰이 없거나 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "401", "User not authenticated", null));
        }

        String userId = principal.getName(); // JWT 토큰에서 userId가져옴 토큰에 userid를 넣어서 getName하면 id가 뽑힘

        UserProfileDto profileDto = userService.getUserProfile(userId);

        if (profileDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "404", "User profile not found", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "200", "프로필 데이터 가져오기 성공", profileDto));
    }


    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile (
            @RequestBody UpdateProfileRequestDto updateProfileRequestDto,
            Principal principal){
        userService.updateProfile(principal, updateProfileRequestDto);  // 인증된 사용자 정보와 DTO 전달
        return ResponseEntity.ok("Profile updated successfully");
    }


}
