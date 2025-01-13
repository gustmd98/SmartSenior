package Sidenow.Smart_SeniorCenter.user.service;

import Sidenow.Smart_SeniorCenter.user.dto.LoginRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.LoginResponseDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupRequestDto;
import Sidenow.Smart_SeniorCenter.user.dto.SignupResponseDto;
import Sidenow.Smart_SeniorCenter.user.entity.User;
import Sidenow.Smart_SeniorCenter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 메서드
    @Override
    public SignupResponseDto create(SignupRequestDto signupRequestDto) {
        // 아이디 중복 확인
        if (userRepository.findByUsernameIgnoreCase(signupRequestDto.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재함");
        }

        // 비밀번호 확인
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getPassword2())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        String encode = passwordEncoder.encode(signupRequestDto.getPassword());

        // User 생성 및 저장
        User user = User.builder()
                .name(signupRequestDto.getName())
                .username(signupRequestDto.getUsername())
                .birth(signupRequestDto.getBirth())
                .phonenum(signupRequestDto.getPhonenum())
                .password(encode)
                .build();
        User savedUser = userRepository.save(user);

        // 응답 반환
        return new SignupResponseDto(savedUser.getId(), "가입 성공");
    }

    // 아이디 중복 확인 메서드
    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    // 로그인 메서드
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 사용자 존재 여부 확인
        User user = userRepository.findByUsernameIgnoreCase(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("아이디가 존재하지 않습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        // 로그인 성공 응답 반환
        return LoginResponseDto.builder()
                .message("로그인 성공")
                //.token(token) // JWT 등을 사용하려면 여기에 추가
                .username(user.getUsername())
                .build();
    }
}

