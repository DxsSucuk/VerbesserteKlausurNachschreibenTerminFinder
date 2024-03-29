package de.presti.vkntf.backend.controller.notice;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.request.ExamAndStudentRequest;
import de.presti.vkntf.backend.api.request.GenericValueRequest;
import de.presti.vkntf.backend.repository.notice.Notice;
import de.presti.vkntf.backend.service.NoticeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "*")
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

    @RequestMapping(value = "/exam", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GenericObjectResponse<Notice>> getNoticeByExamAndStudent(@RequestHeader(name = "Authorization") String sessionToken, @RequestBody ExamAndStudentRequest request) {
        return noticeService.getNoticeByExamAndStudent(sessionToken, request);
    }

    @RequestMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GenericObjectResponse<String>> getImageByNoticeId(@RequestHeader(name = "Authorization") String sessionToken, @RequestBody GenericValueRequest request) {
        return noticeService.getNoticeImage(sessionToken, request);
    }

    @RequestMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GenericResponse> approveNoticeById(@RequestHeader(name = "Authorization") String sessionToken, @RequestBody GenericValueRequest request) {
        return noticeService.approveNotice(sessionToken, request);
    }

}
