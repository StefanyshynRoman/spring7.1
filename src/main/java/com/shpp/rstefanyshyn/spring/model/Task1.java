package com.shpp.rstefanyshyn.spring.model;

import com.shpp.rstefanyshyn.spring.statemachine.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import java.time.LocalDate;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Task1 {

    @javax.persistence.Id
    @Id
    @GeneratedValue
    private  Long id;
    private LocalDate localDate;
    private String state;

    @Transient
    String event;

    @Transient
    String extendedState;

    public Task1(String s, LocalDate of, Status status) {
    }

    //    public Task1(String taskDescription, LocalDate targetDate, Status status) {
//
//        this.taskDescription = taskDescription;
//        this.targetDate = targetDate;
//        this.status = status;
//    }
//    public Task1(String taskDescription, LocalDate targetDate) {
//
//        this.taskDescription = taskDescription;
//        this.targetDate = targetDate;
//        this.status = Sta
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}