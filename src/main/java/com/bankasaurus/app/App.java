
package com.bankasaurus.app;
import java.math.BigDecimal;
import java.util.*;

public class App {

    public static void main(String[] args) throws InterruptedException {
        int selection;
        Scanner sc = new Scanner(System.in);
        AccountRepository repo = new AccountRepository();
        Bank bank = new Bank(repo);

        DisplayLogo();
        outer:
        while (true) {
            DisplayMenu();
            selection = CaptureIntSelection(sc);
            switch (selection) {
                case 1:
                    PromptUser("Please enter name for account and press enter:");
                    var accountName = sc.nextLine();
                    System.out.print("How much would you like to deposit?\n\n$");
                    BigDecimal initialDeposit = sc.nextBigDecimal();
                    var openStatus = bank.OpenAccount(accountName, initialDeposit);
                    System.out.print(openStatus.Message);
                    break;
                case 2:
                    DisplayAccountList(bank.RetrieveAccounts());
                    PromptUser("Please Select Account to show balance:");
                    var selectedAccountIdBalance = CaptureIntSelection(sc);
                    DisplayBalance(bank.RetrieveAccount(selectedAccountIdBalance));
                    break;
                case 3:
                    DisplayAccountList(bank.RetrieveAccounts());
                    PromptUser("Please Select Account to make a deposit:");
                    var selectedAccountIdDeposit = CaptureIntSelection(sc);
                    System.out.print("How much would you like to deposit?\n\n$");
                    BigDecimal deposit = sc.nextBigDecimal();
                    var transactionStatus = bank.Deposit(selectedAccountIdDeposit, deposit);
                    System.out.print(transactionStatus.Message);
                    break;
                case 4:
                    System.out.print("Choose account to transfer from:\n\n");
                    // TODO list accounts
                    System.out.print("Choose account to transfer to:\n\n");
                    // TODO list accounts all but one
                    System.out.print("How much would you like to transfer?\n\n");
                    // TODO deposit money in account
                    System.out.print("Transfer Successful!\n\n1");
                    // TODO print deposit
                    break;
                case 5:
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

    private static int CaptureIntSelection(Scanner sc) {
        int choice;
        choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private static void DisplayMenu() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("1. Open Account");
        System.out.println("2. Check Balance");
        System.out.println("3. Deposit Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Pay Bills");
        System.out.println("6. Exit");
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

    private static void DisplayAccountList(List<Account> accounts) {
        System.out.print("Accounts on file:\n\n");
        Formatter fmt = new Formatter();
        var tableFmt = "%-15s %-15s\n";
        fmt.format(tableFmt, "Account ID", "Account Name");
        for (Account account : accounts)
        {
            fmt.format(tableFmt, account.GetId(), account.GetAccountName());
        }
        System.out.println(fmt);
    }
}
