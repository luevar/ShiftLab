package com.luevar.models;

/**
 * Класс, описывающий пользователя
 */
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Basket basket;

    public Person() {
        this.basket = new Basket();
    }

    public Person(String firstName, String lastName, String patronymic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.basket = new Basket();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Basket getBasket() {
        return basket;
    }
}
