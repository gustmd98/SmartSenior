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

    private String username;

//    @Column(length = 20)
    private String password;

    private String birth;

    private String address;

    @Column(unique = true)
    private String phonenum;


}
