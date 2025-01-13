package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequestDto {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer maxParticipants;  // 최대 참여자 수 설정
}

