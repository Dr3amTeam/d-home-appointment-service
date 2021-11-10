package appointments.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentHistoryViewRepository extends JpaRepository<AppointmentHistoryView, String> {
    @Query(value = "SELECT * FROM customer_history_view WHERE appointment_history_id = (SELECT MAX(appointment_history_id) FROM appointment_history_view WHERE appointment_id = :appointmentId)", nativeQuery = true)
    Optional<AppointmentHistoryView> getLastByAppointmentId(String appointmentId);

    @Query(value = "SELECT * FROM appointment_history_view WHERE appointment_id = :appointmentId ORDER BY created_at", nativeQuery = true)
    List<AppointmentHistoryView> getHistoryByAppointmentId(String appointmentId);
}