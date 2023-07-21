package com.bankasaurus.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final File file;
    private final ObjectMapper objectMapper;

    public AccountRepository(File file, ObjectMapper objectMapper) {
        this.file = file;
        this.objectMapper = objectMapper;
    }

    public List<Account> GetAccounts() {
        List<Account> accounts;
        try {
            accounts = objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    public Optional<Account> GetAccount(Integer accountId) {
        return GetAccounts()
                    .stream()
                    .filter(item -> item.getId().equals(accountId)).findFirst();

    }

    public void WriteAccounts(List<Account> accounts) {
        try {
            objectMapper.writeValue(file, accounts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void WriteAccount(Account account) {
        var accounts = this.GetAccounts();
        for(int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getId().equals(account.getId())) {
                accounts.set(i, account);
            }
        }
        this.WriteAccounts(accounts);
    }
}
