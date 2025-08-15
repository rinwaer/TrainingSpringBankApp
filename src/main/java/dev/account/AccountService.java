package dev.account;

import dev.helpers.TransactionHelper;
import dev.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AccountService {

    private final AccountProperties accountProperties;

    private final TransactionHelper transactionHelper;

    private final SessionFactory sessionFactory;

    public AccountService(AccountProperties accountProperties, TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.accountProperties = accountProperties;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Account createAccount(User user) {
        return transactionHelper.executeInTransaction(() -> {
            var session = sessionFactory.getCurrentSession();
            Account newAccount = new Account(
                    null,
                    accountProperties.getDefaultAccountAmount(),
                    user
            );
            session.persist(newAccount);
            return newAccount;
        });
    }

    private Optional<Account> getAccount(Long id) {
        var account = sessionFactory.getCurrentSession()
                .get(Account.class, id);
        return Optional.of(account);
    }

    public void deposit(Long id, int sum) {
        transactionHelper.executeInTransaction(() -> {
            var account = getAccount(id)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));
            if (sum <= 0) {
                throw new IllegalArgumentException("Invalid sum");
            }

            account.setMoneyAmount(account.getMoneyAmount() + sum);
            return 0;
        });
        System.out.println("Deposited " + sum + " to account " + id);
    }

    public void withdraw(Long id, int sum) {
        transactionHelper.executeInTransaction(() -> {
            var account = getAccount(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
            if (sum > account.getMoneyAmount()) {
                throw new IllegalArgumentException("Invalid sum");
            }
            if (sum <= 0) {
                throw new IllegalArgumentException("Invalid sum");
            }
            account.setMoneyAmount(account.getMoneyAmount() - sum);
            return 0;
        });

        System.out.println("Withdrawn " + sum + " to account " + id);
    }

    public Account close(Long id) {
        return transactionHelper.executeInTransaction(() -> {
            var accountToRemove = getAccount(id)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));

            var accountList = accountToRemove.getUser().getAccountList();

            if (accountList.size() == 1) {
                throw new IllegalArgumentException("You have only one account");
            }

            Account accountToDeposit = accountList.stream()
                    .filter(it -> !Objects.equals(it.getId(), id))
                    .findFirst()
                    .orElseThrow();

            accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
            sessionFactory.getCurrentSession().remove(accountToRemove);
            System.out.println("Account " + id + " has been closed");
            return accountToRemove;
        });
    }


    public void transfer(Long fromId, Long toId, int startAmount) {
        if (startAmount <= 0) {
            throw new IllegalArgumentException("Sum less or equal to zero");
        }
        transactionHelper.executeInTransaction(() -> {
            var accountFrom = getAccount(fromId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));
            var accountTo = getAccount(toId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));
            if (startAmount > accountFrom.getMoneyAmount()) {
                throw new IllegalArgumentException("You have not enough money to transfer");
            }
            int amountWithCommision = !accountFrom.getUser().getId().equals(accountTo.getUser().getId())
                    ? (int) (startAmount - startAmount * accountProperties.getTransferCommission())
                    : startAmount;
            accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - startAmount);
            accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amountWithCommision);
            return 0;
        });
    }
}
