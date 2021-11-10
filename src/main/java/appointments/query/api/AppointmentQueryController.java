package appointments.query.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import appointments.query.projections.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
@Api(tags = "Appointments")
public class AppointmentQueryController {
    private final AppointmentViewRepository appointmentViewRepository;
    private final AppointmentHistoryViewRepository appointmentHistoryViewRepository;

    public AppointmentQueryController(AppointmentViewRepository appointmentViewRepository, AppointmentHistoryViewRepository appointmentHistoryViewRepository) {
        this.appointmentViewRepository = appointmentViewRepository;
        this.appointmentHistoryViewRepository = appointmentHistoryViewRepository;
    }

    @GetMapping("")
    @ApiOperation(value = "Get all appointments", response = List.class)
    public ResponseEntity<List<AppointmentView>> getAll() {
        try {
            return new ResponseEntity<List<AppointmentView>>(appointmentViewRepository.findAll(), HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get appointment by id", response = AppointmentView.class)
    public ResponseEntity<AppointmentView> getById(@PathVariable("id") String id) {
        try {
            Optional<AppointmentView> appointmentViewOptional = appointmentViewRepository.findById(id);
            if (appointmentViewOptional.isPresent()) {
                return new ResponseEntity<AppointmentView>(appointmentViewOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{id}")
    @ApiOperation(value = "Get appointment history", response = List.class)
    public ResponseEntity<List<AppointmentHistoryView>> getHistoryById(@PathVariable("id") String id) {
        try {
            List<AppointmentHistoryView> appointments = appointmentHistoryViewRepository.getHistoryByAppointmentId(id);
            return new ResponseEntity<List<AppointmentHistoryView>>(appointments, HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
