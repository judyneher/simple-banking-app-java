package com.bankasaurus.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Bank {
    private final AccountRepository repository;

    public Bank(AccountRepository accountRepository) {
        repository = accountRepository;
    }

    public List<Account> GetAccounts() {
        return repository.GetAccounts();
    }

    public Optional<Account> RetrieveAccount(Integer accountId) {
        return repository.GetAccount(accountId);
    }

    public void OpenAccount() {
        var accounts = GetAccounts();
        var newAccount = new Account();
        accounts.add(newAccount);
        repository.WriteAccounts(accounts);
    }

    public void CloseAccount(Account account) {
        var accounts = GetAccounts();
        if(accounts.contains(account)) {
            accounts.remove(account);
            repository.WriteAccounts(accounts);
        } else {
            //TODO  error message
        }
    }

    public BigDecimal Deposit(Integer accountId, BigDecimal deposit) {
        var accounts = GetAccounts();
        if(deposit.compareTo(BigDecimal.ZERO) < 0){
            System.out.print("Deposit must be greater than zero!\n\n");
            return null;
        }

        var account = this.RetrieveAccount(accountId);
        if(account.isEmpty()) {
            System.out.print("Could not find account!\n\n");
            return null;
        }

        var endingBalance = account.get().Deposit(deposit);
        repository.WriteAccount(account.get());
        return endingBalance;
    }


    public void TransferFunds(Integer fromAccountId, Integer toAccountId, BigDecimal transferAmount) {
        var from = RetrieveAccount(fromAccountId);
        var to = RetrieveAccount(toAccountId);

        if(from.isPresent() && to.isPresent()) {
            from.get().Withdrawal(transferAmount);
            to.get().Deposit(transferAmount);
        } else {
            // TODO error message
        }
    }
}
