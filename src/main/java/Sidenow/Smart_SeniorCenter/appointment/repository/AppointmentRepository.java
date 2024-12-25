package Sidenow.Smart_SeniorCenter.appointment.repository;

import Sidenow.Smart_SeniorCenter.appointment.entity.Appointment;
import Sidenow.Smart_SeniorCenter.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUser(User user);

}

