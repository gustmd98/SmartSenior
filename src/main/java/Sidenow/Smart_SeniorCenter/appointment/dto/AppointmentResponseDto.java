package Sidenow.Smart_SeniorCenter.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentResponseDto {
    private Long id;
    private String title;
    private String description;
    private String message;
}

