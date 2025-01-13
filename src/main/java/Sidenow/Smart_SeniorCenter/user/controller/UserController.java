package Sidenow.Smart_SeniorCenter.user.controller;

import Sidenow.Smart_SeniorCenter.user.dto.LoginRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.LoginResponseDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupResponseDto;
import Sidenow.Smart_SeniorCenter.apiPayload.ApiResponse;
import Sidenow.Smart_SeniorCenter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; // Map import

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}
