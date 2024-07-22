package com.account.app.demoaccountmanagementapp.services;

import com.account.app.demoaccountmanagementapp.dao.AccountDAO;
import com.account.app.demoaccountmanagementapp.dao.PersonDAO;
import com.account.app.demoaccountmanagementapp.models.Account;
import com.account.app.demoaccountmanagementapp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    private final PersonDAO personDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO, PersonDAO personDAO) {
        this.accountDAO = accountDAO;
        this.personDAO = personDAO;
    }

    public Account getAccountByOwner(Person owner) throws Exception {
        Account acc = accountDAO.findByOwner(owner).orElseThrow(() -> new Exception("Account not found"));
        return acc;
    }

    @Transactional
    public void deposit(int accountId, double amount) {
        Account account = accountDAO.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.isStatus()) {
            account.setBalance(account.getBalance() + amount);
            accountDAO.save(account);
        } else {
            throw new RuntimeException("Счет заблокирован");
        }
    }

    @Transactional
    public void withdraw(int accountId, double amount) {
        Account account = accountDAO.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.isStatus()) {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                accountDAO.save(account);
            } else {
                throw new RuntimeException("Недостаточно средств");
            }
        } else {
            throw new RuntimeException("Счет заблокирован");
        }
    }

    @Transactional
    public void blockAccount(int accountId) {
        Account account = accountDAO.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(false);
        accountDAO.save(account);
    }

    @Transactional
    public void unblockAccount(int accountId) {
        Account account = accountDAO.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(true);
        accountDAO.save(account);
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByUsername(String username) {
        Optional<Person> person = personDAO.getByLogin(username);
        return accountDAO.findByOwner(person.get());
    }

    public Optional<Account> findByIdAcc(int accountId) {
        return accountDAO.findById(accountId);
    }

    public List<Account> findAllAccounts() {
        return accountDAO.findAllUsers();
    }
}
