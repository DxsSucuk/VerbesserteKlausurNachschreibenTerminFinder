package de.presti.vkntf.backend.controller.notice;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.request.ExamCreateRequest;
import de.presti.vkntf.backend.api.request.GenericValueRequest;
import de.presti.vkntf.backend.repository.exam.MissedExam;
import de.presti.vkntf.backend.repository.notice.Notice;
import de.presti.vkntf.backend.service.ExamService;
import de.presti.vkntf.backend.service.NoticeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/notice", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {

    NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @RequestMapping(value = "/create")
    public Mono<GenericObjectResponse<String>> createNoticeEntry(@RequestHeader(name = "Authorization") String sessionToken, @RequestBody GenericValueRequest request) {
        return noticeService.createNotice(sessionToken, request);
    }
}
