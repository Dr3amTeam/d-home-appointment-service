package appointments.query.projections;

import appointments.contracts.events.AppointmentEdited;
import appointments.contracts.events.AppointmentRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppointmentHistoryViewProjection {
    private final AppointmentHistoryViewRepository appointmentHistoryViewRepository;

    public AppointmentHistoryViewProjection(AppointmentHistoryViewRepository appointmentHistoryViewRepository) {
        this.appointmentHistoryViewRepository = appointmentHistoryViewRepository;
    }

    @EventHandler
    public void on(AppointmentRegistered event) {
        AppointmentHistoryView appointmentHistoryView = new AppointmentHistoryView(event.getAppointmentId(), event.getCustomerId(), event.getEmployeeId(), event.getDate(), event.getDescription(), event.getAmount(), event.getPayMethodId(), event.getStatus());
        appointmentHistoryViewRepository.save(appointmentHistoryView);
    }

    @EventHandler
    public void on(AppointmentEdited event) {
        Optional<AppointmentHistoryView> appointmentHistoryViewOptional = appointmentHistoryViewRepository.getLastByAppointmentId(event.getAppointmentId().toString());
        if (appointmentHistoryViewOptional.isPresent()) {
            AppointmentHistoryView appointmentHistoryView = appointmentHistoryViewOptional.get();
            appointmentHistoryView = new AppointmentHistoryView(appointmentHistoryView);
            appointmentHistoryView.setCustomerId(event.getCustomerId());
            appointmentHistoryView.setEmployeeId(event.getEmployeeId());
            appointmentHistoryView.setDate(event.getDate());
            appointmentHistoryView.setDescription(event.getDescription());
            appointmentHistoryView.setAmount(event.getAmount());
            appointmentHistoryView.setPayMethodId(event.getPayMethodId());
            appointmentHistoryView.setStatus(event.getStatus());
            appointmentHistoryViewRepository.save(appointmentHistoryView);
        }
    }
}