package appointments.query.projections;

import appointments.contracts.events.AppointmentEdited;
import appointments.contracts.events.AppointmentRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Optional;
@@Component
public class AppointmentViewProjection {
    private final AppointmentViewRepository appointmentViewRepository;

    public AppointmentViewProjection(AppointmentViewRepository appointmentViewRepository) {
        this.appointmentViewRepository = appointmentViewRepository;
    }

    @EventHandler
    public void on(AppointmentRegistered event, @Timestamp Instant timestamp) {
        AppointmentView appointmentView = new AppointmentView(event.getAppointmentId(), event.getCustomerId(), event.getEmployeeId(), event.getDate(), event.getDescription(), event.getAmount(), event.getPayMethodId(), event.getOccurredOn());
        appointmentViewRepository.save(appointmentView);
    }

    @EventHandler
    public void on(AppointmentEdited event, @Timestamp Instant timestamp) {
        Optional<AppointmentView> appointmentViewOptional = AppointmentViewRepository.getByAppointmentId(event.getAppointmentId());
        if (appointmentViewOptional.isPresent()) {
            AppointmentView appointmentView = appointmentViewOptional.get();
            appointmentView.setCustomerId(event.getCustomerId());
            appointmentView.setEmployeeId(event.getEmployeeId());
            appointmentView.setDate(event.getDate());
            appointmentView.setDescription(event.getDescription());
            appointmentView.setAmount(event.getAmount());
            appointmentView.setPayMethodId(event.getPayMethodId());
            appointmentView.setUpdatedAt(event.getOccurredOn());
            appointmentViewRepository.save(appointmentView);
        }
    }
}
}
