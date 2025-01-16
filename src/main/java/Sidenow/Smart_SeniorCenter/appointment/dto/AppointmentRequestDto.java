package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequestDto {
    private String title;             // 약속 제목
    private String description;       // 약속 설명
    private LocalDateTime startTime;  // 시작 시간
    private LocalDateTime endTime;    // 종료 시간
    private Integer maxParticipants;  // 최대 참여자 수 설정
    private String location;          // 약속 장소

    // **추가된 필드**
    private String createdBy;         // 약속 생성자의 이름 (필수 입력)
    private String phoneNumber;       // 생성자의 연락처 (옵션)
}




