package com.bankasaurus.app;

import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class BankTests {

    private final String account1Name = "Account 1";
    private final BigDecimal account1Balance = new BigDecimal("10.00");
    private final String account2Name = "Account 2";
    private final BigDecimal account2Balance = new BigDecimal("20.00");
    private final String account3Name = "Account 3";
    private final BigDecimal account3Balance = new BigDecimal("30.00");
    private final String account4Name = "Account 4";
    private final BigDecimal account4Balance = new BigDecimal("40.00");

    public AccountRepository SetupRepo() {
        var repo = new AccountRepository();
        repo.AddAccount(account1Name, account1Balance);
        repo.AddAccount(account2Name, account2Balance);
        repo.AddAccount(account3Name, account3Balance);
        repo.AddAccount(account4Name, account4Balance);
        return repo;
    }

    public Bank SetupBank() {
        return new Bank(SetupRepo());
    }

    @Test
    public void SuccessfulOpenAccountWithValidInput() {
        var bank = SetupBank();
        String validAccoutName = "New Test Account";
        var validDecimal = new BigDecimal("10.00");
        var response = bank.OpenAccount(validAccoutName, validDecimal);
        assertEquals(Status.Success, response.Status);
        assertTrue(response.Message.contains("Account successfully opened!"));
    }

    @Test
    public void SuccessfulDepositWithValidAccountIdAndDeposit() {
        var bank = SetupBank();
        var validDeposit = new BigDecimal("10.00");
        var validAccount2Id = 1;
        var response = bank.Deposit(validAccount2Id, validDeposit);
        assertEquals(Status.Success, response.Status);
        assertTrue(response.Message.contains("Deposit successful, balance: $" + validDeposit.add(account2Balance)));
    }

    @Test
    public void SuccessfulTransferFundsWithValidTransferAmount() {
        var bank = SetupBank();
        var validTransferAmount = new BigDecimal("10");
        var validSourceAccountId = 1;
        var validTargetAccountId = 2;
        var response = bank.TransferFunds(validSourceAccountId, validTargetAccountId, validTransferAmount);
        assertEquals(bank.RetrieveAccount(validSourceAccountId).get().GetBalance(), account2Balance.subtract(validTransferAmount));
        assertEquals(bank.RetrieveAccount(validTargetAccountId).get().GetBalance(), account3Balance.add(validTransferAmount));
        assertEquals(Status.Success, response.Status);
        assertTrue(response.Message.contains("Transfer Successful!"));
    }
}
