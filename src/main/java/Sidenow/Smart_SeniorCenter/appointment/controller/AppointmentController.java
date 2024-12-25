package Sidenow.Smart_SeniorCenter.appointment.controller;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
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
    public ResponseEntity<AppointmentResponseDto> createAppointment(
            @PathVariable Long userId,
            @RequestBody AppointmentRequestDto requestDto) {
        AppointmentResponseDto response = appointmentService.createAppointment(userId, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByUser(@PathVariable Long userId) {
        List<AppointmentResponseDto> response = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long appointmentId) {
        String response = appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentRequestDto requestDto) {
        AppointmentResponseDto response = appointmentService.updateAppointment(appointmentId, requestDto);
        return ResponseEntity.ok(response);
    }
}


