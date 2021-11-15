package appointments.command.api;

import appointments.command.application.dtos.request.EditAppointmentRequest;
import appointments.command.application.dtos.request.OpenAppointmentRequest;
import appointments.command.application.dtos.response.EditAppointmentResponse;
import appointments.command.application.dtos.response.OpenAppointmentResponse;
import appointments.command.application.services.AppointmentApplicationService;
import appointments.command.infra.CustomerDniRepository;
import appointments.common.api.ApiController;
import appointments.common.application.Notification;
import appointments.common.application.Result;
import io.swagger.annotations.Api;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/appointments")
@Api(tags = "Appointments")
public class AppointmentCommandController {
    private final AppointmentApplicationService appointmentApplicationService;
    private final CommandGateway commandGateway;
    private final CustomerDniRepository customerDniRepository;

    public AppointmentCommandController(AppointmentApplicationService appointmentApplicationService, CommandGateway commandGateway, CustomerDniRepository customerDniRepository) {
        this.appointmentApplicationService = appointmentApplicationService;
        this.commandGateway = commandGateway;
        this.customerDniRepository= customerDniRepository;
    }

    @PostMapping("")
    public ResponseEntity<Object> open(@Validated @RequestBody OpenAppointmentRequest openAppointmentRequest) {
        try {
            Result<OpenAppointmentResponse, Notification> result = appointmentApplicationService.register(openAppointmentRequest);
            if (result.isSuccess()) {
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch(Exception e) {
            return ApiController.serverError();
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Object> edit(@PathVariable("appointmentId") String accountId, @RequestBody EditAppointmentRequest editAppointmentRequest) {
        try {
            editAppointmentRequest.setAppointmentId(accountId);
            Result<EditAppointmentResponse, Notification> result = appointmentApplicationService.edit(editAppointmentRequest);
            if (result.isSuccess()) {
                return ApiController.ok(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (AggregateNotFoundException exception) {
            return ApiController.notFound();
        } catch(Exception e) {
            return ApiController.serverError();
        }
    }
}