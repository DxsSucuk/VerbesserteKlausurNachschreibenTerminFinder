package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.api.request.ExamCreateRequest;
import de.presti.vkntf.backend.repository.exam.MissedExam;
import de.presti.vkntf.backend.repository.exam.MissedExamRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("examService")
public class ExamService {

    SessionService sessionService;
    MissedExamRepository missedExamRepository;
    NoticeService noticeService;

    public ExamService(SessionService sessionService, MissedExamRepository missedExamRepository, NoticeService noticeService) {
        this.sessionService = sessionService;
        this.missedExamRepository = missedExamRepository;
        this.noticeService = noticeService;
    }

    public Mono<Boolean> createMissedExam(String sessionToken, ExamCreateRequest request) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1() && !session.getT2().getTeacher().isBlank()) {
                MissedExam missedExam = new MissedExam("", session.getT2().getTeacher(), request.classromId(), request.missedAt());
                return Flux.fromStream(request.students().stream()).flatMap(x -> {
                    missedExam.setStudentId(x);
                    return missedExamRepository.save(missedExam);
                }).hasElements();
            }

            return Mono.just(false);
        }).switchIfEmpty(Mono.just(false));
    }

    public Mono<List<MissedExam>> getMissedExams(String sessionToken) {
        return sessionService.checkSession(sessionToken).flatMap(session -> {
            if (session.getT1()) {
                return missedExamRepository.getMissedExamsByStudentId(session.getT2().getStudent())
                        .flatMap(x -> noticeService.getValidNoticeByExamAndStudent(x.getId(), session.getT2().getStudent()).map(y -> {
                    x.setHasNotice(y != null);
                    return x;
                })).collectList();
            }

            return Mono.empty();
        }).switchIfEmpty(Mono.empty());
    }
}
