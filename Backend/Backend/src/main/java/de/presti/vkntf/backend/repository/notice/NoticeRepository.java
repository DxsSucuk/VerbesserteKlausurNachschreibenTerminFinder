package de.presti.vkntf.backend.repository.notice;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface NoticeRepository extends R2dbcRepository<Notice, Long> {

    Mono<Notice> getNoticeById(@Param("id") long id);
    Flux<Notice> getNoticesByExamId(@Param("examId") long examId);
    Flux<Notice> getNoticesByStudentId(@Param("studentId") String studentId);
    Flux<Notice> getNoticesByApprovedByTeacherId(@Param("approvedByTeacherId") String approvedByTeacherId);
    Flux<Notice> getNoticesByStudentIdAndApprovedByTeacherId(@Param("studentId") String studentId, @Param("approvedByTeacherId") String approvedByTeacherId);
    Mono<Notice> getNoticeByExamIdAndStudentId(@Param("examId") long examId, @Param("studentId") String studentId);
}
