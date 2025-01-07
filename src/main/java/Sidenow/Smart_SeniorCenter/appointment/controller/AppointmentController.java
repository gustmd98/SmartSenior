package Sidenow.Smart_SeniorCenter.appointment.controller;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
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

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(
            @PathVariable Long userId,
            @RequestBody AppointmentRequestDto requestDto) {
        AppointmentResponseDto response = appointmentService.createAppointment(userId, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 생성 성공", response));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<String>> deleteAppointment(@PathVariable Long appointmentId) {
        String response = appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "약속 삭제 성공", response));
    }

    @PostMapping("/{appointmentId}/cancel-participation")
    public ResponseEntity<ApiResponse<String>> cancelParticipation(@PathVariable Long appointmentId, @RequestParam String username) {
        String response = appointmentService.cancelParticipation(appointmentId, username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", response, null));
    }

    @PostMapping("/{appointmentId}/fail")
    public ResponseEntity<ApiResponse<String>> failAppointment(@PathVariable Long appointmentId) {
        String response = appointmentService.failAppointment(appointmentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", response, null));
    }

    // **추가된 코드 1: 약속 참여 신청 기능**
    @PostMapping("/{appointmentId}/join")
    public ResponseEntity<ApiResponse<String>> joinAppointment(@PathVariable Long appointmentId, @RequestParam String username) {
        String response = appointmentService.joinAppointment(appointmentId, username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", response, null));
    }

    // **추가된 코드 2: 내가 만든 약속 조회 기능**
    @GetMapping("/my-appointments/{userId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAppointmentsByUser(@PathVariable Long userId) {
        List<AppointmentResponseDto> response = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "내가 만든 약속 조회 성공", response));
    }

    // **추가된 코드 3: 내가 참여한 약속 조회 기능**
    @GetMapping("/joined-appointments/{username}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getJoinedAppointments(@PathVariable String username) {
        List<AppointmentResponseDto> response = appointmentService.getJoinedAppointments(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "200", "내가 참여한 약속 조회 성공", response));
    }
}






