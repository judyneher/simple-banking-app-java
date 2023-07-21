package com.bankasaurus.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

public class Account {
    @JsonSerialize(using = CurrencySerializer.class)
    @JsonProperty("balance")
    private BigDecimal balance;
    private String accountName;
    private Integer id;

    public Account(BigDecimal balance, String accountName, Integer accountNumber) {
        this.balance = balance;
        this.accountName = accountName;
        this.id = accountNumber;
    }

    public Account() {

    }

    public BigDecimal Deposit(BigDecimal deposit) {
        balance = balance.add(deposit);
        return balance;
    }

    public BigDecimal GetBalance() {
        return balance;
    }

    public void SetBalance(BigDecimal bal) {
        balance = bal;
    }

    public BigDecimal Withdrawal(BigDecimal withdrawal) {
        balance = balance.subtract(withdrawal);
        return balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
