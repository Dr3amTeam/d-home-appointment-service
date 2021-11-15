package appointments.command.application.handlers;

import appointments.command.domain.Appointment;
import appointments.contracts.events.AppointmentEdited;
import appointments.contracts.events.AppointmentRegistered;
import appointments.query.projections.AppointmentView;
import appointments.query.projections.AppointmentViewRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppointmentEventHandler {
    private final AppointmentViewRepository appointmentViewRepository;

    public AppointmentEventHandler(AppointmentViewRepository appointmentViewRepository) {
        this.appointmentViewRepository = appointmentViewRepository;
    }
    @EventHandler
    public void on(AppointmentRegistered appointmentRegistered) {
        AppointmentView appointmentView = new AppointmentView(
                appointmentRegistered.getAppointmentId(),
                appointmentRegistered.getCustomerId(),
                appointmentRegistered.getEmployeeId(),
                appointmentRegistered.getDate(),
                appointmentRegistered.getDescription(),
                appointmentRegistered.getAmount(),
                appointmentRegistered.getPayMethodId(),
                appointmentRegistered.getStatus()
        );
        appointmentViewRepository.save(appointmentView);
    }

    @EventHandler
    public void on(AppointmentEdited appointmentEdited) {
        Optional<AppointmentView> optionalAppointmentView = appointmentViewRepository.findById(appointmentEdited.getAppointmentId());
        if (optionalAppointmentView.isPresent()) {
            AppointmentView appointmentView = optionalAppointmentView.get();
            appointmentViewRepository.save(appointmentView);
        }
    }
}
