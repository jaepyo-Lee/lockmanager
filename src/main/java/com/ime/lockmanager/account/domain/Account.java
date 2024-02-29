package com.ime.lockmanager.account.domain;

import com.ime.lockmanager.account.domain.dto.ModifyAccountDto;
import com.ime.lockmanager.common.domain.BaseTimeEntity;
import com.ime.lockmanager.major.domain.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "ACCOUNT_TABLE")
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String bank;
    private String ownerName;
    @OneToOne
    @JoinColumn(name = "major_id")
    private Major major;

    public Account modifyAccountInfo(ModifyAccountDto modifyAccountDto){
        bank = modifyAccountDto.getBank();
        number = modifyAccountDto.getAccountNum();
        ownerName = modifyAccountDto.getOwnerName();
        return this;
    }
}
