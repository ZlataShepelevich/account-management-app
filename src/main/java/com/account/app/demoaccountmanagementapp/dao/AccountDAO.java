package com.account.app.demoaccountmanagementapp.dao;

import com.account.app.demoaccountmanagementapp.models.Account;
import com.account.app.demoaccountmanagementapp.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<Account> findById(int accountId) {
        Account account = entityManager.find(Account.class, accountId);
        return Optional.ofNullable(account);
    }

    @Transactional
    public void save(Account account) {
        if (account.getId() == 0) {
            entityManager.persist(account);
        } else {
            entityManager.merge(account);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByOwner(Person owner) {
        TypedQuery<Account> query = entityManager.createQuery(
                "SELECT a FROM Account a WHERE a.owner = :owner", Account.class);
        query.setParameter("owner", owner);
        Account account = query.getSingleResult();
        return Optional.ofNullable(account);
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByIdAcc(int id) {
        TypedQuery<Account> query = entityManager.createQuery(
                "SELECT a FROM Account a WHERE a.id = :id", Account.class);
        query.setParameter("id", id);
        Account account = query.getSingleResult();
        return Optional.ofNullable(account);
    }

    public List<Account> findAllUsers() {
        String sql = "SELECT a.* FROM Account a JOIN Person p ON a.person_id = p.id WHERE p.role = 'ROLE_USER'";
        Query query = entityManager.createNativeQuery(sql, Account.class);
        return query.getResultList();
    }
}
