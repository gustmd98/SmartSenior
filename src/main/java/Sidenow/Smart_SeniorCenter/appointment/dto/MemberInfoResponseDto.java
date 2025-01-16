package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfoResponseDto {  // 접근제어자 변경
    private String username;  // 사용자 이름
    private String phoneNumber;  // 전화번호
}

