package com.ime.lockmanager.common.webclient.sejong.service;

import com.ime.lockmanager.common.format.exception.webclient.SejongIncorrectInformException;
import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SejongLoginService {

    public SejongMemberResponseDto callSejongMemberDetailApi(SejongMemberRequestDto requestDto){
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
        if (memberResponseDto.getResult().getIs_auth()=="false"){
            throw new SejongIncorrectInformException();
        }
        return memberResponseDto;

    }
}
