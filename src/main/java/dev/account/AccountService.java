package dev.account;

import dev.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class AccountService {

    private final Map<Integer, Account> accounts;

    private int id;

    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
        this.accounts = new HashMap<>();
        this.id = 0;
    }

    public Account createAccount(User user) {
        id++;
        Account account = new Account(id, user.getId(), accountProperties.getDefaultAccountAmount());
        accounts.put(account.getId(), account);
        return account;
    }

    public Optional<Account> getAccount(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public List<Account> getAllAccounts(int userId) {
        return accounts.values().stream().filter(account -> account.getUserId()==userId)
                .toList();
    }

    public void deposit(int id, int sum) {
        var account = getAccount(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if(sum <=0) {
            throw new IllegalArgumentException("Invalid sum");
        }
        account.setMoneyAmount(account.getMoneyAmount()+sum);
        System.out.println("Deposited " + sum + " to account " + id);
    }

    public void withdraw(int id, int sum) {
        var account = getAccount(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if(sum > account.getMoneyAmount()) {
            throw new IllegalArgumentException("Invalid sum");
        }
        account.setMoneyAmount(account.getMoneyAmount()-sum);
        System.out.println("Withdrawn " + sum + " to account " + id);
    }

    public Account close(int id) {
        var accountToRemove = getAccount(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        List<Account> accountList = getAllAccounts(accountToRemove.getUserId());
        if(accountList.size() == 1 ) {
            throw new IllegalArgumentException("You have only one account or dont have any accounts");
        }
        Account accountToDeposit = accountList.stream().filter(it -> it.getId() != id)
                .findFirst().orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
        accounts.remove(id);
        System.out.println("Account " + id + " has been closed");
        return accountToRemove;
    }

    public void transfer(int fromId, int toId, int startAmount) {
        var accountFrom = getAccount(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        var accountTo = getAccount(toId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if(startAmount > accountFrom.getMoneyAmount()) {
            throw new IllegalArgumentException("You have not enough money to transfer");
        }
        if(startAmount <=0) {
            throw new IllegalArgumentException("Sum less or equal to zero");
        }

        int amountWithCommision = accountFrom.getUserId() != accountTo.getUserId()
                ? (int)(startAmount - startAmount * accountProperties.getTransferCommission())
                : startAmount;
        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - startAmount);
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amountWithCommision);

    }


}
