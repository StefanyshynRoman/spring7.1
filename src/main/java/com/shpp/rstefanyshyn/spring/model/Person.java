package com.shpp.rstefanyshyn.spring.model;

import com.shpp.rstefanyshyn.spring.services.IpnValidator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
@ToString
@IpnValidator
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @NotEmpty(message = "Name must not be empty")
    @Column()
    private String name;

    @NotNull
    @Past(message = "Date of birth must be in past")
    @Column()
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Gender must not be empty")
    @Pattern(regexp = "^(male|female)$")
    @Column()
    private String gender;

    @NotEmpty(message = "Ipn cannot be empty")
    @Column()
    private String ipn;

    public Person(String name, LocalDate dateOfBirth, String gender, String ipn) {

        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.ipn = ipn;
    }

    @Override
    public String toString() {
        return "Person {" +
                "Name ='" + name + '\'' +
                ", Date of birth = " + dateOfBirth +
                ", Gender ='" + gender + '\'' +
                ",IPN=" + ipn +
                '}';

    }
}
