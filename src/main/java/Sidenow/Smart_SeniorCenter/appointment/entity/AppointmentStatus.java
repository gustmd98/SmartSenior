package Sidenow.Smart_SeniorCenter.appointment.entity;

public enum AppointmentStatus {
    ACTIVE,     // 정상
    CONFIRMED,  // 확정됨 (최대 인원 도달 시)
    CANCELED,   // 취소됨
    FAILED      // 파투됨
}
