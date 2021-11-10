package appointments.command.application.services;

import appointments.command.application.dtos.request.EditAppointmentRequest;
import appointments.command.application.dtos.request.OpenAppointmentRequest;
import appointments.command.application.dtos.response.EditAppointmentResponse;
import appointments.command.application.dtos.response.OpenAppointmentResponse;
import appointments.command.application.validators.EditAppointmentValidator;
import appointments.command.application.validators.OpenAppointmentValidator;
import appointments.contracts.commands.EditAppointment;
import appointments.contracts.commands.OpenAppointment;
import common.application.Notification;
import common.application.Result;
import common.application.ResultType;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Component
public class AppointmentApplicationService {
    private final OpenAppointmentValidator openAppointmentValidator;
    private final EditAppointmentValidator editAppointmentValidator;
    private final CommandGateway commandGateway;

    public AppointmentApplicationService(OpenAppointmentValidator openAppointmentValidator, EditAppointmentValidator editAppointmentValidator, CommandGateway commandGateway) {
        this.openAppointmentValidator = openAppointmentValidator;
        this.editAppointmentValidator = editAppointmentValidator;
        this.commandGateway = commandGateway;
    }

    public Result<OpenAppointmentResponse, Notification> register(OpenAppointmentRequest openAppointmentRequest) throws Exception {
        Notification notification = this.openAppointmentValidator.validate(openAppointmentRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        String appointmentId = UUID.randomUUID().toString();
        OpenAppointment openAppointment = new OpenAppointment(
                appointmentId,
                openAppointmentRequest.getCustomerId().trim(),
                openAppointmentRequest.getEmployeeId().trim(),
                openAppointmentRequest.getDate().trim(),
                openAppointmentRequest.getDescription().trim(),
                openAppointmentRequest.getAmount().trim(),
                openAppointmentRequest.getPayMethodId(),
                openAppointmentRequest.getStatus()
        );
        CompletableFuture<Object> future = commandGateway.send(openAppointment);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        OpenAppointmentResponse openAppointmentResponse = new OpenAppointmentResponse(
                openAppointment.getAppointmentId(),
                openAppointment.getCustomerId(),
                openAppointment.getEmployeeId(),
                openAppointment.getDate(),
                openAppointment.getDescription(),
                openAppointment.getAmount(),
                openAppointment.getPayMethodId(),
                openAppointment.getStatus()
        );
        return Result.success(openAppointmentResponse);
    }

    public Result<EditAppointmentResponse, Notification> edit(EditAppointmentRequest editAppointmentRequest) throws Exception {
        Notification notification = this.editAppointmentValidator.validate(editAppointmentRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditAppointment editAppointment = new EditAppointment(
                editAppointmentRequest.getAppointmentId().trim(),
                editAppointmentRequest.getCustomerId().trim(),
                editAppointmentRequest.getEmployeeId().trim(),
                editAppointmentRequest.getDate().trim(),
                editAppointmentRequest.getDescription().trim(),
                editAppointmentRequest.getAmount().trim(),
                editAppointmentRequest.getPayMethodId().trim(),
                editAppointmentRequest.getStatus().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(editAppointment);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditAppointmentResponse editAppointmentResponse = new EditAppointmentResponse(
                editAppointment.getAppointmentId(),
                editAppointment.getCustomerId(),
                editAppointment.getEmployeeId(),
                editAppointment.getDate(),
                editAppointment.getDescription(),
                editAppointment.getAmount(),
                editAppointment.getPayMethodId(),
                editAppointment.getStatus()
        );
        return Result.success(editAppointmentResponse);
    }
}
