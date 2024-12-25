package Sidenow.Smart_SeniorCenter.appointment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import Sidenow.Smart_SeniorCenter.user.entity.User;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 약속을 생성한 사용자

}


