package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.repository.classroom.Classroom;
import de.presti.vkntf.backend.repository.classroom.ClassroomRepository;
import de.presti.vkntf.backend.repository.student.Student;
import de.presti.vkntf.backend.repository.student.StudentRepository;
import de.presti.vkntf.backend.repository.teacher.Teacher;
import de.presti.vkntf.backend.repository.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("classroomService")
public class ClassroomService {

    SessionService sessionService;
    ClassroomRepository classroomRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;

    @Autowired
    public ClassroomService(TeacherRepository teacherRepository,
                            StudentRepository studentRepository,
                            SessionService sessionService,
                            ClassroomRepository classroomRepository) {
        this.sessionService = sessionService;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
    }

    public Mono<Classroom> getClassroom(String classroomId) {
        return classroomRepository.getClassroomById(classroomId);
    }

    public Mono<List<Student>> getStudents(String classroomId, String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1() && !session.getT2().getTeacher().isBlank()) {
                return studentRepository.getStudentsByClassroomId(classroomId).collectList();
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }

    public Mono<Teacher> getClassTeacher(String classroomId, String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1()) {
                return classroomRepository.getClassroomById(classroomId)
                        .flatMap(y -> teacherRepository.getTeacherById(y.getClassTeacher()))
                        .switchIfEmpty(Mono.empty());
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }

}
