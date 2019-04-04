package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.dto.BankDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Bank {

    @Id
    private String instituteCode;

    @Column(nullable = false, unique = true)
    private String instituteName;

    public boolean match(BankName bankName) {
        return instituteName.equals(bankName.getBankName());
    }

    public BankDto toBankDto() {
        return new BankDto(instituteCode, instituteName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(instituteCode, bank.instituteCode) &&
                Objects.equals(instituteName, bank.instituteName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instituteCode, instituteName);
    }
}
