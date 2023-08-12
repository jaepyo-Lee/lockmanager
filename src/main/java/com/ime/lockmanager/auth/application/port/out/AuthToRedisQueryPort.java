package com.ime.lockmanager.auth.application.port.out;

import java.time.Duration;
import java.util.Date;

public interface AuthToRedisQueryPort {
    void refreshSave(String studentNum,String refreshToken);

    void logoutTokenSave(String accessToken, Duration leftTime);
    String getRefreshToken(String studentNum);

    void removeRefreshToken(String studentNum);
    void removeAndSaveRefreshToken(String studentNum, String refreshToken);
    boolean validateToken(String accessToken);
}
