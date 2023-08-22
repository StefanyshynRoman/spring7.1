package com.shpp.rstefanyshyn.spring.repository;

import com.shpp.rstefanyshyn.spring.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface PersonRepository extends JpaRepository<Person,Long> {

        List<Person> findAll();
    }

