package Sidenow.Smart_SeniorCenter.appointment.service;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(Long userId, AppointmentRequestDto requestDto);
    String deleteAppointment(Long appointmentId);
    String cancelParticipation(Long appointmentId, String username);
    String failAppointment(Long appointmentId);
    String joinAppointment(Long appointmentId, String username);
    List<AppointmentResponseDto> getAppointmentsByUser(Long userId);
    List<AppointmentResponseDto> getJoinedAppointments(String username);
    void autoCancelExpiredAppointments();
}





