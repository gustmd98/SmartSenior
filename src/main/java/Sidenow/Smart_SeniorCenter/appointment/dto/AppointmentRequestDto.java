package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDto {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

