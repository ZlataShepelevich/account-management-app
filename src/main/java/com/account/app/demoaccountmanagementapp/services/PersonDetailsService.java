package com.account.app.demoaccountmanagementapp.services;

import com.account.app.demoaccountmanagementapp.dao.PersonDAO;
import com.account.app.demoaccountmanagementapp.models.Person;
import com.account.app.demoaccountmanagementapp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonDAO personDAO;

    @Autowired
    public PersonDetailsService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personDAO.getByLogin(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetails(person.get());
    }
}
