package dev.account;


import dev.user.User;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money_amount")
    private int moneyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Account(Long id, int moneyAmount, User user) {
        this.id = id;
        this.moneyAmount = moneyAmount;
        this.user = user;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
