package appointments.command.application.dtos.request;

import lombok.Value;

@Value
public class OpenAppointmentRequest {
    private String appointmentId;
    private String customerId;
    private String employeeId;
    private String date;
    private String description;
    private String amount;
    private String payMethodId;
    private String Status;
}
