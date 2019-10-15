package com.luevar.exceptions;

public class PersonNotFoundException extends Exception {
    private Integer id;

    public PersonNotFoundException(Integer id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
