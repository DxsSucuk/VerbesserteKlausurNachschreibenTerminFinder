package de.presti.vkntf.backend.controller.classroom;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.repository.student.Student;
import de.presti.vkntf.backend.service.ClassroomService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/classroom/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ClassroomController {

    ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }


    @RequestMapping(value = "/students")
    public Mono<GenericObjectResponse<List<Student>>> getStudents(@PathVariable("id") String classroomId, @RequestHeader(name = "Authorization") String sessionToken) {
        return classroomService.getStudents(classroomId, sessionToken).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<Student>>(false, x, "No students found"));
            }

            return Mono.just(new GenericObjectResponse<List<Student>>(true, x, "Students found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<Student>>(false, Collections.emptyList(),"No students found")));
    }
}
