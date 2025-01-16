package Sidenow.Smart_SeniorCenter.appointment.controller;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.MemberInfoResponseDto;
import Sidenow.Smart_SeniorCenter.apiPayload.ApiResponse;
import Sidenow.Smart_SeniorCenter.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // **약속 생성 기능 (로그인 기반)**
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(
            @PathVariable Long userId,
            @RequestBody AppointmentRequestDto requestDto) {
        AppointmentResponseDto response = appointmentService.createAppointment(userId, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 생성 성공", response));
    }

    // **약속 삭제 기능**
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<String>> deleteAppointment(@PathVariable Long appointmentId) {
        String response = appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 삭제 성공", response));
    }

    // **약속 참여 취소 기능**
    @PostMapping("/{appointmentId}/cancel-participation")
    public ResponseEntity<ApiResponse<String>> cancelParticipation(@PathVariable Long appointmentId, @RequestParam String username) {
        String response = appointmentService.cancelParticipation(appointmentId, username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "참여 취소 성공", response));
    }

    // **약속 실패 처리 기능**
    @PostMapping("/{appointmentId}/fail")
    public ResponseEntity<ApiResponse<String>> failAppointment(@PathVariable Long appointmentId) {
        String response = appointmentService.failAppointment(appointmentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 실패 처리 성공", response));
    }

    // **약속 참여 신청 기능**
    @PostMapping("/{appointmentId}/join")
    public ResponseEntity<ApiResponse<String>> joinAppointment(@PathVariable Long appointmentId, @RequestParam String username) {
        String response = appointmentService.joinAppointment(appointmentId, username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 신청 성공", response));
    }

    // **전체 약속 조회 기능**
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAllAppointments() {
        List<AppointmentResponseDto> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "전체 약속 조회 성공", appointments));
    }

    // **내가 만든 약속 조회 기능**
    @GetMapping("/my-appointments/{username}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAppointmentsByUsername(@PathVariable String username) {
        List<AppointmentResponseDto> response = appointmentService.getAppointmentsByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "내가 만든 약속 조회 성공", response));
    }

    // **내가 참여한 약속 조회 기능**
    @GetMapping("/joined-appointments/{username}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getJoinedAppointments(@PathVariable String username) {
        List<AppointmentResponseDto> response = appointmentService.getJoinedAppointments(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "내가 참여한 약속 조회 성공", response));
    }

    // **약속 멤버 정보 조회 기능**
    @GetMapping("/{appointmentId}/members")
    public ResponseEntity<ApiResponse<List<MemberInfoResponseDto>>> getAppointmentMembers(@PathVariable Long appointmentId) {
        List<MemberInfoResponseDto> members = appointmentService.getAppointmentMembers(appointmentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 멤버 정보 조회 성공", members));
    }
}








