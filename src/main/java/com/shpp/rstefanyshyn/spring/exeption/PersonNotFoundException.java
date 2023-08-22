package com.shpp.rstefanyshyn.spring.exeption;

    public class PersonNotFoundException extends RuntimeException {

        public PersonNotFoundException(Long id) {
            super("Could not find person " + id);
        }
    }

