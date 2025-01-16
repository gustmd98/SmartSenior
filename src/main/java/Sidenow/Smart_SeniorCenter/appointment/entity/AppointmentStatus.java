package Sidenow.Smart_SeniorCenter.appointment.entity;

public enum AppointmentStatus {
    ACTIVE,     // 정상: 약속 모집 중 상태
    CONFIRMED,  // 확정됨: 최대 인원에 도달하여 약속이 확정됨
    CANCELED,   // 취소됨: 참여자가 부족하거나 사용자가 직접 취소한 경우
    FAILED      // 파투됨: 약속 시간이 지났으나 약속이 성사되지 않은 경우
}

