package appointments.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AppointmentHistoryView {
    @Id @GeneratedValue @Getter @Setter
    private Long appointmentHistoryId;
    @Column(length=36) @Getter @Setter
    private String appointmentId;
    @Column(length=50) @Getter @Setter
    private String customerId;
    @Column(length=50) @Getter @Setter
    private String employeeId;
    @Column(length=8) @Getter @Setter
    private String date;
    @Column(length=180) @Getter @Setter
    private String description;
    @Column(length=20) @Getter @Setter
    private String amount;
    @Column(length=60) @Getter @Setter
    private String payMethodId;
    @Column(nullable = true) @Getter @Setter
    private String status;

    public AppointmentHistoryView() {
    }

    public AppointmentHistoryView(String appointmentId, String customerId, String employeeId, String date, String description, String amount, String payMethodId, String status) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.payMethodId = payMethodId;
        this.status = status;
    }
    public AppointmentHistoryView(AppointmentHistoryView appointmentHistoryView) {
        this.appointmentId = appointmentHistoryView.getAppointmentId();
        this.customerId = appointmentHistoryView.getCustomerId();
        this.employeeId = appointmentHistoryView.getEmployeeId();
        this.date = appointmentHistoryView.getDate();
        this.description = appointmentHistoryView.getDescription();
        this.amount = appointmentHistoryView.getAmount();
        this.payMethodId = appointmentHistoryView.getPayMethodId();
        this.status = appointmentHistoryView.getStatus();
    }

}
