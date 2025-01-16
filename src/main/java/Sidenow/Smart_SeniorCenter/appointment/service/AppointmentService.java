package Sidenow.Smart_SeniorCenter.appointment.service;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.MemberInfoResponseDto;

import java.util.List;

public interface AppointmentService {

    // 약속 생성 (로그인 기반)
    AppointmentResponseDto createAppointment(Long userId, AppointmentRequestDto requestDto);

    // 전체 약속 조회
    List<AppointmentResponseDto> getAllAppointments();

    // 약속 참가
    String joinAppointment(Long appointmentId, String username);

    // 약속 삭제
    String deleteAppointment(Long appointmentId);

    // 참여 취소
    String cancelParticipation(Long appointmentId, String username);

    // 약속 실패 처리
    String failAppointment(Long appointmentId);

    // 특정 사용자가 생성한 약속 조회
    List<AppointmentResponseDto> getAppointmentsByUser(Long userId);

    // 특정 사용자가 참가한 약속 조회
    List<AppointmentResponseDto> getJoinedAppointments(String username);

    // 특정 사용자의 약속 조회 (username 기반 조회)
    List<AppointmentResponseDto> getAppointmentsByUsername(String username);

    // **새로운 기능: 약속 멤버 조회 (멤버 정보 반환)**
    List<MemberInfoResponseDto> getAppointmentMembers(Long appointmentId);

}




