package appointments.command.infra;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerDni {
    @Id
    @Column(length=8)
    public String dni;
    public String customerId;

    public CustomerDni() {
    }

    public CustomerDni(String dni, String customerId) {
        this.dni = dni;
        this.customerId = customerId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}