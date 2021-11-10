package appointments.command.application.validators;

import appointments.command.application.dtos.request.OpenAppointmentRequest;
import common.application.Notification;
import org.springframework.stereotype.Component;

@Component
public class OpenAppointmentValidator {

    public Notification validate(OpenAppointmentRequest openAppointmentRequest)
    {
        Notification notification = new Notification();
        String customerId = openAppointmentRequest.getCustomerId().trim();
        if (customerId.isEmpty()) {
            notification.addError("Customer Id is required");
        }
        String employeeId = openAppointmentRequest.getEmployeeId().trim();
        if (employeeId.isEmpty()) {
            notification.addError("Employee Id is required");
        }
        String date = openAppointmentRequest.getDate().trim();
        if (date.isEmpty()) {
            notification.addError("Date is required");
        }
        String description = openAppointmentRequest.getDescription().trim();
        if (description.isEmpty()) {
            notification.addError("Description is required");
        }
        String amount = openAppointmentRequest.getAmount().trim();
        if (amount.isEmpty()) {
            notification.addError("Amount is required");
        }
        String payMethod = openAppointmentRequest.getPayMethodId().trim();
        if (payMethod.isEmpty()) {
            notification.addError("Payment Method Id is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }

        return notification;
    }
}
