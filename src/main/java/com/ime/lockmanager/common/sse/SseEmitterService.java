package com.ime.lockmanager.common.sse;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
@Service
public class SseEmitterService {
    // thread-safe 한 컬렉션 객체로 sse emitter 객체를 관리해야 한다.
    private final static Map<String, Set<SseEmitter>> emitterMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 60 * 1000;
    private static final long RECONNECTION_TIMEOUT = 1000L;

    public SseEmitter connect(final String lockerSSE) {
        SseEmitter sseEmitter = createEmitter();

        sendEvent(sseEmitter,createInitSseBuilder());    // (3)

        Set<SseEmitter> sseEmitters = emitterMap.getOrDefault(lockerSSE, new HashSet<>());    // (4)

        sseEmitter.onCompletion(() -> {    // (5)
            sseEmitters.remove(sseEmitter);
        });
        sseEmitter.onTimeout(()->sseEmitters.remove(sseEmitter));
        sseEmitter.onError((e)->sseEmitters.remove(sseEmitter));

        sseEmitters.add(sseEmitter);

        emitterMap.put(lockerSSE,sseEmitters);

        return sseEmitter;
    }

    private static SseEmitter.SseEventBuilder createInitSseBuilder() {
        final SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()    // (2)
                .name("connect")
                .data("connected!")
                .reconnectTime(3000L);
        return sseEventBuilder;
    }

    public static void sendEvent(final SseEmitter sseEmitter, final SseEmitter.SseEventBuilder sseEventBuilder) {
        try {
            sseEmitter.send(sseEventBuilder);
        } catch (IOException e) {
            sseEmitter.complete();
        }
    }
    private SseEmitter createEmitter() {
        return new SseEmitter(TIMEOUT);
    }
    public static Map<String,Set<SseEmitter>>getEmitterMap(){
        return emitterMap;
    }
}
