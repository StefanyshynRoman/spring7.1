package com.shpp.rstefanyshyn.spring.config;

import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import static com.shpp.rstefanyshyn.spring.statemachine.Status.PLANNED;

@Configuration
class LoadTaskToDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadTaskToDatabase.class);

    @Bean
    CommandLineRunner initTaskDatabase(TaskRepository taskRepository) {

        return args -> {
            log.info("Preloadingw " + taskRepository.save(new Task("Lessons 6", LocalDate.of(2989, 2, 23), PLANNED)));

            log.info("Preloadingw " + taskRepository.save(new Task("Lessons Spring", LocalDate.of(2039, 2, 23), PLANNED)));
            log.info("Preloading " + taskRepository.save(new Task("Lessons English", LocalDate.of(2032, 1, 1))));
        };
    }
}