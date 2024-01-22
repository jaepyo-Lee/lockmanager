package com.ime.lockmanager.account.application.port.in;

import com.ime.lockmanager.account.application.port.in.req.SaveOrModifyAccountRequestDto;
import com.ime.lockmanager.account.application.port.in.res.AccountInfoResponseDto;
import com.ime.lockmanager.account.application.port.in.res.SaveOrModifyAccountResponseDto;

public interface AccountUsecase {
    AccountInfoResponseDto findAccountInfo(Long majorId);

    SaveOrModifyAccountResponseDto saveOrModifyAccountInfo(Long majorId,
                                                           SaveOrModifyAccountRequestDto saveOrModifyAccountRequestDto);

}
