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
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupResponseDto create(SignupRequestDto signupRequestDto){

        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new IllegalStateException("이미 존재함");
        }
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getPassword2())){
            throw new IllegalStateException("비밀번호 일치x");
        }

        String encode = passwordEncoder.encode(signupRequestDto.getPassword());

        User user = User.builder()
                .name(signupRequestDto.getName())
                .username(signupRequestDto.getUsername())
                .birth(signupRequestDto.getBirth())
                .password(encode)
                .build();
        User savedUser=userRepository.save(user);

        return new SignupResponseDto(savedUser.getId(),"가입성공");
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("아이디 존재x"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return LoginResponseDto.builder()
                .message("로그인 성공")
                .token(token)
                .username(user.getUsername())
                .build();
    }
}
