package com.shpp.rstefanyshyn.spring.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.shpp.rstefanyshyn.spring.controllers.PersonController;
import com.shpp.rstefanyshyn.spring.model.Person;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PersonModelAssembler
        implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person person) {

        return EntityModel.of(person, //
                linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("employees"));
    }
}