package com.shpp.rstefanyshyn.spring.config;

import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.model.Task1;
import com.shpp.rstefanyshyn.spring.repository.Task1Repository;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import static com.shpp.rstefanyshyn.spring.statemachine.Status.PLANNED;

@Configuration
class LoadToTask1 {

    private static final Logger log = LoggerFactory.getLogger(LoadToTask1.class);
//
//    @Bean
//    CommandLineRunner initTaskDatabase(Task1Repository task1Repository) {
//
//        return args -> {
//            log.info("Preloading " + task1Repository.save(new Task1("Lessons 6", LocalDate.of(2989, 2, 23), PLANNED)));
//
//            log.info("Preloading " + task1Repository.save(new Task1("Lessons Spring", LocalDate.of(2039, 2, 23), PLANNED)));
//          //  log.info("Preloading " + task1Repository.save(new Task1("Lessons English", LocalDate.of(2032, 1, 1))));
//        };
  //  }
}