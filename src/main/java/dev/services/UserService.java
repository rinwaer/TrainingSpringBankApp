package dev.services;

import dev.model.User;

import java.util.*;


public class UserService {

    private final Map<Integer, User> users;

    private int id;

    private final Set<String> takenLogins;

    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.id = id;
        this.users = new HashMap<>();
        this.takenLogins = new HashSet<>();
        this.accountService = accountService;
    }

    public User createUser(String login) {

        if(takenLogins.contains(login)) {
            throw new IllegalArgumentException("User with login %s already exists"
                    .formatted(login));
        }
        takenLogins.add(login);


        id++;
        var newUser = new User(id, login, new ArrayList<>());

        var newAccount = accountService.createAccount(newUser);
        newUser.getAccounts().add(newAccount);

        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> findUser(int id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> getAllUsers() {
        return users.values().stream().toList();

    }


}
