package Sidenow.Smart_SeniorCenter.appointment.entity;

import jakarta.persistence.*;
import lombok.*;
import Sidenow.Smart_SeniorCenter.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

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

    private String title;           // 약속 제목
    private String description;     // 약속 설명
    private LocalDateTime startTime; // 시작 시간
    private LocalDateTime endTime;   // 종료 시간
    private String location;         // 약속 장소
    private Integer maxParticipants; // 최대 참여자 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 약속 생성자와 연결된 외래 키
    private User user; // 약속 생성자

    @ElementCollection
    private List<String> participants; // 참여자 목록 (username)

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;  // 약속 상태 (ACTIVE, CANCELED, FAILED)
}




