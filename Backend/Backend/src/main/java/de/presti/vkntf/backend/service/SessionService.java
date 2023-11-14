package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.repository.session.Session;
import de.presti.vkntf.backend.repository.session.SessionRepository;
import de.presti.vkntf.backend.repository.student.StudentRepository;
import de.presti.vkntf.backend.repository.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service("sessionService")
public class SessionService {

    SessionRepository sessionRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Mono<Tuple2<Boolean, Session>> checkSession(String sessionToken) {
        return sessionRepository.getSessionBySessionToken(sessionToken).flatMap(session ->
        {
            if (session.getTeacher().isBlank()) {
                return studentRepository.getStudentsById(session.getStudent())
                        .flatMap(user -> Mono.just(true).zipWith(Mono.just(session))).switchIfEmpty(Mono.just(false).zipWith(Mono.empty()));
            }

            return teacherRepository.getTeacherById(session.getTeacher())
                    .flatMap(user -> Mono.just(true).zipWith(Mono.just(session))).switchIfEmpty(Mono.just(false).zipWith(Mono.empty()));
        }).switchIfEmpty(Mono.just(false).zipWith(Mono.empty()));
    }

    public Mono<GenericResponse> logout(String sessionToken) {
        return sessionRepository.getSessionBySessionToken(sessionToken).flatMap(session -> sessionRepository.delete(session)
                        .then(Mono.just(new GenericResponse(true, "Logout successful"))))
                .switchIfEmpty(Mono.just(new GenericResponse(false, "Session not found")));
    }
}