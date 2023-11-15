package de.presti.vkntf.backend.repository.teacher;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TeacherClassroomMapRepository  extends R2dbcRepository<TeacherClassroomMap, Long> {

    Flux<TeacherClassroomMap> getTeacherClassroomMapByTeacherId(String teacherId);

    Flux<TeacherClassroomMap> getTeacherClassroomMapByClassroomId(String classroomId);
}
