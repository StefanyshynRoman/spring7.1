package com.shpp.rstefanyshyn.spring.services;

import com.shpp.rstefanyshyn.spring.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class MyValidator implements ConstraintValidator<IpnValidator, Person> {
    private static final Logger logger = LogManager.getLogger(MyValidator.class);
    Person person;

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext context) {
        this.person = person;
        return isIPNSizeCorrect(person) && isDateOfBirthCorrect(person) && isGenderCorrect(person) && isCheckingDigitCorrect(person.getIpn());
    }

     boolean isCheckingDigitCorrect(String ipn) {

        int sum =
                Character.getNumericValue(ipn.charAt(0)) * (-1) +
                        Character.getNumericValue(ipn.charAt(1)) * 5 +
                        Character.getNumericValue(ipn.charAt(2)) * 7 +
                        Character.getNumericValue(ipn.charAt(3)) * 9 +
                        Character.getNumericValue(ipn.charAt(4)) * 4 +
                        Character.getNumericValue(ipn.charAt(5)) * 6 +
                        Character.getNumericValue(ipn.charAt(6)) * 10 +
                        Character.getNumericValue(ipn.charAt(7)) * 5 +
                        Character.getNumericValue(ipn.charAt(8)) * 7;

        int controlDigit = Character.getNumericValue(ipn.charAt(9));

        int remainder = sum % 11;
        int expectedControlDigit = remainder == 10 ? 0 : remainder;
         logger.info("Control digit is {} ",controlDigit == expectedControlDigit);
        return controlDigit == expectedControlDigit;
    }

     boolean isGenderCorrect(Person person) {
        if (person.getGender().equals("male")) {

            return person.getIpn().charAt(8) % 2 != 0;

        }
        if (person.getGender().equals("female")) {
            return person.getIpn().charAt(8) % 2 == 0;
        } else {
            logger.info("Gender is bad - {}", person.getGender());
            return false;

        }
    }

     boolean isDateOfBirthCorrect(Person person) {
        long days = LocalDate.of(1899, 12, 31).until(person.getDateOfBirth(), ChronoUnit.DAYS);
        StringBuilder daysFromIpn = new StringBuilder();
        daysFromIpn.append(String.valueOf(person.getIpn()), 0, 5);

        logger.info("Amounts days from ipn {}", daysFromIpn);
        logger.info("Amounts correct days {}", days);

        return days == Integer.parseInt(daysFromIpn.toString());
    }

     boolean isIPNSizeCorrect(Person person) {
        if (isNumber(person.getIpn())) {
            long ipn = parseLong(person.getIpn());
            return ipn >= 1000000000L && ipn <= 9999999999L;
        } else {
            logger.info("ipn size is not correct  {}", person.getIpn());
            return false;
        }
    }

    boolean isNumber(String ipn) {
        logger.info("Ipn number - {}", Pattern.matches("^[0-9]+$", ipn));
        return Pattern.matches("^[0-9]+$", ipn);
    }
}