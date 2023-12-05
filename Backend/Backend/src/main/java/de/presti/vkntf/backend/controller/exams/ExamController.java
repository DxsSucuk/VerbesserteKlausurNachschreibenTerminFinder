package de.presti.vkntf.backend.controller.exams;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.request.ExamCreateRequest;
import de.presti.vkntf.backend.repository.exam.MissedExam;
import de.presti.vkntf.backend.service.ExamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exam", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ExamController {

    ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @RequestMapping(value = "/create")
    public Mono<GenericResponse> createMissedExams(@RequestHeader(name = "Authorization") String sessionToken, @RequestBody ExamCreateRequest request) {
        return examService.createMissedExam(sessionToken, request).flatMap(x -> {
            if (x) {
                return Mono.just(new GenericResponse(true, "Missed exam created"));
            }

            return Mono.just(new GenericResponse(false, "Missed exam not created"));
        }).switchIfEmpty(Mono.just(new GenericResponse(false, "Missed exam not created")));
    }

    @RequestMapping(value = "/missed")
    public Mono<GenericObjectResponse<List<MissedExam>>> getMissedExams(@RequestHeader(name = "Authorization") String sessionToken) {
        return examService.getMissedExams(sessionToken).flatMap(x -> {
            if (x.isEmpty()) {
                return Mono.just(new GenericObjectResponse<List<MissedExam>>(false, x, "No missed exams found"));
            }

            return Mono.just(new GenericObjectResponse<List<MissedExam>>(true, x, "Missed exams found"));
        }).switchIfEmpty(Mono.just(new GenericObjectResponse<List<MissedExam>>(false, null, "No missed exams found")));
    }

}
