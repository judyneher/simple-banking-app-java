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

    // TODO:
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
    public void OpenAccountWithEmptyNameReturnsFailure() {
        // Given
        var bank = SetupBank();
        String accountName = "";

        // When
        var response = bank.OpenAccount(accountName, BigDecimal.ZERO);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Account Name cannot be empty"));
    }

    @Test
    public void OpenAccountWithOnlySpacesInNameReturnsFailure() {
        // Given
        var bank = SetupBank();
        String accountName = "   ";

        // When
        var response = bank.OpenAccount(accountName, BigDecimal.ZERO);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Account Name cannot be empty"));
    }

    @Test
    public void OpenAccountWithNegativeDepositReturnsFailure() {
        // Given
        var bank = SetupBank();
        String validAccoutName = "New Test Account";
        var negativeDecimal = new BigDecimal("-10.00");

        // When
        var response = bank.OpenAccount(validAccoutName, negativeDecimal);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Deposit must be greater than zero!"));
    }

    @Test
    public void OpenAccountSuccessfulWithValidInput() {
        // Given
        var bank = SetupBank();
        String validAccoutName = "New Test Account";
        var validDecimal = new BigDecimal("10.00");

        // When
        var response = bank.OpenAccount(validAccoutName, validDecimal);

        // Then
        assertEquals(Status.Success, response.Status);
        assertTrue(response.Message.contains("Account successfully opened!"));
    }

    @Test
    public void DepositWithNegativeValueFails() {
        // Given
        var bank = SetupBank();
        var negativeDeposit = new BigDecimal("-10.00");

        // When
        var response = bank.Deposit(bank.RetrieveAccounts().get(0).GetId(), negativeDeposit);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Deposit must be greater than zero!"));
    }

    @Test
    public void DepositWithInvalidAccountIdFails() {
        // Given
        var bank = SetupBank();
        var validDeposit = new BigDecimal("10.00");
        var invalidAccountId = 20000;

        // When
        var response = bank.Deposit(invalidAccountId, validDeposit);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Account not found!"));
    }

    @Test
    public void SuccessfulDepositWithValidAccountIdAndDeposit() {
        // Given
        var bank = SetupBank();
        var validDeposit = new BigDecimal("10.00");
        var validAccount2Id = 1;

        // When
        var response = bank.Deposit(validAccount2Id, validDeposit);

        // Then
        assertEquals(Status.Success, response.Status);
        assertTrue(response.Message.contains("Deposit successful, balance: $" + validDeposit.add(account2Balance)));
    }

    @Test
    public void TransferFundsWithInvalidSourceAccountIdFails() {
        // Given
        var bank = SetupBank();
        var validTransfer = new BigDecimal("1.00");
        var validAccount2Id = 1;
        var invalidSourceAccountId = 200000;

        // When
        var response = bank.TransferFunds(invalidSourceAccountId, validAccount2Id, validTransfer);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Error: Source account not found!"));
    }

    @Test
    public void TransferFundsWithInvalidTargetAccountIdFails() {
        // Given
        var bank = SetupBank();
        var validTransfer = new BigDecimal("1.00");
        var validSourceAccountId = 1;
        var invalidTargetAccountId = 200000;

        // When
        var response = bank.TransferFunds(validSourceAccountId, invalidTargetAccountId, validTransfer);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Error: Target account not found!"));
    }

    @Test
    public void TransferFundsFailsWhenSourceAndTargetAccountSame() {
        // Given
        var bank = SetupBank();
        var validTransfer = new BigDecimal("1.00");
        var validSourceAccountId = 1;

        // When
        var response = bank.TransferFunds(validSourceAccountId, validSourceAccountId, validTransfer);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Error: Source account and destination account cannot be the same!"));
    }

    @Test
    public void TransferFundsFailsWhenTransferExceedsSourceBalance() {
        // Given
        var bank = SetupBank();
        var transferThatExceedsSourceBalance = new BigDecimal("1000000");
        var validSourceAccountId = 1;
        var validTargetAccountId = 2;

        // When
        var response = bank.TransferFunds(validSourceAccountId, validTargetAccountId, transferThatExceedsSourceBalance);

        // Then
        assertEquals(Status.Failure, response.Status);
        assertTrue(response.Message.contains("Error: Source account has insufficient funds!"));
    }
}
