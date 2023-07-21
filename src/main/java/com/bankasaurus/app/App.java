/*----------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 *---------------------------------------------------------------------------------------*/

package com.bankasaurus.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

import static java.lang.Thread.sleep;

public class App {

    public static void main(String[] args) throws InterruptedException {
        int selection;
        Scanner sc = new Scanner(System.in);

        String filePath = "/Accounts.json";
        File file = OpenFile(filePath);

        ObjectMapper mapper = new ObjectMapper();
        AccountRepository repo = new AccountRepository(file, mapper);
        Bank bank = new Bank(repo);

        DisplayLogo();
        outer:
        while (true) {

            DisplayMenu();
            selection = CaptureIntSelection(sc);
            switch (selection) {
                case 1:
                    DisplayAccountList(bank);
                    PromptUser("Please Select Account to show balance:");
                    var selectedAccountIdBalance = CaptureIntSelection(sc);
                    DisplayBalance(bank.RetrieveAccount(selectedAccountIdBalance));
                    break;
                case 2:
                    DisplayAccountList(bank);
                    PromptUser("Please Select Account to make a deposit:");
                    var selectedAccountIdDeposit = CaptureIntSelection(sc);
                    var depositAccount = bank.RetrieveAccount(selectedAccountIdDeposit);
                    System.out.print("How much would you like to deposit?\n\n$");
                    BigDecimal deposit = sc.nextBigDecimal();
                    BigDecimal afterDeposit = bank.Deposit(depositAccount.get().getId(), deposit);

                    String message = afterDeposit == null ?
                        "Error making deposit! Balance: $" +  afterDeposit + "\n\n": "Deposit Successful! Balance: $" + afterDeposit + "\n\n";
                    System.out.print(message);
                    break;

                case 3:
                    System.out.print("Choose account to transfer from:\n\n");
                    // TODO list accounts
                    System.out.print("Choose account to transfer to:\n\n");
                    // TODO list accounts all but one
                    System.out.print("How much would you like to transfer?\n\n");
                    // TODO deposit money in account
                    System.out.print("Transfer Successful!\n\n1");
                    // TODO print deposit
                    break;
                case 4:
                    System.out.println("\nThank you for choosing Bankasaurus.");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void DisplayLogo() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("██████   █████  ███    ██ ██   ██  █████  ███████  █████  ██    ██ ██████  ██    ██ ███████ \n" +
                "██   ██ ██   ██ ████   ██ ██  ██  ██   ██ ██      ██   ██ ██    ██ ██   ██ ██    ██ ██      \n" +
                "██████  ███████ ██ ██  ██ █████   ███████ ███████ ███████ ██    ██ ██████  ██    ██ ███████ \n" +
                "██   ██ ██   ██ ██  ██ ██ ██  ██  ██   ██      ██ ██   ██ ██    ██ ██   ██ ██    ██      ██ \n" +
                "██████  ██   ██ ██   ████ ██   ██ ██   ██ ███████ ██   ██  ██████  ██   ██  ██████  ███████ \n" +
                "                                                                                            \n" +
                "                                                                                            ");
        System.out.println("24-Hour Banking Application\n");
    }

    private static File OpenFile(String filePath) {
        URL fileUrl = App.class.getResource(filePath);
        assert fileUrl != null;
        return new File(fileUrl.getFile());
    }

    private static int CaptureIntSelection(Scanner sc) {
        int choice;
        choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private static void DisplayMenu() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Transfer Money");
        System.out.println("4. Pay Bills");
        System.out.println("5. Exit");
        System.out.print("\nEnter your choice : ");
    }

    private static void DisplayBalance(Optional<Account> selectedAccount) {
        if(selectedAccount.isPresent()) {
            System.out.print("Balance: $" + selectedAccount.get().GetBalance() + "\n\n");
            selectedAccount.get().GetBalance();
        } else {
            System.out.println("Account not found");
        }
    }

    private static void PromptUser(String s) {
        System.out.print(s+"\n\n");
    }

    private static void DisplayAccountList(Bank bank) {
        System.out.print("Accounts on file:\n\n");
        var accounts = bank.GetAccounts();
        Formatter fmt = new Formatter();
        var tableFmt = "%-15s %-15s\n";
        fmt.format(tableFmt, "Account ID", "Account Name");
        for (Account account : accounts)
        {
            fmt.format(tableFmt, account.getId(), account.getAccountName());
        }
        System.out.println(fmt);
    }
}
