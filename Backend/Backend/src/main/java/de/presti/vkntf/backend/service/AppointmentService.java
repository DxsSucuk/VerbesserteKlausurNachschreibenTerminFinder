package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.request.AppointmentCreateRequest;
import de.presti.vkntf.backend.repository.appointment.Appointment;
import de.presti.vkntf.backend.repository.appointment.AppointmentRepository;
import de.presti.vkntf.backend.repository.student.StudentRepository;
import de.presti.vkntf.backend.repository.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("appointmentService")
public class AppointmentService {

    SessionService sessionService;
    AppointmentRepository appointmentRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    ClassroomService classroomService;

    @Autowired
    public AppointmentService(TeacherRepository teacherRepository,
                              StudentRepository studentRepository,
                              SessionService sessionService,
                              AppointmentRepository appointmentRepository,
                              ClassroomService classroomService) {
        this.sessionService = sessionService;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.appointmentRepository = appointmentRepository;
        this.classroomService = classroomService;
    }

    public Mono<List<Appointment>> getPendingAppointments(String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1() && !session.getT2().getTeacher().isBlank()) {
                if (session.getT2().getTeacher().isBlank()) {
                    return appointmentRepository.getAppointmentsByStudentIdAndAcceptedByTeacherFalseAndAcceptedByStudentTrue(session.getT2().getStudent())
                            .collectList();
                } else {
                    return appointmentRepository.getAppointmentsByTeacherIdAndAcceptedByTeacherTrueAndAcceptedByStudentFalse(session.getT2().getTeacher())
                            .collectList();
                }
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }

    public Mono<List<Appointment>> getProposedAppointments(String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1() && !session.getT2().getTeacher().isBlank()) {
                if (session.getT2().getTeacher().isBlank()) {
                    return appointmentRepository.getAppointmentsByStudentIdAndAcceptedByTeacherTrueAndAcceptedByStudentFalse(session.getT2().getStudent())
                            .collectList();
                } else {
                    return appointmentRepository.getAppointmentsByTeacherIdAndAcceptedByTeacherFalseAndAcceptedByStudentTrue(session.getT2().getTeacher())
                            .collectList();
                }
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }

    public Mono<List<Appointment>> getAgreedAppointments(String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1() && !session.getT2().getTeacher().isBlank()) {
                if (session.getT2().getTeacher().isBlank()) {
                    return appointmentRepository.getAppointmentsByStudentIdAndAcceptedByTeacherTrueAndAcceptedByStudentTrue(session.getT2().getStudent()).collectList();
                } else {
                    return appointmentRepository.getAppointmentsByTeacherIdAndAcceptedByTeacherTrueAndAcceptedByStudentTrue(session.getT2().getTeacher()).collectList();
                }
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }

    public Mono<GenericResponse> createAppointment(String sessionToken, AppointmentCreateRequest request) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
           if (session.getT1()) {
               if (!session.getT2().getTeacher().isBlank()) {
                   Appointment appointment = new Appointment(session.getT2().getTeacher(), "", request.classromId(), request.date());
                   return Flux.fromStream(request.students().stream()).flatMap(x -> {
                       appointment.setStudentId(x);
                       return appointmentRepository.save(appointment);
                   }).hasElements().flatMap(x -> {
                       if (x) {
                           return Mono.just(new GenericResponse(true, "Appointment created"));
                       }

                       return Mono.just(new GenericResponse(false, "Appointment not created"));
                   }).switchIfEmpty(Mono.just(new GenericResponse(false, "Appointment not created")));
               } else {
                     Appointment appointment = new Appointment("", session.getT2().getStudent(), request.classromId(), request.date());
                     return classroomService.getClassroom(request.classromId()).flatMap(x -> {
                         appointment.setTeacherId(x.getClassTeacher());
                         return Mono.just(appointment);
                     }).flatMap(x -> appointmentRepository.save(x)).flatMap(x -> {
                         if (x != null) {
                             return Mono.just(new GenericResponse(true, "Appointment created"));
                         }

                         return Mono.just(new GenericResponse(false, "Appointment not created"));
                     }).switchIfEmpty(Mono.just(new GenericResponse(false, "Appointment not created")));
               }
           }

           return Mono.just(new GenericResponse(false, "Appointment not created"));
        }).switchIfEmpty(Mono.just(new GenericResponse(false, "Appointment not created")));
    }
}
