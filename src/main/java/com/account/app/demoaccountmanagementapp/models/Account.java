package com.account.app.demoaccountmanagementapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "status")
    private boolean status; // true for active, false for blocked

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    public Account() {}

    public Account(double balance, boolean status, Person owner) {
        this.balance = balance;
        this.status = status;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", status=" + status +
                ", owner=" + owner +
                '}';
    }
}