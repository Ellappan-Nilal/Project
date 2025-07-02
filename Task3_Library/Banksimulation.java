import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// 1. Implement Account class
class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    // 2. Maintain transaction history
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        System.out.println("Account created for " + accountHolderName + " with account number " + accountNumber + " and initial balance " + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction(new Date(), "Deposit", amount, balance));
            System.out.println("Deposited: " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                transactionHistory.add(new Transaction(new Date(), "Withdrawal", amount, balance));
                System.out.println("Withdrew: " + amount + ". New balance: " + balance);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet for account " + accountNumber + ".");
            return;
        }
        System.out.println("\n--- Transaction History for Account: " + accountNumber + " ---");
        System.out.printf("%-25s %-15s %-15s %-15s\n", "Date", "Type", "Amount", "Balance After");
        for (Transaction transaction : transactionHistory) {
            System.out.printf("%-25s %-15s %-15.2f %-15.2f\n",
                    transaction.getDate().toString(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getBalanceAfterTransaction());
        }
        System.out.println("----------------------------------------------");
    }

    // Key Concept: Method Overriding (demonstrated by SavingsAccount and CheckingAccount)
    // This is a basic display method, which can be overridden by subclasses.
    public void displayAccountInfo() {
        System.out.println("\n--- Account Information ---");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Current Balance: " + balance);
    }
}

class Transaction {
    private Date date;
    private String type; // "Deposit" or "Withdrawal"
    private double amount;
    private double balanceAfterTransaction;

    public Transaction(Date date, String type, double amount, double balanceAfterTransaction) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}

// Key Concept: Inheritance
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance, double interestRate) {
        super(accountNumber, accountHolderName, initialBalance);
        this.interestRate = interestRate;
        System.out.println("Savings Account created with interest rate: " + interestRate + "%");
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void applyInterest() {
        double interest = getBalance() * (interestRate / 100);
        deposit(interest); // Use the inherited deposit method
        System.out.println("Interest applied: " + interest + ". New balance: " + getBalance());
    }

    // Key Concept: Method Overriding
    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo(); // Call the superclass method
        System.out.println("Account Type: Savings");
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}

// Key Concept: Inheritance
class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderName, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
        System.out.println("Checking Account created with overdraft limit: " + overdraftLimit);
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    // Key Concept: Method Overriding
    @Override
    public void withdraw(double amount) {
        if (amount > 0) {
            if (getBalance() + overdraftLimit >= amount) {
                double previousBalance = getBalance();
                super.withdraw(amount); // Call the superclass withdraw method
                if (getBalance() < 0) {
                    System.out.println("You are now in overdraft by: " + (0 - getBalance()));
                }
            } else {
                System.out.println("Withdrawal amount exceeds overdraft limit.");
            }
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    // Key Concept: Method Overriding
    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo(); // Call the superclass method
        System.out.println("Account Type: Checking");
        System.out.println("Overdraft Limit: " + overdraftLimit);
    }
}

public class BankSimulation {
    private static List<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Simulation!");

        while (true) {
            displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositFunds();
                    break;
                case 3:
                    withdrawFunds();
                    break;
                case 4:
                    displayAccountDetails();
                    break;
                case 5:
                    displayAllAccounts();
                    break;
                case 6:
                    applyInterestToSavings();
                    break;
                case 7:
                    System.out.println("Thank you for using the Bank Simulation. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Funds");
        System.out.println("3. Withdraw Funds");
        System.out.println("4. Display Account Details and History");
        System.out.println("5. Display All Accounts");
        System.out.println("6. Apply Interest (Savings Accounts Only)");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
            System.out.print("Enter your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        return choice;
    }

    private static void createAccount() {
        System.out.println("\n--- Create New Account ---");
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = getPositiveDoubleInput();
        System.out.print("Enter account type (1 for Savings, 2 for Checking): ");
        int typeChoice = getUserChoice();

        String accountNumber = generateAccountNumber();

        if (typeChoice == 1) {
            System.out.print("Enter interest rate (e.g., 2.5 for 2.5%): ");
            double interestRate = getPositiveDoubleInput();
            accounts.add(new SavingsAccount(accountNumber, name, initialBalance, interestRate));
        } else if (typeChoice == 2) {
            System.out.print("Enter overdraft limit: ");
            double overdraftLimit = getPositiveDoubleInput();
            accounts.add(new CheckingAccount(accountNumber, name, initialBalance, overdraftLimit));
        } else {
            System.out.println("Invalid account type. Account creation failed.");
        }
    }

    private static void depositFunds() {
        System.out.println("\n--- Deposit Funds ---");
        Account account = findAccount();
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = getPositiveDoubleInput();
            account.deposit(amount);
        }
    }

    private static void withdrawFunds() {
        System.out.println("\n--- Withdraw Funds ---");
        Account account = findAccount();
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = getPositiveDoubleInput();
            account.withdraw(amount);
        }
    }

    private static void displayAccountDetails() {
        System.out.println("\n--- Display Account Details and History ---");
        Account account = findAccount();
        if (account != null) {
            account.displayAccountInfo();
            account.displayTransactionHistory();
        }
    }

    private static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts created yet.");
            return;
        }
        System.out.println("\n--- All Bank Accounts ---");
        for (Account account : accounts) {
            account.displayAccountInfo();
        }
    }

    private static void applyInterestToSavings() {
        System.out.println("\n--- Apply Interest to Savings Accounts ---");
        boolean foundSavings = false;
        for (Account account : accounts) {
            if (account instanceof SavingsAccount) { // Check if it's a SavingsAccount
                ((SavingsAccount) account).applyInterest();
                foundSavings = true;
            }
        }
        if (!foundSavings) {
            System.out.println("No Savings Accounts found to apply interest to.");
        }
    }

    private static Account findAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("Account not found with number: " + accountNumber);
        return null;
    }

    private static String generateAccountNumber() {
        // Simple sequential account number generation
        return String.format("ACC%04d", accounts.size() + 1);
    }

    private static double getPositiveDoubleInput() {
        double value;
        while (true) {
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume the invalid input
                System.out.print("Enter amount: ");
            }
            value = scanner.nextDouble();
            scanner.nextLine(); // consume the newline character
            if (value > 0) {
                return value;
            } else {
                System.out.println("Amount must be positive. Please try again.");
                System.out.print("Enter amount: ");
            }
        }
    }
}