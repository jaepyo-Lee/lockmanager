package com.ime.lockmanager.common.sse;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.user.prefix}")
public class SSEController {

    private final static String SSE_LOCKER = "LOCKER_";
    private final SseEmitterService sseEmitterService;

    @GetMapping(value = "/sse/connect/lockers",produces = TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connectLocker(@ApiIgnore Authentication authentication,
                                                    @RequestParam Long majorId) {
        return ResponseEntity.ok(sseEmitterService.connect(SSE_LOCKER + majorId.toString()));
    }
}
