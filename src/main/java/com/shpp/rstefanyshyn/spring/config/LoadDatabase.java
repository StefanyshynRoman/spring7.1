package com.shpp.rstefanyshyn.spring.config;


import com.shpp.rstefanyshyn.spring.model.Person;
import com.shpp.rstefanyshyn.spring.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Person( "Roman", LocalDate.of(1988, 5, 15),"male","3227707737")));
            log.info("Preloading " + repository.save(new Person("Marina",LocalDate.of(1989, 2, 23),"female","3256120341")));
        };
    }
}