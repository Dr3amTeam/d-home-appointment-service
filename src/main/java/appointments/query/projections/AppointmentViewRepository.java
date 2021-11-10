package appointments.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppointmentViewRepository extends JpaRepository<AppointmentView, String> {
    Optional<AppointmentView> getByAppointmentId(String appointmentId);
}