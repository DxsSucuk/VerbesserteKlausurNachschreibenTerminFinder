package de.presti.vkntf.backend.repository.appointment;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AppointmentRepository extends R2dbcRepository<Appointment, Long> {

    Mono<Appointment> getAppointmentById(@Param("id") long id);
    Flux<Appointment> getAppointmentsByTeacherId(@Param("teacherId") String teacherId);
    Flux<Appointment> getAppointmentsByTeacherIdAndAcceptedByStudentTrue(@Param("teacherId") String teacherId);
    Flux<Appointment> getAppointmentsByTeacherIdAndAcceptedByTeacherTrue(@Param("teacherId") String teacherId);
    Flux<Appointment> getAppointmentsByTeacherIdAndAcceptedByTeacherTrueAndAcceptedByStudentTrue(@Param("teacherId") String teacherId);
    Flux<Appointment> getAppointmentsByTeacherIdAndAcceptedByTeacherTrueAndAcceptedByStudentFalse(@Param("teacherId") String teacherId);
    Flux<Appointment> getAppointmentsByTeacherIdAndAcceptedByTeacherFalseAndAcceptedByStudentTrue(@Param("teacherId") String teacherId);

    Flux<Appointment> getAppointmentsByStudentId(@Param("studentId") String studentId);
    Flux<Appointment> getAppointmentByStudentIdAndAcceptedByStudentTrue(@Param("studentId") String studentId);
    Flux<Appointment> getAppointmentsByStudentIdAndAcceptedByTeacherTrue(@Param("studentId") String studentId);
    Flux<Appointment> getAppointmentsByStudentIdAndAcceptedByTeacherTrueAndAcceptedByStudentTrue(@Param("studentId") String studentId);
    Flux<Appointment> getAppointmentsByStudentIdAndAcceptedByTeacherTrueAndAcceptedByStudentFalse(@Param("studentId") String studentId);
    Flux<Appointment> getAppointmentsByStudentIdAndAcceptedByTeacherFalseAndAcceptedByStudentTrue(@Param("studentId") String studentId);
}
