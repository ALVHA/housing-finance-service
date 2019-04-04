package com.riverway.housingfinance.bank;

public enum BankName {

    JUTAEK("주택도시기금"),
    KOOKMIN("국민은행"),
    WOORI("우리은행"),
    SHINHAN("신한은행"),
    KOREA_CITY("한국시티은행"),
    HANA("하나은행"),
    NONGHYUP_SUHYUP("농협은행/수협은행"),
    EXCHANGE("외환은행"),
    ETC("기타은행");

    private String bankName;

    BankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public static BankName of(String bankName) {
        for (BankName value : values()) {
            if (bankName.startsWith(value.bankName)) {
                return value;
            }
        }
        return BankName.ETC;
    }
}