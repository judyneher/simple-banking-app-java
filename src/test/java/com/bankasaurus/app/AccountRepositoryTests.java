package com.bankasaurus.app;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountRepositoryTests {

    private final String account1Name = "Account 1";
    private final BigDecimal account1Balance = new BigDecimal("10.00");
    private final String account2Name = "Account 2";
    private final BigDecimal account2Balance = new BigDecimal("20.00");
    private final String account3Name = "Account 3";
    private final BigDecimal account3Balance = new BigDecimal("30.00");
    private final String account4Name = "Account 4";
    private final BigDecimal account4Balance = new BigDecimal("40.00");

    public AccountRepository SetupRepo(){
        var repo = new AccountRepository();
        repo.AddAccount(account1Name, account1Balance);
        repo.AddAccount(account2Name, account2Balance);
        repo.AddAccount(account3Name, account3Balance);
        repo.AddAccount(account4Name, account4Balance);
        return repo;
    }

    @Test
    public void AccountFound() {
        // Given
        var repo = SetupRepo();
        var accounts = repo.GetAccounts();
        var lastAccount = accounts.get(accounts.size() -1);

        // When
        var fetchedAccount = repo.GetAccount(lastAccount.GetId());

        // Then
        assertTrue(fetchedAccount.isPresent());
        assertEquals(lastAccount.GetId(), fetchedAccount.get().GetId());
        assertEquals(lastAccount.GetAccountName(), fetchedAccount.get().GetAccountName());
        assertEquals(lastAccount.GetBalance(), fetchedAccount.get().GetBalance());
    }
    @Test
    public void AccountOptionalReturnedNoMatch() {
        var repo = SetupRepo();
        var idDoesNotExist = 1000;
        var noAccount = repo.GetAccount(idDoesNotExist);
        assertTrue(noAccount.isEmpty());
    }
    @Test
    public void AccountNotReturnedEmptyRepo() {
        var repo = new AccountRepository();
        var idDoesNotExist = 1000;
        var noAccount = repo.GetAccount(idDoesNotExist);
        assertTrue(noAccount.isEmpty());
    }

    @Test
    public void AccountsPersisted() {
        // Given
        var repo = SetupRepo();

        // When
        var accounts = repo.GetAccounts();

        // Then
        assertEquals(4, accounts.size());
    }

    @Test
    public void AccountsHaveUniqueSequentialIds() {
        // Given
        var repo = SetupRepo();

        // When
        var accounts = repo.GetAccounts();

        // Then
        for(int i = 0; i < accounts.size(); i++){
            for(int j = i+1; j < accounts.size(); j++) {
                assertNotEquals(accounts.get(i).GetId(), accounts.get(j).GetId());
            }
        }
    }

    @Test
    public void AccountIdStartsWithZero() {
        // Given
        var repo = SetupRepo();

        // When
        var accounts = repo.GetAccounts();

        // Then
        for(int i = 0; i < accounts.size(); i++){
            for(int j = i+1; j < accounts.size(); j++) {
                assertNotEquals(accounts.get(i).GetId(), accounts.get(j).GetId());
            }
        }
    }
    @Test
    public void AccountsHaveSequentialIds() {
        // Given
        var repo = SetupRepo();

        // When
        var accounts = repo.GetAccounts();

        // Then
        var account1 = accounts.get(0);
        assertSame(account1Name, account1.GetAccountName());
        assertSame(0, account1.GetId());

        var account2 = accounts.get(1);
        assertSame(account2Name, account2.GetAccountName());
        assertSame(1, account2.GetId());

        var account3 = accounts.get(2);
        assertSame(account3Name, account3.GetAccountName());
        assertSame(2, account3.GetId());

        var account4 = accounts.get(3);
        assertSame(account4Name, account4.GetAccountName());
        assertSame(3, account4.GetId());
    }

    @Test
    public void AccountValuesPersisted() {
        // Given
        var repo = SetupRepo();

        // When
        var accounts = repo.GetAccounts();

        // Then
        var account1 = accounts.get(0);
        assertSame(account1Name, account1.GetAccountName());
        assertSame(account1Balance, account1.GetBalance());

        var account2 = accounts.get(1);
        assertSame(account2Name, account2.GetAccountName());
        assertSame(account2Balance, account2.GetBalance());

        var account3 = accounts.get(2);
        assertSame(account3Name, account3.GetAccountName());
        assertSame(account3Balance, account3.GetBalance());

        var account4 = accounts.get(3);
        assertSame(account4Name, account4.GetAccountName());
        assertSame(account4Balance, account4.GetBalance());
    }
}
