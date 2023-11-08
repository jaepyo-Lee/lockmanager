package com.ime.lockmanager.common.webclient.sejong.service;

import com.ime.lockmanager.common.format.exception.auth.InvalidLoginParamException;
import com.ime.lockmanager.common.format.exception.auth.NotImeStudentException;
import com.ime.lockmanager.common.format.exception.webclient.SejongIncorrectInformException;
import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Service
public class SejongLoginService {
    private static List<String> MAJOR_LIST = new ArrayList<>(List.of("스마트기기공학전공", "무인이동체공학전공", "지능기전공학과", "지능기전공학부"));

    public SejongMemberResponseDto callSejongMemberDetailApi(SejongMemberRequestDto requestDto) {
        return WebClient.builder()
                .baseUrl("https://auth.imsejong.com/auth")
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("method", "ClassicSession")
                        .build())
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError(), this::handleInvalidParamError)
                .bodyToMono(SejongMemberResponseDto.class).block();
    }

    private Mono<? extends Throwable> handleInvalidParamError(ClientResponse clientResponse) {
        return ClientResponse.create(HttpStatus.BAD_REQUEST)
                .build().createException()
                .flatMap(e -> Mono.error(new InvalidLoginParamException()));
    }
}
