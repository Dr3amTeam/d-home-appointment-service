package appointments.query.projections;

import appointments.contracts.events.AppointmentEdited;
import appointments.contracts.events.AppointmentRegistered;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
public class AppointmentViewProjection {
    private final AppointmentViewRepository appointmentViewRepository;

    public AppointmentViewProjection(AppointmentViewRepository appointmentViewRepository) {
        this.appointmentViewRepository = appointmentViewRepository;
    }

    @EventHandler
    public void on(AppointmentRegistered event) {
        AppointmentView appointmentView = new AppointmentView(event.getAppointmentId(), event.getCustomerId(), event.getEmployeeId(), event.getDate(), event.getDescription(), event.getAmount(), event.getPayMethodId(), event.getStatus());
        appointmentViewRepository.save(appointmentView);
    }

    @EventHandler
    public void on(AppointmentEdited event) {
        Optional<AppointmentView> appointmentViewOptional = AppointmentViewRepository.findById(event.getAppointmentId().toString());
        if (appointmentViewOptional.isPresent()) {
            AppointmentView appointmentView = appointmentViewOptional.get();
            appointmentView.setCustomerId(event.getCustomerId());
            appointmentView.setEmployeeId(event.getEmployeeId());
            appointmentView.setDate(event.getDate());
            appointmentView.setDescription(event.getDescription());
            appointmentView.setAmount(event.getAmount());
            appointmentView.setPayMethodId(event.getPayMethodId());
            appointmentView.setStatus(event.getStatus());
            appointmentViewRepository.save(appointmentView);
        }
    }
}

