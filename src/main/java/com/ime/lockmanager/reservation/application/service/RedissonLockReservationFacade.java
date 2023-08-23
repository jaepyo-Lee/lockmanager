package com.ime.lockmanager.reservation.application.service;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockReservationFacade {
    private final RedissonClient redissonClient;
    private final ReservationUseCase reservationUseCase;
    private static LockerRegisterResponseDto register;
    private static final String LOCK_PREFIX = "lockerId : ";

    public LockerRegisterResponseDto register(LockerRegisterRequestDto dto) throws Exception {

        RLock lock = redissonClient.getLock(LOCK_PREFIX+dto.getLockerNum());

        boolean available = lock.tryLock(5, 2, TimeUnit.SECONDS);
        if (!available) {
            log.error("lock 획득실패");
            return null;
        }
        log.info("redisson : lock 획득 후 로직 진행");
        register = reservationUseCase.register(dto);

        if(lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
        return register;

    }

}
