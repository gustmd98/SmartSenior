package Sidenow.Smart_SeniorCenter.appointment.service;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(Long userId, AppointmentRequestDto requestDto);
    List<AppointmentResponseDto> getAppointmentsByUser(Long userId);
    String deleteAppointment(Long appointmentId);
    AppointmentResponseDto updateAppointment(Long appointmentId, AppointmentRequestDto requestDto);
}

