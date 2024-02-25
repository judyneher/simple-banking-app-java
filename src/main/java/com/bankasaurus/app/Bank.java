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
        var response = new TransactionStatus();
        if(initialDeposit.compareTo(BigDecimal.ZERO) < 0){
            response.Status = Status.Failure;
            response.Message = "Deposit must be greater than zero!\n\n";
            return response;
        }

        if(accountName.isEmpty() || accountName.isBlank()){
            response.Status = Status.Failure;
            response.Message = "Account Name cannot be empty\n\n";
            return response;
        }

        repository.AddAccount(accountName, initialDeposit);
        response.Status = Status.Success;
        response.Message = "Account successfully opened!\n\n";
        return response;
    }

    public TransactionStatus Deposit(Integer accountId, BigDecimal deposit) {
        var response = new TransactionStatus();
        if(deposit.compareTo(BigDecimal.ZERO) < 0){
            response.Status = Status.Failure;
            response.Message = "Deposit must be greater than zero!\n\n";
            return response;
        }

        // todo return error if more than 2 decimal places

        var account = this.RetrieveAccount(accountId);
        if(account.isEmpty()) {
            response.Status = Status.Failure;
            response.Message = "Could not find account!\n\n";
            return response;
        }

        var endingBalance = account.get().Deposit(deposit);
        response.Status = Status.Success;
        response.Message = "Deposit successful, balance: $" + endingBalance + "\n\n";
        return response;
    }

    public TransactionStatus TransferFunds(Integer fromAccountId, Integer toAccountId, BigDecimal transferAmount) {
        var response = new TransactionStatus();

        // todo account null error

        if(fromAccountId.equals(toAccountId)) {
            response.Status = Status.Failure;
            response.Message = "Error: Source account and destination account cannot be the same!\n\n";
            return response;
        }

        // todo return error if more than 2 decimal places

        var from = RetrieveAccount(fromAccountId);
        var to = RetrieveAccount(toAccountId);

        if(from.isEmpty() || to.isEmpty()){
            response.Status = Status.Failure;
            response.Message = "Error retrieving accounts!\n\n";
            return response;
        }

        if(from.get().GetBalance().compareTo(transferAmount) < 0) {
            response.Status = Status.Failure;
            response.Message = "Error: Source account and destination account cannot be the same!\n\n";
            return response;
        }

        from.get().Withdrawal(transferAmount);
        to.get().Deposit(transferAmount);
        response.Status = Status.Success;
        response.Message = "Transfer Successful!\n\n";
        return response;
    }
}
