package com.riverway.housingfinance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Bank {

    @Id
    private String instituteCode;

    @Column(nullable = false, unique = true)
    private String instituteName;

    public boolean match(BankName bankName) {
        return instituteName.equals(bankName.getBankName());
    }
}
