package de.presti.vkntf.backend.controller.auth;

import de.presti.vkntf.backend.api.GenericObjectResponse;
import de.presti.vkntf.backend.api.GenericResponse;
import de.presti.vkntf.backend.api.request.GenericValueRequest;
import de.presti.vkntf.backend.api.request.UserLoginRequest;
import de.presti.vkntf.backend.repository.session.Session;
import de.presti.vkntf.backend.service.UserManagerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    UserManagerService userManagerService;

    public AuthController(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    @RequestMapping(value = "/login")
    public Mono<GenericObjectResponse<Session>> login(@RequestBody UserLoginRequest userLoginRequest) {
        return userManagerService.login(userLoginRequest.name(), userLoginRequest.password());
    }

    @RequestMapping(value = "/check")
    public Mono<GenericObjectResponse<Session>> check(@RequestBody GenericValueRequest valueRequest) {
        return userManagerService.check(valueRequest.value());
    }

    @RequestMapping(value = "/logout")
    public Mono<GenericResponse> logout(@RequestBody GenericValueRequest valueRequest) {
        return userManagerService.logout(valueRequest.value());
    }

}
