package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentResponseDto {
    private Long id;
    private String title;
    private String description;
    private String dateStatus;   // D-Day, D-N 등
    private Integer currentParticipants; // 현재 참여자 수
    private Integer maxParticipants;     // 최대 참여자 수
    private String statusMessage;  // "신청 가능" / "마감"
    private boolean isCreator;  // 생성자인지 여부
}

