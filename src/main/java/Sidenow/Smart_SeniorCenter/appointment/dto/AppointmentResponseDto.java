package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppointmentResponseDto {
    private Long id;                    // 약속 ID
    private String title;                // 약속 제목
    private String description;          // 약속 설명
    private String location;             // 약속 장소
    private String dateStatus;           // D-Day, D-N 등
    private Integer currentParticipants; // 현재 참여자 수
    private Integer maxParticipants;     // 최대 참여자 수
    private String statusMessage;        // "모집 중", "마감" 등 상태 메시지
    private boolean isCreator;           // 생성자인지 여부
    private String createdBy;            // 생성자 이름 (로그인된 사용자 정보 기반)
    private String phoneNumber;          // 생성자 연락처 정보 (선택사항)

    // **추가된 필드: 약속 멤버 정보 리스트**
    private List<MemberInfoResponseDto> members; // 약속에 참여한 멤버들의 정보
}





