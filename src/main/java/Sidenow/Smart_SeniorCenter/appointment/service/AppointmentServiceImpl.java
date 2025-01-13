package Sidenow.Smart_SeniorCenter.appointment.service;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import Sidenow.Smart_SeniorCenter.appointment.entity.Appointment;
import Sidenow.Smart_SeniorCenter.appointment.entity.AppointmentStatus;
import Sidenow.Smart_SeniorCenter.appointment.repository.AppointmentRepository;
import Sidenow.Smart_SeniorCenter.user.entity.User;
import Sidenow.Smart_SeniorCenter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        if (requestDto.getMaxParticipants() < 2) {
            throw new IllegalArgumentException("최소 인원은 본인을 포함해 2명 이상이어야 합니다.");
        }

        Appointment appointment = Appointment.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .user(user)
                .maxParticipants(requestDto.getMaxParticipants())
                .participants(List.of(user.getUsername()))  // 본인 포함
                .status(AppointmentStatus.ACTIVE)
                .build();

        appointmentRepository.save(appointment);
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .title(appointment.getTitle())
                .description(appointment.getDescription())
                .statusMessage("약속 생성 완료")
                .currentParticipants(1)  // 본인 포함 초기 참여자 수
                .maxParticipants(requestDto.getMaxParticipants())
                .build();
    }

    @Override
    public String deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        appointmentRepository.delete(appointment);
        return "약속 삭제 완료";
    }

    @Override
    public String cancelParticipation(Long appointmentId, String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        if (!appointment.getParticipants().remove(username)) {
            throw new IllegalArgumentException("해당 사용자는 참여자가 아닙니다.");
        }

        // 참여자가 2명 이하가 되면 상태를 "취소됨"으로 변경
        if (appointment.getParticipants().size() < 2) {
            appointment.setStatus(AppointmentStatus.CANCELED);
        }

        appointmentRepository.save(appointment);
        return "참여 취소 완료";
    }

    @Override
    public String failAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        appointment.setStatus(AppointmentStatus.FAILED);
        appointmentRepository.save(appointment);
        return "약속이 파투 처리되었습니다.";
    }

    @Scheduled(cron = "0 0 * * * *") // 매 시간 정각마다 실행
    @Override
    public void autoCancelExpiredAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Appointment appointment : appointments) {
            if (appointment.getStartTime().isBefore(now) && appointment.getStatus() == AppointmentStatus.ACTIVE) {
                appointment.setStatus(AppointmentStatus.CANCELED);
                appointmentRepository.save(appointment);
            }
        }
    }

    // **수정된 부분: 최대 참여자 수 도달 시 상태 변경 및 예외 처리 추가**
    @Override
    public String joinAppointment(Long appointmentId, String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        if (appointment.getParticipants().size() >= appointment.getMaxParticipants()) {
            throw new IllegalStateException("참여 인원이 가득 찼습니다. 더 이상 신청할 수 없습니다.");
        }

        if (appointment.getParticipants().contains(username)) {
            throw new IllegalArgumentException("이미 약속에 참여한 사용자입니다.");
        }

        appointment.getParticipants().add(username);

        if (appointment.getParticipants().size() == appointment.getMaxParticipants()) {
            appointment.setStatus(AppointmentStatus.CONFIRMED);  // 최대 인원 도달 시 "확정" 상태
        }

        appointmentRepository.save(appointment);
        return "참여 신청 완료!";
    }

    // **내가 만든 약속 조회**
    @Override
    public List<AppointmentResponseDto> getAppointmentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Appointment> appointments = appointmentRepository.findByUser(user);
        return appointments.stream().map(appointment -> {
            String statusMessage;
            if (appointment.getStatus() == AppointmentStatus.CANCELED) {
                statusMessage = "취소됨";
            } else if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
                statusMessage = "확정";
            } else {
                statusMessage = "모집 중";
            }
            return AppointmentResponseDto.builder()
                    .id(appointment.getId())
                    .title(appointment.getTitle())
                    .description(appointment.getDescription())
                    .statusMessage(statusMessage)
                    .currentParticipants(appointment.getParticipants().size())
                    .maxParticipants(appointment.getMaxParticipants())
                    .build();
        }).collect(Collectors.toList());
    }

    // **내가 참여한 약속 조회**
    @Override
    public List<AppointmentResponseDto> getJoinedAppointments(String username) {
        List<Appointment> joinedAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getParticipants().contains(username))
                .toList();

        return joinedAppointments.stream().map(appointment -> {
            String statusMessage = switch (appointment.getStatus()) {
                case CANCELED -> "취소됨";
                case CONFIRMED -> "확정됨";
                default -> "모집 중";
            };
            return AppointmentResponseDto.builder()
                    .id(appointment.getId())
                    .title(appointment.getTitle())
                    .description(appointment.getDescription())
                    .statusMessage(statusMessage)
                    .currentParticipants(appointment.getParticipants().size())
                    .maxParticipants(appointment.getMaxParticipants())
                    .build();
        }).collect(Collectors.toList());
    }
}
