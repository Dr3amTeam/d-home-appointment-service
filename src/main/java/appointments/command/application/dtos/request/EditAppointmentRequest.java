package appointments.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;

public class EditAppointmentRequest {
    private @Setter @Getter String appointmentId;
    private @Getter String customerId;
    private @Getter String employeeId;
    private @Getter String date;
    private @Getter String description;
    private @Getter String amount;
    private @Getter String payMethodId;
    private @Getter String status;
}
