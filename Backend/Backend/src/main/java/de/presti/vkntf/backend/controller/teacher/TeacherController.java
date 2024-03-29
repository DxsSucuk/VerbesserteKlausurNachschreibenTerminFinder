package de.presti.vkntf.backend.controller.teacher;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.repository.appointment.Appointment;
import de.presti.vkntf.backend.repository.classroom.Classroom;
import de.presti.vkntf.backend.service.TeacherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/teacher", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TeacherController {

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "/classrooms", consumes = MediaType.ALL_VALUE)
    public Mono<GenericObjectResponse<List<Classroom>>> getClasses(@RequestHeader(name = "Authorization") String sessionToken) {

        return teacherService.getClasses(sessionToken).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<Classroom>>(false, x, "No classrooms found"));
            }

            return Mono.just(new GenericObjectResponse<List<Classroom>>(true, x, "Classrooms found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<Classroom>>(false, Collections.emptyList(), "No classrooms found")));
    }
}
