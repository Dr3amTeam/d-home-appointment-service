package appointments.command.application.handlers;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppointmentEventHandler {
    @EventHandler
    public void on(CustomerRegistered event) {
        customerDniRepository.save(new CustomerDni(event.getDni(), event.getCustomerId()));
    }

    @EventHandler
    public void on(CustomerEdited event) {
        Optional<CustomerDni> CustomerDniOptional = customerDniRepository.getDniByCustomerId(event.getCustomerId());
        CustomerDniOptional.ifPresent(customerDniRepository::delete);
        customerDniRepository.save(new CustomerDni(event.getDni(), event.getCustomerId()));
    }
}
