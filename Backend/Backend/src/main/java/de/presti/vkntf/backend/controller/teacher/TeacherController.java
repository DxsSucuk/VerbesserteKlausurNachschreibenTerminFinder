package de.presti.vkntf.backend.controller.teacher;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.repository.classroom.Classroom;
import de.presti.vkntf.backend.service.TeacherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/teacher", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TeacherController {

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "/classrooms")
    public Mono<GenericObjectResponse<List<Classroom>>> getClasses(@RequestHeader(name = "Authorization") String sessionToke) {

        return teacherService.getClasses(sessionToke).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<Classroom>>(false, x, "No classrooms found"));
            }

            return Mono.just(new GenericObjectResponse<List<Classroom>>(true, x, "Classrooms found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<Classroom>>(false, List.of(), "No classrooms found")));
    }

}
