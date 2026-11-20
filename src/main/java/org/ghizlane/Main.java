
package org.ghizlane;

import org.ghizlane.model.*;
import org.ghizlane.repository.*;
import org.ghizlane.threads.*;
import org.ghizlane.util.JsonUtil;

import java.util.List;

public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        // 1. BUILDER
        System.out.println(
                "=== 1. Builder : 3 façons de créer un compte ==="
        );

        BankAccount withSetters = new BankAccount();
        withSetters.setBalance(1000);

        BankAccount withConstructor =
                new BankAccount(
                        null,
                        2000,
                        "MAD",
                        AccountType.CURRENT_ACCOUNT,
                        AccountStatus.CREATED
                );

        BankAccount withBuilder =
                BankAccount.builder()
                        .balance(3000)
                        .currency("MAD")
                        .type(AccountType.SAVING_ACCOUNT)
                        .status(AccountStatus.ACTIVATED)
                        .build();

        System.out.println(withSetters);
        System.out.println(withConstructor);
        System.out.println(withBuilder);

        System.out.println(
                "Director : " +
                        new BankDirector().savingAccount()
        );

        // 2. SINGLETON + REPOSITORY
        System.out.println(
                "\n=== 2. Singleton + repository + JSON + recherche ==="
        );

        AccountRepositoryImpl repo =
                AccountRepositoryImpl.getInstance();

        repo.populateData();

        System.out.println(
                "Même instance ? " +
                        (repo == AccountRepositoryImpl.getInstance())
        );

        System.out.println(
                "Nombre de comptes : " + repo.count()
        );

        System.out.println(
                "Compte 1 : " +
                        repo.findById(1L)
                                .map(JsonUtil::toJson)
                                .orElse("Absent")
        );

        // Recherche avec Predicate et lambda
        List<BankAccount> currentActive =
                repo.search(a ->
                        a.getType() == AccountType.CURRENT_ACCOUNT
                                && a.getStatus() == AccountStatus.ACTIVATED
                );

        System.out.println(
                "Comptes courants activés : " +
                        JsonUtil.toJson(currentActive)
        );

        // Modifier le premier compte
        BankAccount first =
                repo.findById(1L).orElseThrow();

        first.setStatus(AccountStatus.SUSPENDED);

        repo.update(first);

        System.out.println(
                "Après update : " +
                        repo.findById(1L).orElseThrow()
        );

        // 3. PROTOTYPE
        System.out.println(
                "\n=== 3. Prototype : clone profond vs référence ==="
        );

        BankAccount original =
                BankAccount.builder()
                        .balance(9000)
                        .currency("MAD")
                        .customer(
                                new Customer(1L, "Salma")
                        )
                        .build();

        BankAccount copy = original.clone();

        BankAccount alias = original;

        original.getCustomer().setName("Nom modifié");
        original.setBalance(8888);

        System.out.println("Original : " + original);
        System.out.println("Clone    : " + copy);

        System.out.println(
                "Alias == original ? " +
                        (alias == original)
        );

        System.out.println(
                "Client partagé ? " +
                        (original.getCustomer() == copy.getCustomer())
        );

        // 4. THREADS
        System.out.println(
                "\n=== 4. Threads : Thread, Runnable et lambda ==="
        );

        Thread t1 = new TaskImpl1();

        Thread t2 = new Thread(new TaskImpl2());

        Thread t3 = new Thread(() ->
                System.out.println(
                        "Lambda : " +
                                Thread.currentThread().getName()
                )
        );

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // 5. SYNCHRONISATION
        System.out.println(
                "\n=== 5. 10 threads modifient le même singleton ==="
        );

        Thread[] workers = new Thread[10];

        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(repo::populateData);
            workers[i].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println(
                "Nombre attendu = 110, obtenu = " +
                        repo.count()
        );
    }
}
