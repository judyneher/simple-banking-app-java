package com.bankasaurus.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Bank {
    private final AccountRepository repository;

    public Bank(AccountRepository accountRepository) {
        repository = accountRepository;
    }

    public List<Account> RetrieveAccounts() {
        return repository.GetAccounts();
    }

    public Optional<Account> RetrieveAccount(Integer accountId) {
        return repository.GetAccount(accountId);
    }

    public TransactionStatus OpenAccount(String accountName, BigDecimal initialDeposit) {
        return new TransactionStatus(Status.Failure, "Open Account functionality has not been implemented\n\n");
    }

    public TransactionStatus Deposit(Integer accountId, BigDecimal deposit) {
        return new TransactionStatus(Status.Failure, "Deposit functionality has not been implemented\n\n");
    }

    public TransactionStatus TransferFunds(Integer fromAccountId, Integer toAccountId, BigDecimal transferAmount) {
        return new TransactionStatus(Status.Failure, "Transfer Funds functionality has not been implemented\n\n");
    }
}
