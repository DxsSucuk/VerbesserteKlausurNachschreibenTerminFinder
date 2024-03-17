package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.request.GenericValueRequest;
import de.presti.vkntf.backend.repository.exam.MissedExam;
import de.presti.vkntf.backend.repository.exam.MissedExamRepository;
import de.presti.vkntf.backend.repository.notice.Notice;
import de.presti.vkntf.backend.repository.notice.NoticeRepository;
import de.presti.vkntf.backend.repository.student.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("noticeService")
public class NoticeService {

    final SessionService sessionService;
    final MissedExamRepository missedExamRepository;
    final NoticeRepository noticeRepository;

    public NoticeService(SessionService sessionService,
                         MissedExamRepository missedExamRepository,
                         NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
        this.sessionService = sessionService;
        this.missedExamRepository = missedExamRepository;
    }

    public Mono<GenericObjectResponse<String>> createNotice(String session, GenericValueRequest request) {
        return sessionService.checkSession(session).flatMap(x -> {
            if (x.getT1() && x.getT2().getTeacher().isBlank()) {
                return getNoticeByExamAndStudent(Long.parseLong(request.value()), x.getT2().getStudent()).flatMap(y -> {
                    if (y == null) {
                        return missedExamRepository.getMissedExamBy(Long.parseLong(request.value())).flatMap(z -> {
                            if (z != null) {
                                Notice notice = new Notice(z.getStudentId());
                                notice.setExamId(z.getId());
                                return noticeRepository.save(notice).map(a -> new GenericObjectResponse<>(true, String.valueOf(a.getId()), "Notice created"));
                            }

                            return Mono.just(new GenericObjectResponse<String>(false, null, "Missed exam not found"));
                        });
                    } else {
                        return Mono.just(new GenericObjectResponse<String>(false, null, "Notice already exists"));
                    }
                });
            }

            return Mono.just(new GenericObjectResponse<String>(false, null, "You are a teacher!"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<String>(false, null, "No session found")));
    }

    public Mono<List<Notice>> getNoticesByExam(long examId) {
        return noticeRepository.getNoticesByExamId(examId).collectList();
    }

    public Mono<Notice> getNoticeByExamAndStudent(long examId, String studentId) {
        return noticeRepository.getNoticeByExamIdAndStudentId(examId, studentId);
    }

    public Mono<Notice> getValidNoticeByExamAndStudent(long examId, String studentId) {
        return noticeRepository.getNoticeByExamIdAndStudentIdAndApprovedByTeacherIdTrue(examId, studentId);
    }
}
