package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.SessionResponse;
import de.presti.vkntf.backend.repository.session.Session;
import de.presti.vkntf.backend.repository.session.SessionRepository;
import de.presti.vkntf.backend.repository.student.StudentRepository;
import de.presti.vkntf.backend.repository.teacher.TeacherRepository;
import de.presti.vkntf.backend.utility.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service("userManagerService")
public class UserManagerService {

    SessionService sessionService;
    PasswordEncoder passwordEncoder;
    SessionRepository sessionRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;

    @Autowired
    public UserManagerService(SessionRepository sessionRepository,
                              TeacherRepository teacherRepository,
                              StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder,
                              SessionService sessionService) {
        this.passwordEncoder = passwordEncoder;
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Mono<GenericObjectResponse<SessionResponse>> login(String username, String password) {
        if (username == null || password == null) {
            return Mono.just(new GenericObjectResponse<>(false, null, "Missing Parameters"));
        }

        return studentRepository.getStudentsById(username).flatMap(user -> {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return sessionRepository.save(new Session(RandomUtils.getRandomBase64String(128), user))
                        .flatMap(session -> Mono.just(new GenericObjectResponse<>(true, new SessionResponse(session, user), "User login successful")));
            } else {
                return Mono.just(new GenericObjectResponse<SessionResponse>(false, null, "Incorrect password"));
            }
        }).switchIfEmpty(teacherRepository.getTeacherById(username).flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return sessionRepository.save(new Session(RandomUtils.getRandomBase64String(128), user))
                                .flatMap(session -> Mono.just(new GenericObjectResponse<>(true, new SessionResponse(session, user),"User login successful")));
                    } else {
                        return Mono.just(new GenericObjectResponse<SessionResponse>(false, null, "Incorrect password"));
                    }
                }).switchIfEmpty(Mono.just(new GenericObjectResponse<SessionResponse>(false, null, "User not found")))
        ).onErrorReturn(new GenericObjectResponse<>(false, null, "Failed"));
    }

    public Mono<GenericResponse> logout(String sessionToken) {
        return sessionService.logout(sessionToken);
    }

    public Mono<GenericObjectResponse<Session>> check(String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(x -> {
            if (x.getT1()) {
                return Mono.just(new GenericObjectResponse<Session>(true, x.getT2(), "Session valid"));
            } else {
                return Mono.just(new GenericObjectResponse<Session>(false, null,"Session invalid"));
            }
        });
    }
}