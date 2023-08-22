package com.shpp.rstefanyshyn.spring.model;

import com.shpp.rstefanyshyn.spring.statemachine.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
@ToString
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Task description must not be empty")
    @Column()
    private String taskDescription;
    @NotNull
    //@Future(message = "Date must be in future")
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
                "Task description ='" + taskDescription + '\'' +
                ", Date of finish = " + targetDate +
                ", Status ='" + state +
                '}';

    }

}