package Sidenow.Smart_SeniorCenter.user.repository;

import Sidenow.Smart_SeniorCenter.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByUsername(String username);// 대소문자 구분 없이 아이디 찾기
}




