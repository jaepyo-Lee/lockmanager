package com.ime.lockmanager.common.webclient.sejong.service;

import com.ime.lockmanager.common.format.exception.auth.NotImeStudentException;
import com.ime.lockmanager.common.format.exception.webclient.SejongIncorrectInformException;
import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SejongLoginService {
    private static List<String> MAJOR_LIST = new ArrayList<>(List.of("스마트기기공학전공", "무인이동체공학전공", "지능기전공학과"));

    public SejongMemberResponseDto callSejongMemberDetailApi(SejongMemberRequestDto requestDto) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://auth.imsejong.com/auth")
                .build();
        SejongMemberResponseDto memberResponseDto = webClient.post()
                .uri(
                        uriBuilder -> uriBuilder
                                .queryParam("method", "ClassicSession")
                                .build()
                ).body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(SejongMemberResponseDto.class)
                .block();
        if (memberResponseDto.getResult().getIs_auth() == "false") {
            throw new SejongIncorrectInformException();
        } else if (!MAJOR_LIST.contains(memberResponseDto.getResult().getBody().getMajor())){
            throw new NotImeStudentException();
        }

            return memberResponseDto;

    }
}
