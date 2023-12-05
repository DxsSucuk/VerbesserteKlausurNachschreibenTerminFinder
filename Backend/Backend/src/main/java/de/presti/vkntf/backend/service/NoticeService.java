package de.presti.vkntf.backend.service;

import de.presti.vkntf.backend.repository.exam.MissedExam;
import de.presti.vkntf.backend.repository.exam.MissedExamRepository;
import de.presti.vkntf.backend.repository.notice.Notice;
import de.presti.vkntf.backend.repository.notice.NoticeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("noticeService")
public class NoticeService {

    MissedExamRepository missedExamRepository;
    NoticeRepository noticeRepository;

    public NoticeService(SessionService sessionService, MissedExamRepository missedExamRepository, NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Mono<List<Notice>> getNoticesByExam(long examId) {
        return noticeRepository.getNoticesByExamId(examId).collectList();
    }

    public Mono<Notice> getNoticeByExamAndStudent(long examId, String studentId) {
        return noticeRepository.getNoticeByExamIdAndStudentId(examId, studentId);
    }
}
