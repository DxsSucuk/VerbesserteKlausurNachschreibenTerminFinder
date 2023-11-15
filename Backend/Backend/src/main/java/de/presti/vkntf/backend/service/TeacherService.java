package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.repository.classroom.Classroom;
import de.presti.vkntf.backend.repository.classroom.ClassroomRepository;
import de.presti.vkntf.backend.repository.student.StudentRepository;
import de.presti.vkntf.backend.repository.teacher.TeacherClassroomMap;
import de.presti.vkntf.backend.repository.teacher.TeacherClassroomMapRepository;
import de.presti.vkntf.backend.repository.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("teacherService")
public class TeacherService {

    SessionService sessionService;
    ClassroomRepository classroomRepository;
    TeacherRepository teacherRepository;
    TeacherClassroomMapRepository teacherClassroomMapRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository,
                            SessionService sessionService,
                            ClassroomRepository classroomRepository,
                            TeacherClassroomMapRepository teacherClassroomMapRepository) {
        this.sessionService = sessionService;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
        this.teacherClassroomMapRepository = teacherClassroomMapRepository;
    }

    public Mono<List<Classroom>> getClasses(String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1() && !session.getT2().getTeacher().isBlank()) {
                return teacherClassroomMapRepository.getTeacherClassroomMapByTeacherId(session.getT2().getTeacher())
                        .flatMap(teacherClassroomMap -> classroomRepository.getClassroomById(teacherClassroomMap.getClassroomId()))
                        .mergeWith(classroomRepository.getClassroomByClassTeacher(session.getT2().getTeacher())).collectList();
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }

}
