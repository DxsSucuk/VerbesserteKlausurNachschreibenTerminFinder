package de.presti.vkntf.backend.repository.teacher;

import de.presti.vkntf.backend.repository.student.Student;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TeacherRepository extends R2dbcRepository<Teacher, String> {

    Mono<Teacher> getTeacherById(@Param("id") String id);

    Flux<Teacher> getTeachersByAbteilung(@Param("abteilung") int abteilung);

}
