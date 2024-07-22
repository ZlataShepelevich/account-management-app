package com.account.app.demoaccountmanagementapp.dao;

import com.account.app.demoaccountmanagementapp.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<Person> getByLogin(String login) {
        System.out.println("login " + login);
        Person person = entityManager.createQuery("FROM Person p WHERE p.login = :login", Person.class)
                .setParameter("login", login)
                .getSingleResult();
        return Optional.ofNullable(person);
    }
}
