package de.presti.vkntf.backend.repository.classroom;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClassroomRepository extends R2dbcRepository<Classroom, String> {

    Mono<Classroom> getClassroomByName(@Param("name") String name);
    Flux<Classroom> getClassroomsByAbteilung(@Param("abteilung") int abteilung);
    Flux<Classroom> getClassroomByClassTeacher(@Param("classTeacher") String classTeacher);
}
