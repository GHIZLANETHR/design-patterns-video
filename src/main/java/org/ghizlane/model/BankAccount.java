package org.ghizlane.model;

public class BankAccount implements Cloneable {

    private Long accountId;
    private double balance;
    private String currency;
    private AccountType type;
    private AccountStatus status;
    private Customer customer;

    // Constructeur sans paramètres
    public BankAccount() {
    }

    // Constructeur avec paramètres
    public BankAccount(Long accountId,
                       double balance,
                       String currency,
                       AccountType type,
                       AccountStatus status) {
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
        this.type = type;
        this.status = status;
    }

    // Getters et setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // DESIGN PATTERN BUILDER
    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public static class AccountBuilder {

        private final BankAccount account =
                new BankAccount();

        public AccountBuilder accountId(Long id) {
            account.accountId = id;
            return this;
        }

        public AccountBuilder balance(double balance) {
            account.balance = balance;
            return this;
        }

        public AccountBuilder currency(String currency) {
            account.currency = currency;
            return this;
        }

        public AccountBuilder type(AccountType type) {
            account.type = type;
            return this;
        }

        public AccountBuilder status(AccountStatus status) {
            account.status = status;
            return this;
        }

        public AccountBuilder customer(Customer customer) {
            account.customer = customer;
            return this;
        }

        public BankAccount build() {
            return account;
        }
    }

    // DESIGN PATTERN PROTOTYPE
    @Override
    public BankAccount clone() {
        try {
            BankAccount copy =
                    (BankAccount) super.clone();

            if (customer != null) {
                copy.customer = customer.clone();
            }

            return copy;

        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", customer=" + customer +
                '}';
    }
}

