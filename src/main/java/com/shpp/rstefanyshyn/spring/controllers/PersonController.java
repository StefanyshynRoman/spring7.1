package com.shpp.rstefanyshyn.spring.controllers;

import com.shpp.rstefanyshyn.spring.config.aop.LogExecutionTime;
import com.shpp.rstefanyshyn.spring.exeption.InvalidPersonException;
import com.shpp.rstefanyshyn.spring.exeption.PersonNotFoundException;
import com.shpp.rstefanyshyn.spring.model.Person;
import com.shpp.rstefanyshyn.spring.repository.PersonRepository;
import com.shpp.rstefanyshyn.spring.services.PersonModelAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
public class PersonController {
    private final PersonRepository personRepository;
    private final Validator validator;
    private final PersonModelAssembler assembler;

    @Autowired
    public PersonController(PersonRepository personRepository, Validator validator, PersonModelAssembler assembler) {
        this.personRepository = personRepository;
        this.validator = validator;
        this.assembler = assembler;
    }

    @GetMapping("/persons")
    @LogExecutionTime
    public CollectionModel<EntityModel<Person>> all() {

        List<EntityModel<Person>> employees = personRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @GetMapping("/persons/{id}")
    @LogExecutionTime
    public EntityModel<Person> one(@PathVariable Long id) {

        Person person = personRepository.findById(id) //
                .orElseThrow(() -> new PersonNotFoundException(id));

        return assembler.toModel(person);
    }


    @PostMapping("/persons")
    @LogExecutionTime
    Person newEmployee(@RequestBody Person newPerson) {
        isValidate(newPerson);
        return personRepository.save(newPerson);
    }
    //    @PutMapping("/tasks/{id}")
//    public ResponseEntity<Task> replaceEmployee(@RequestBody Task newTask, @PathVariable Long id) {
//
//
//        return taskRepository.findById(id)
//                .map(task -> {
//                    task.setTaskDescription(newTask.getTaskDescription());
//                    task.setTargetDate(newTask.getTargetDate());
//                    task.setStatus(newTask.getStatus());
//                    Task updatedTask = taskRepository.save(task);
//                    return ResponseEntity.ok(updatedTask);
//                })
//                .orElseGet(() -> {
//                    newTask.setId(id);
//                    Task createdTask = taskRepository.save(newTask);
//                    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
//                });
//    }

    @PutMapping("/persons/{id}")
    @LogExecutionTime
    public ResponseEntity<Person> replaceEmployee(@RequestBody Person newPerson, @PathVariable Long id) {
        isValidate(newPerson);

        return personRepository.findById(id)
                .map(person -> {
                    person.setName(newPerson.getName());
                    person.setDateOfBirth(newPerson.getDateOfBirth());
                    person.setGender(newPerson.getGender());
                    person.setIpn(newPerson.getIpn());
                    Person updatedPerson = personRepository.save(person);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    Person createdPerson = personRepository.save(newPerson);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
                });
    }


    @DeleteMapping("/persons/{id}")
    @LogExecutionTime
    void deletePerson(@PathVariable Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    private void isValidate(@RequestBody Person newPerson) {
        Set<ConstraintViolation<Person>> violations = validator.validate(newPerson);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            throw new InvalidPersonException("Invalid Person data: " + errorMessage);
        }
    }
}
