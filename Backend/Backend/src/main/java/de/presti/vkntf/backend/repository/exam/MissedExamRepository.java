package de.presti.vkntf.backend.repository.exam;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MissedExamRepository extends R2dbcRepository<MissedExam, Long> {

    Mono<MissedExam> getMissedExamBy(@Param("id") long id);
    Flux<MissedExam> getMissedExamsByStudentId(@Param("studentId") String studentId);
    Flux<MissedExam> getMissedExamsByTeacherId(@Param("teacherId") String teacherId);
    Flux<MissedExam> getMissedExamsByClassromId(@Param("classromId") String classromId);
    Flux<MissedExam> getMissedExamsByTeacherIdAndStudentId(@Param("teacherId") String teacherId, @Param("studentId") String studentId);
}
