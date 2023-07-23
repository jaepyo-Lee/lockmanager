package com.ime.lockmanager.common.redis;

import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import com.ime.lockmanager.common.format.exception.auth.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository implements AuthToRedisQueryPort {
    private final RedisTemplate redisTemplate;


    @Override
    public void refreshSave(String studentNum,String refreshToken) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(studentNum,refreshToken,14, TimeUnit.DAYS);
    }

    @Override
    public String getRefreshToken(String studentNum) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return (String) valueOperations.get(studentNum);
    }

    @Override
    public void removeRefreshToken(String studentNum) {
        redisTemplate.delete(studentNum);
    }

    @Override
    public void removeAndSaveRefreshToken(String studentNum, String refreshToken) {
        redisTemplate.delete(studentNum);
        refreshSave(studentNum, refreshToken);
    }

    @Override
    public void logoutTokenSave(String accessToken, Duration leftTime) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(accessToken,"logout",leftTime);
    }

    //로그아웃이고, 아무것도 없으면 false
    @Override
    public boolean validateToken(String accessToken) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String s = (String) valueOperations.get(accessToken);
        if(s==null){
            return true;
        }else if (!s.equals("logout")){
            throw new InvalidRefreshTokenException();
        }
        else{
            return false;
        }
    }
}
