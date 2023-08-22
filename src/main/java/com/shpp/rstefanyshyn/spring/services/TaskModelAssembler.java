package com.shpp.rstefanyshyn.spring.services;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.shpp.rstefanyshyn.spring.controllers.PersonController;
import com.shpp.rstefanyshyn.spring.controllers.TaskController;
import com.shpp.rstefanyshyn.spring.model.Task;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



@Component
public class TaskModelAssembler
   implements RepresentationModelAssembler<Task, EntityModel<Task>>{



        @Override
        public EntityModel<Task> toModel(Task task) {

        return EntityModel.of(task, //
                linkTo(methodOn(TaskController.class).one(task.getId())).withSelfRel(),
                linkTo(methodOn(TaskController.class).all()).withRel("todos"));
    }
    }