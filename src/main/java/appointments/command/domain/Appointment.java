package appointments.command.domain;

import appointments.contracts.commands.EditAppointment;
import appointments.contracts.commands.OpenAppointment;
import appointments.contracts.events.AppointmentEdited;
import appointments.contracts.events.AppointmentRegistered;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Appointment {
    @AggregateIdentifier
    private String appointmentId;
    private String customerId;
    private String employeeId;
    private String date;
    private String description;
    private String amount;
    private String payMethodId;
    private AppointmentStatus status;

    public Appointment() {
    }

    @CommandHandler
    public Appointment(OpenAppointment command) {
        Instant now = Instant.now();
        apply(
                new AppointmentRegistered(
                        command.getAppointmentId(),
                        command.getCustomerId(),
                        command.getEmployeeId(),
                        command.getDate(),
                        command.getAmount(),
                        command.getPayMethodId(),
                        command.getStatus(),
                        now
                )
        );
    }

    @CommandHandler
    public void handle(EditAppointment command) {
        Instant now = Instant.now();
        apply(
                new AppointmentEdited(
                        command.getAppointmentId(),
                        command.getCustomerId(),
                        command.getEmployeeId(),
                        command.getDate(),
                        command.getAmount(),
                        command.getPayMethodId(),
                        command.getStatus(),
                        now
                )
        );
    }

    @EventSourcingHandler
    protected void on(AppointmentRegistered event) {
        appointmentId = event.getAppointmentId();
        customerId = event.getCustomerId();
        employeeId = event.getEmployeeId();
        date = event.getDate();
        description = event.getDescription();
        amount = event.getAmount();
        payMethodId=event.getPayMethodId();
        status = AppointmentStatus.CREATED;
    }

    @EventSourcingHandler
    protected void on(AppointmentEdited event) {
        appointmentId= event.getAppointmentId();
        customerId = event.getCustomerId();
        employeeId = event.getEmployeeId();
        date = event.getDate();
        description = event.getDescription();
        amount = event.getAmount();
        payMethodId=event.getPayMethodId();
        status = AppointmentStatus.EDITED;
    }
}
