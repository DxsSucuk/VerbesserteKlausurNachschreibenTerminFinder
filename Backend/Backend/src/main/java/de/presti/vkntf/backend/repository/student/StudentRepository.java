package de.presti.vkntf.backend.repository.student;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StudentRepository extends R2dbcRepository<Student, String> {

    Mono<Student> getStudentsById(@Param("id")String id);
    Flux<Student> getStudentsByClassroomId(@Param("classroomId") String classroomId);

}
