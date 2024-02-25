package com.bankasaurus.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final List<Account> accounts;

    public AccountRepository() {
        this.accounts = new ArrayList<>();
    }

    public List<Account> GetAccounts() {
        return accounts;
    }

    public Account AddAccount(String accountName, BigDecimal initialBalance) {
        var newAccount = new Account();
        newAccount.SetAccountName(accountName);
        newAccount.SetId(this.accounts.size());
        newAccount.SetBalance(initialBalance);
        accounts.add(newAccount);
        return newAccount;
    }

    public Optional<Account> GetAccount(Integer accountId) {
        return GetAccounts()
                    .stream()
                    .filter(item -> item.GetId().equals(accountId)).findFirst();
    }
}
