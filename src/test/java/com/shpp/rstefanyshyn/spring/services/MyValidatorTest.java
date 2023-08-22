package com.shpp.rstefanyshyn.spring.services;

import com.shpp.rstefanyshyn.spring.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyValidatorTest {
    private static final Logger logger = LogManager.getLogger(MyValidatorTest.class);

    MyValidator myValidator = new MyValidator();
    Person person;

    @BeforeEach
    void setUp() {
        person = new Person("Roman",
                LocalDate.of(1988, 5, 15),
                "male",
                "3227707737");

    }

    @Test
    void isValid() {
        ConstraintValidatorContext context = null;
        boolean result = myValidator.isValid(person, context);
        assertTrue(result);

        Person person1 = new Person("Marina",
                LocalDate.of(1989, 2, 23),
                "male", "3256120341");
        boolean result1 = myValidator.isValid(person1, context);
        assertFalse(result1);
    }

    @Test
    void isDateOfBirthCorrectTest() {
        MyValidator myValidator = new MyValidator();
        Person person1 = new Person();
        person1.setDateOfBirth(LocalDate.of(1990, 10, 15));
        person1.setIpn("33160");
        boolean result = myValidator.isDateOfBirthCorrect(person1);
        assertTrue(result);
        Person person2 = new Person();
        person2.setDateOfBirth(LocalDate.of(1990, 10, 15));
        person2.setIpn("33161");
        boolean result2 = myValidator.isDateOfBirthCorrect(person2);
        assertFalse(result2);
        boolean result1 = myValidator.isDateOfBirthCorrect(person);
        assertTrue(result1);
    }

    @Test
    void isNumber() {
        assertTrue(myValidator.isNumber("3227707737"));
        assertTrue(myValidator.isNumber("12345"));
        assertFalse(myValidator.isNumber("hello"));
        assertFalse(myValidator.isNumber("12abc"));
    }

    @Test
    void isIPNSizeCorrectTest() {
        Person person1 = new Person();

        person1.setIpn("32277");
        boolean result = myValidator.isIPNSizeCorrect(person);
        assertTrue(result);
        boolean result1 = myValidator.isIPNSizeCorrect(person1);
        assertFalse(result1);
    }

    @Test
    void isGenderCorrectTest() {
        Person person1 = new Person();
        person1.setIpn("3256120341");
        person1.setGender("female");
        boolean result = myValidator.isGenderCorrect(person1);
        assertTrue(result);
        boolean result1 = myValidator.isGenderCorrect(person);
        assertTrue(result1);

        Person person2 = new Person();
        person2.setIpn("3256120341");
        person2.setGender("ledi");
        boolean result2 = myValidator.isGenderCorrect(person2);
        assertFalse(result2);

    }

    @Test
    void isCheckingDigitCorrectTest() {
        boolean result1 = myValidator.isCheckingDigitCorrect("3227707737");
        boolean result2 = myValidator.isCheckingDigitCorrect("3227707733");
        assertTrue(myValidator.isCheckingDigitCorrect("3256120341"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120342"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120343"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120344"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120345"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120346"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120347"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120348"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120349"));
        assertFalse(myValidator.isCheckingDigitCorrect("3256120340"));
        assertTrue(result1);
        assertFalse(result2);
    }
}