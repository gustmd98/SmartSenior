package Sidenow.Smart_SeniorCenter.appointment.service;

import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentRequestDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.AppointmentResponseDto;
import Sidenow.Smart_SeniorCenter.appointment.dto.MemberInfoResponseDto;
import Sidenow.Smart_SeniorCenter.appointment.entity.Appointment;
import Sidenow.Smart_SeniorCenter.appointment.entity.AppointmentStatus;
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

    // **로그인 기반 약속 생성 기능**
    @Override
    public AppointmentResponseDto createAppointment(Long userId, AppointmentRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (requestDto.getMaxParticipants() < 2) {
            throw new IllegalArgumentException("최소 인원은 2명 이상이어야 합니다.");
        }

        Appointment appointment = Appointment.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .maxParticipants(requestDto.getMaxParticipants())
                .participants(List.of(user.getUsername()))
                .user(user)
                .status(AppointmentStatus.ACTIVE)
                .build();

        appointmentRepository.save(appointment);
        return buildAppointmentResponseDto(appointment);
    }

    // **약속 삭제 기능**
    @Override
    public String deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));
        appointmentRepository.delete(appointment);
        return "약속 삭제 완료";
    }

    // **약속 참여 취소 기능**
    @Override
    public String cancelParticipation(Long appointmentId, String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        if (!appointment.getParticipants().remove(username)) {
            throw new IllegalArgumentException("해당 사용자는 참여자가 아닙니다.");
        }

        if (appointment.getParticipants().size() < 2) {
            appointment.setStatus(AppointmentStatus.CANCELED);
        }

        appointmentRepository.save(appointment);
        return "참여 취소 완료";
    }

    // **약속 파투 처리**
    @Override
    public String failAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        appointment.setStatus(AppointmentStatus.FAILED);
        appointmentRepository.save(appointment);
        return "약속이 파투 처리되었습니다.";
    }

    // **약속 생성 조회**
    @Override
    public List<AppointmentResponseDto> getAppointmentsByUsername(String username) {
        List<Appointment> appointments = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getParticipants().contains(username))
                .collect(Collectors.toList());

        return appointments.stream().map(this::buildAppointmentResponseDto).collect(Collectors.toList());
    }

    // **약속 참여 신청**
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
            appointment.setStatus(AppointmentStatus.CONFIRMED);
        }

        appointmentRepository.save(appointment);
        return "참여 신청 완료!";
    }

    // **전체 약속 조회**
    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream().map(this::buildAppointmentResponseDto).collect(Collectors.toList());
    }

    // **내가 만든 약속 조회 (userId 기반)**
    @Override
    public List<AppointmentResponseDto> getAppointmentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Appointment> appointments = appointmentRepository.findByUser(user);
        return appointments.stream().map(this::buildAppointmentResponseDto).collect(Collectors.toList());
    }

    // **내가 참여한 약속 조회 (username 기반)**
    @Override
    public List<AppointmentResponseDto> getJoinedAppointments(String username) {
        List<Appointment> joinedAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getParticipants().contains(username))
                .collect(Collectors.toList());

        return joinedAppointments.stream().map(this::buildAppointmentResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<MemberInfoResponseDto> getAppointmentMembers(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("약속을 찾을 수 없습니다."));

        return appointment.getParticipants().stream().map(username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("참여자 정보를 찾을 수 없습니다."));
            return new MemberInfoResponseDto(user.getUsername(), user.getPhonenum());
        }).toList();
    }

    // **Response DTO 생성 메서드**
    private AppointmentResponseDto buildAppointmentResponseDto(Appointment appointment) {
        List<MemberInfoResponseDto> members = appointment.getParticipants().stream().map(username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("참여자 정보를 찾을 수 없습니다."));
            return new MemberInfoResponseDto(user.getUsername(), user.getPhonenum());
        }).collect(Collectors.toList());

        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .title(appointment.getTitle())
                .description(appointment.getDescription())
                .location(appointment.getLocation())
                .statusMessage(appointment.getStatus().toString())
                .currentParticipants(appointment.getParticipants().size())
                .maxParticipants(appointment.getMaxParticipants())
                .members(members) // 멤버 정보 추가
                .build();
    }
}








