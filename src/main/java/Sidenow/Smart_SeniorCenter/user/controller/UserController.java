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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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



//    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> updateProfile(
//            @RequestPart("data") UpdateProfileRequestDto updateProfileRequestDto,  // JSON 데이터를 받음
//            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage, // 이미지 파일 받음
//            Principal principal) {
//
//        // 서비스 호출
//        String result = userService.updateProfile(principal, updateProfileRequestDto, profileImage);
//
//        // 성공 응답
//        return ResponseEntity.ok(result);
//    }
    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfile(
            @RequestPart("data") UpdateProfileRequestDto updateProfileRequestDto,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            Principal principal) {

        System.out.println("Data received: " + updateProfileRequestDto);
        System.out.println("Profile image received: " + (profileImage != null ? profileImage.getOriginalFilename() : "None"));

        // 서비스 호출
        String result = userService.updateProfile(principal, updateProfileRequestDto, profileImage);

        // 성공 응답
        return ResponseEntity.ok(result);
    }
//    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> updateProfile(
//            @RequestPart("data") UpdateProfileRequestDto updateProfileRequestDto,
//            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
//            Principal principal) {
//
//        userService.updateProfile(principal, updateProfileRequestDto, profileImage);
//        return ResponseEntity.ok("Profile updated successfully");
//    }
}




