package appointments.command.application.validators;

import appointments.command.application.dtos.request.EditAppointmentRequest;
import common.application.Notification;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import appointments.command.domain.Appointment;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

@Component
public class EditAppointmentValidator {
    private final Repository<Appointment> appointmentRepository;

    public EditAppointmentValidator(Repository<Appointment> appointmentRepository){
        this.appointmentRepository=appointmentRepository;
    }


    public Notification validate(EditAppointmentRequest editAppointmentRequest)
    {
        Notification notification = new Notification();
        String appointmentId = editAppointmentRequest.getAppointmentId().trim();
        if (appointmentId.isEmpty()) {
            notification.addError("Appointment id is required");
        }
        loadAppointmentAggregate(appointmentId);
        String customerId = editAppointmentRequest.getCustomerId().trim();
        if (customerId.isEmpty()) {
            notification.addError("Customer id is required");
        }
        String employeeId = editAppointmentRequest.getEmployeeId().trim();
        if (employeeId.isEmpty()) {
            notification.addError("Employee id is required");
        }
        String date = editAppointmentRequest.getDate().trim();
        if (date.isEmpty()) {
            notification.addError("Date is required");
        }
        String amount = editAppointmentRequest.getAmount().trim();
        if (amount.isEmpty()) {
            notification.addError("Amount is required");
        }
        String payMethod = editAppointmentRequest.getPayMethodId().trim();
        if (payMethod.isEmpty()) {
            notification.addError("Payment Method ID is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        return notification;
    }

    private void loadAppointmentAggregate(String appointmentId) {
        UnitOfWork unitOfWork = null;
        try {
            unitOfWork = DefaultUnitOfWork.startAndGet(null);
            appointmentRepository.load(appointmentId);
            unitOfWork.commit();
        } catch (AggregateNotFoundException ex) {
            unitOfWork.commit();
            throw ex;
        } catch(Exception ex) {
            if (unitOfWork != null) unitOfWork.rollback();
        }
    }
}
