package com.ime.lockmanager.common.webclient.sejong.service;

import com.ime.lockmanager.common.format.exception.auth.InvalidLoginParamException;
import com.ime.lockmanager.common.format.exception.auth.NotEnoughWebclientLoginParamException;
import com.ime.lockmanager.common.format.exception.webclient.WebClientAuthServerException;
import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SejongLoginService {
    @Value("${sejong.login_server}")
    private String SEJONG_LOGIN_SERVER;
    public SejongMemberResponseDto callSejongMemberDetailApi(SejongMemberRequestDto requestDto) {
        return WebClient.builder()
                .baseUrl(SEJONG_LOGIN_SERVER)
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder.queryParam("method", "ClassicSession").build())
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> handleLoginServerError(clientResponse))
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> handleWebclientLoginParamError(clientResponse))
                .bodyToMono(SejongMemberResponseDto.class)
                .flatMap(responseDto -> verifyLoginParamError(responseDto))
                .block();
    }

    private Mono<SejongMemberResponseDto> verifyLoginParamError(SejongMemberResponseDto responseDto) {
        if ("false".equals(responseDto.getResult().getIs_auth())) {
            return Mono.error(new InvalidLoginParamException());
        }
        return Mono.just(responseDto);
    }

    private Mono<? extends Throwable> handleWebclientLoginParamError(ClientResponse clientResponse) {
        return ClientResponse.create(HttpStatus.BAD_REQUEST)
                .build().createException()
                .flatMap(e -> Mono.error(new NotEnoughWebclientLoginParamException()));
    }
    private Mono<? extends Throwable> handleLoginServerError(ClientResponse clientResponse) {
        return ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR)
                .build().createException()
                .flatMap(e -> Mono.error(new WebClientAuthServerException()));
    }
}
