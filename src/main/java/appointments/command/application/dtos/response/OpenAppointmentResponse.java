package appointments.command.application.dtos.response;

import lombok.Value;

@Value
public class OpenAppointmentResponse {
    private String appointmentId;
    private String customerId;
    private String employeeId;
    private String date;
    private String description;
    private String amount;
    private String payMethodId;
    private String status;
}
