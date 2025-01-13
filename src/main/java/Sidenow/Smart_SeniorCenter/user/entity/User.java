package Sidenow.Smart_SeniorCenter.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 10)
    private String name;

    @Column(unique = true)//controller에 중복검증로직이 있어도 동시에 접속되면 중복될수있어서 unique 조건 추가
    private String username;

    private String password;

    private String birth;

    private String address;

    @Column(unique = true)
    private String phonenum;

    private String profileImagePath;

    private String favoriteplace;

}
