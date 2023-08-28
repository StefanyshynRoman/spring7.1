package com.shpp.rstefanyshyn.spring.model;

import com.shpp.rstefanyshyn.spring.exeption.InvalidDataException;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Task description must not be empty")
    @Column()
    private String taskDescription;

    public void setTargetDate(LocalDate targetDate) throws InvalidDataException {
        this.targetDate = targetDate;
    }

    @NotNull
    @Column()
    private LocalDate targetDate;

    @Column()
    private String state;

    public Task(String taskDescription, LocalDate targetDate, Status status) {

        this.taskDescription = taskDescription;
        this.targetDate = targetDate;
        this.state = String.valueOf(status);
    }

    public Task(String taskDescription, LocalDate targetDate) {

        this.taskDescription = taskDescription;
        this.targetDate = targetDate;
        this.state = String.valueOf(Status.PLANNED);
    }



    @Transient
    String event;

    @Transient
    String extendedState;

    @Override
    public String toString() {
        return "Task {" +
                "id = '" + id + '\'' +
                ", Task description ='" + taskDescription + '\'' +
                ", Date of finish = " + targetDate +
                ", Status ='" + state + '\'' +
                '}';

    }

}