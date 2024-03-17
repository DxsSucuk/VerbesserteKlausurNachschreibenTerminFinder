package de.presti.vkntf.backend.controller.appointment;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.request.AppointmentCreateRequest;
import de.presti.vkntf.backend.repository.appointment.Appointment;
import de.presti.vkntf.backend.service.AppointmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

    AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @RequestMapping(value = "/create")
    public Mono<GenericResponse> createAppointment(@RequestHeader(name = "Authorization") String sessionToken, AppointmentCreateRequest request) {
        return appointmentService.createAppointment(sessionToken, request);
    }

    @RequestMapping(value = "/pending", consumes = MediaType.ALL_VALUE)
    public Mono<GenericObjectResponse<List<Appointment>>> getPendingAppointments(@RequestHeader(name = "Authorization") String sessionToken) {
        return appointmentService.getPendingAppointments(sessionToken).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<Appointment>>(false, x, "No appointments found"));
            }

            return Mono.just(new GenericObjectResponse<List<Appointment>>(true, x, "Appointments found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<Appointment>>(false, Collections.emptyList(), "No appointments found")));
    }

    @RequestMapping(value = "/agreed", consumes = MediaType.ALL_VALUE)
    public Mono<GenericObjectResponse<List<Appointment>>> getAgreedAppointments(@RequestHeader(name = "Authorization") String sessionToken) {
        return appointmentService.getAgreedAppointments(sessionToken).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<Appointment>>(false, x, "No appointments found"));
            }

            return Mono.just(new GenericObjectResponse<List<Appointment>>(true, x, "Appointments found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<Appointment>>(false, Collections.emptyList(), "No appointments found")));
    }

    @RequestMapping(value = "/proposed", consumes = MediaType.ALL_VALUE)
    public Mono<GenericObjectResponse<List<Appointment>>> getProposedAppointment(@RequestHeader(name = "Authorization") String sessionToken) {
        return appointmentService.getProposedAppointments(sessionToken).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<Appointment>>(false, x, "No appointments found"));
            }

            return Mono.just(new GenericObjectResponse<List<Appointment>>(true, x, "Appointments found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<Appointment>>(false, Collections.emptyList(), "No appointments found")));
    }
}
