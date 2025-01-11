package Sidenow.Smart_SeniorCenter.user.service;

import Sidenow.Smart_SeniorCenter.jwt.JwtGenerator;
import Sidenow.Smart_SeniorCenter.jwt.JwtToken;
import Sidenow.Smart_SeniorCenter.user.dto.*;
import Sidenow.Smart_SeniorCenter.user.entity.User;
import Sidenow.Smart_SeniorCenter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private static final String UPLOAD_DIR = "uploads/";

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
                .phonenum(signupRequestDto.getPhonenum())
                .password(encode)
                .build();
        try {
            User savedUser = userRepository.save(user);
            return new SignupResponseDto(savedUser.getId(), "가입성공");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("이미 존재함");
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("아이디 존재x"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        JwtToken jwtToken = jwtGenerator.generateToken(user.getId());


        return LoginResponseDto.builder()
                .message("로그인 성공")
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .username(user.getUsername())
                .build();
    }

    public String updateProfile(Principal principal, UpdateProfileRequestDto updateProfileRequestDto) {

        String username = principal.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // updateProfileRequestDto를 통해 받은 데이터로 사용자 정보 수정
        if (updateProfileRequestDto.getPhonenum() != null) {
            user.setPhonenum(updateProfileRequestDto.getPhonenum());
        }
        if (updateProfileRequestDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateProfileRequestDto.getPassword()));
        }
        if (updateProfileRequestDto.getFavoriteplace() != null) {
            user.setFavoriteplace(updateProfileRequestDto.getFavoriteplace());
        }
        // 프로필 이미지가 포함되어 있으면 파일을 저장하고 경로를 설정
        if (updateProfileRequestDto.getProfileImage() != null) {

            String existingImagePath = user.getProfileImagePath();
            if (existingImagePath != null && !existingImagePath.isEmpty()) {
                // 이전 이미지를 파일 시스템에서 삭제
                try {
                    Files.deleteIfExists(Paths.get(existingImagePath));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete old profile image", e);
                }
            }
            try {
                String profileImagePath = saveProfileImage(username, updateProfileRequestDto.getProfileImage());
                user.setProfileImagePath(profileImagePath);  // 저장된 이미지 경로 설정
            } catch (IOException e) {
                throw new RuntimeException("Profile image upload failed", e);
            }
        }
        userRepository.save(user);

        return "Updated successfully";
    }

    @Override
    public String saveProfileImage(String username, MultipartFile file) throws IOException {

        String userDirectory = "uploads/" + username;
        Path userDirPath = Paths.get(userDirectory);
        if (!Files.exists(userDirPath)) {
            Files.createDirectories(userDirPath);  // 사용자 디렉토리가 없으면 생성
        }

        // 파일 이름 생성 (원본 파일 이름을 그대로 사용할 수도 있지만, 고유성을 위해 수정 가능)
        String originalFileName = file.getOriginalFilename();
        String fileName = username + "_" + System.currentTimeMillis() + "_" + originalFileName;

        // 파일 경로 설정
        Path targetLocation = userDirPath.resolve(fileName);

        // 파일 저장
        Files.copy(file.getInputStream(), targetLocation);

        return targetLocation.toString();
    }

}
