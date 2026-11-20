package org.ghizlane.model;

public class BankDirector {

    public BankAccount.AccountBuilder accountBuilder() {
        return BankAccount.builder();
    }

    public BankAccount currentAccount() {
        return accountBuilder()
                .type(AccountType.CURRENT_ACCOUNT)
                .currency("MAD")
                .status(AccountStatus.CREATED)
                .build();
    }

    public BankAccount savingAccount() {
        return accountBuilder()
                .type(AccountType.SAVING_ACCOUNT)
                .currency("MAD")
                .status(AccountStatus.ACTIVATED)
                .build();
    }
}
