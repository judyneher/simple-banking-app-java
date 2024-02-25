package com.bankasaurus.app;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;
    private String accountName;
    private Integer id;

    public Account(BigDecimal balance, String accountName, Integer accountNumber) {
        this.balance = balance;
        this.accountName = accountName;
        this.id = accountNumber;
    }

    public Account() {}

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

    public Integer GetId() {
        return id;
    }

    public void SetId(Integer id) {
        this.id = id;
    }

    public String GetAccountName() {
        return accountName;
    }

    public void SetAccountName(String accountName) {
        this.accountName = accountName;
    }
}
