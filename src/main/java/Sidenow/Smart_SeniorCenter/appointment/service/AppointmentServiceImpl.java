package Sidenow.Smart_SeniorCenter.appointment.service;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import Sidenow.Smart_SeniorCenter.appointment.entity.Appointment;
import Sidenow.Smart_SeniorCenter.appointment.repository.AppointmentRepository;
import Sidenow.Smart_SeniorCenter.user.entity.User;
import Sidenow.Smart_SeniorCenter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    public AppointmentResponseDto createAppointment(Long userId, AppointmentRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Appointment appointment = Appointment.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .user(user)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponseDto.builder()
                .id(savedAppointment.getId())
                .title(savedAppointment.getTitle())
                .description(savedAppointment.getDescription())
                .message("약속이 성공적으로 생성되었습니다.")
                .build();
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return appointmentRepository.findByUser(user).stream()
                .map(appointment -> AppointmentResponseDto.builder()
                        .id(appointment.getId())
                        .title(appointment.getTitle())
                        .description(appointment.getDescription())
                        .message("약속 목록")
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        appointmentRepository.delete(appointment);
        return "약속이 성공적으로 삭제되었습니다.";
    }

    @Override
    public AppointmentResponseDto updateAppointment(Long appointmentId, AppointmentRequestDto requestDto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        // 업데이트 내용 반영
        appointment.setTitle(requestDto.getTitle());
        appointment.setDescription(requestDto.getDescription());
        appointment.setStartTime(requestDto.getStartTime());
        appointment.setEndTime(requestDto.getEndTime());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return AppointmentResponseDto.builder()
                .id(updatedAppointment.getId())
                .title(updatedAppointment.getTitle())
                .description(updatedAppointment.getDescription())
                .message("약속이 성공적으로 수정되었습니다.")
                .build();
    }
}

