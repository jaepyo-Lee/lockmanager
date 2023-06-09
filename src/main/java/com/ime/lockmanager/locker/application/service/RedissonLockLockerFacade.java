package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.common.exception.ApplicationRunException;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockLockerFacade{
    private final RedissonClient redissonClient;
    private final LockerUseCase lockerUseCase;

    public LockerRegisterResponseDto register(LockerRegisterRequestDto dto) throws Exception {
        RLock lock = redissonClient.getLock(dto.getStudentNum().toString());
        LockerRegisterResponseDto registerResponseDto = null;

        boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);
        if (!available) {
            log.error("lock 획득실패");
            return null;
        }
        log.info("redisson : lock 획득 후 로직 진행");
        registerResponseDto = lockerUseCase.register(dto);

        lock.unlock();
        return registerResponseDto;

    }
}
