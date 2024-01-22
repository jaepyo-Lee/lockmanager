package com.ime.lockmanager.account.application.service;

import com.ime.lockmanager.account.application.port.in.AccountUsecase;
import com.ime.lockmanager.account.application.port.in.req.SaveOrModifyAccountRequestDto;
import com.ime.lockmanager.account.application.port.in.res.AccountInfoResponseDto;
import com.ime.lockmanager.account.application.port.in.res.SaveOrModifyAccountResponseDto;
import com.ime.lockmanager.account.application.port.out.AccountQueryPort;
import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.account.domain.dto.ModifyAccountDto;
import com.ime.lockmanager.common.format.exception.account.NotFoundAccountException;
import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class AccountService implements AccountUsecase {
    private final MajorQueryPort majorQueryPort;
    private final AccountQueryPort accountQueryPort;

    @Transactional(readOnly = true)
    @Override
    public AccountInfoResponseDto findAccountInfo(Long majorId) {
        Account account = accountQueryPort.findByMajorId(majorId)
                .orElseThrow(NotFoundAccountException::new); //예외처리해야함
        return AccountInfoResponseDto.builder()
                .accountNum(account.getNumber())
                .bank(account.getBank())
                .ownerName(account.getOwnerName())
                .build();
    }


    @Override
    public SaveOrModifyAccountResponseDto saveOrModifyAccountInfo(Long majorId,
                                                                  SaveOrModifyAccountRequestDto saveOrModifyAccountRequestDto) {

        Account saveOrModifiedAccount = null;
        Major major = majorQueryPort.findById(majorId).orElseThrow(NotFoundMajorDetailException::new);//예외 수정해야함
        Optional<Account> findAccount = accountQueryPort.findByMajor(major);


        if (findAccount.isPresent()) {
            saveOrModifiedAccount = modifyExistingAccount(findAccount.get(), saveOrModifyAccountRequestDto);
        } else {
            saveOrModifiedAccount = saveNewAccount(major, saveOrModifyAccountRequestDto);
        }

        return SaveOrModifyAccountResponseDto.builder()
                .accountNum(saveOrModifiedAccount.getNumber())
                .bank(saveOrModifiedAccount.getBank())
                .ownerName(saveOrModifiedAccount.getOwnerName())
                .build();
    }

    private Account modifyExistingAccount(Account existingAccount, SaveOrModifyAccountRequestDto requestDto) {
        return existingAccount.modifyAccountInfo(ModifyAccountDto.builder()
                .accountNum(requestDto.getAccountNum())
                .bank(requestDto.getBank())
                .ownerName(requestDto.getOwnerName())
                .build());
    }

    private Account saveNewAccount(Major major, SaveOrModifyAccountRequestDto requestDto) {
        return accountQueryPort.save(Account.builder()
                .number(requestDto.getAccountNum())
                .bank(requestDto.getBank())
                .ownerName(requestDto.getOwnerName())
                .major(major)
                .build());
    }
}
