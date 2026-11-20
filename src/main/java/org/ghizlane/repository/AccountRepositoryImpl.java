package org.ghizlane.repository;

import org.ghizlane.model.*;

import java.util.*;
import java.util.function.Predicate;

public class AccountRepositoryImpl
        implements AccountRepository {

    // DESIGN PATTERN SINGLETON
    private static final AccountRepositoryImpl INSTANCE;

    static {
        System.out.println(
                "Initialisation du singleton AccountRepositoryImpl"
        );

        INSTANCE = new AccountRepositoryImpl();
    }

    private AccountRepositoryImpl() {
    }

    public static AccountRepositoryImpl getInstance() {
        return INSTANCE;
    }

    // Stockage des comptes en mémoire
    private final Map<Long, BankAccount> accounts =
            new HashMap<>();

    private long counter = 0;

    // Ajouter un compte
    @Override
    public synchronized BankAccount save(
            BankAccount account) {

        if (account == null) {
            throw new IllegalArgumentException(
                    "Compte obligatoire"
            );
        }

        account.setAccountId(++counter);

        accounts.put(
                account.getAccountId(),
                account
        );

        return account;
    }

    // Afficher tous les comptes
    @Override
    public synchronized List<BankAccount> findAll() {
        return new ArrayList<>(accounts.values());
    }

    // Chercher un compte par ID
    @Override
    public synchronized Optional<BankAccount> findById(
            Long id) {

        return Optional.ofNullable(accounts.get(id));
    }

    // Recherche multicritère
    @Override
    public synchronized List<BankAccount> search(
            Predicate<BankAccount> predicate) {

        return accounts.values()
                .stream()
                .filter(predicate)
                .toList();
    }

    // Modifier un compte
    @Override
    public synchronized BankAccount update(
            BankAccount account) {

        if (account == null
                || account.getAccountId() == null
                || !accounts.containsKey(account.getAccountId())) {

            throw new IllegalArgumentException(
                    "Compte introuvable"
            );
        }

        accounts.put(
                account.getAccountId(),
                account
        );

        return account;
    }

    // Supprimer un compte
    @Override
    public synchronized void deleteById(Long id) {
        accounts.remove(id);
    }

    // Génération de 10 comptes aléatoires
    @Override
    public void populateData() {

        for (int i = 0; i < 10; i++) {

            BankAccount account =
                    new BankDirector()
                            .accountBuilder()
                            .balance(
                                    10000 + Math.random() * 90000
                            )
                            .currency(
                                    Math.random() > 0.5
                                            ? "MAD" : "USD"
                            )
                            .type(
                                    Math.random() > 0.5
                                            ? AccountType.CURRENT_ACCOUNT
                                            : AccountType.SAVING_ACCOUNT
                            )
                            .status(
                                    Math.random() > 0.5
                                            ? AccountStatus.CREATED
                                            : AccountStatus.ACTIVATED
                            )
                            .build();

            save(account);
        }
    }

    public synchronized int count() {
        return accounts.size();
    }
}
