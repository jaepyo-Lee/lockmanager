package com.ime.lockmanager.common.webclient.sejong.service;

import com.ime.lockmanager.common.format.exception.auth.InvalidLoginParamException;
import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import lombok.RequiredArgsConstructor;
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
    private static List<String> MAJOR_LIST = new ArrayList<>(List.of("스마트기기공학전공", "무인이동체공학전공", "지능기전공학과", "지능기전공학부"));

    public SejongMemberResponseDto callSejongMemberDetailApi(SejongMemberRequestDto requestDto) {
        return WebClient.builder()
                .baseUrl("https://auth.imsejong.com/auth")
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder.queryParam("method", "ClassicSession").build())
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new IllegalStateException("Server Error")))
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

    /*private Mono<? extends Throwable> handleLoginServerError(ClientResponse clientResponse) {

        ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR)
                .build().createException()
                .flatMap(e -> Mono.error(new IllegalArgumentException("로그인 서버 에러입니다. 관리자에게 문의해주세요")));
    }*/
}
