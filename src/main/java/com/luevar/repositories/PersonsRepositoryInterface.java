package com.luevar.repositories;

import com.luevar.exceptions.EmptyFileException;
import com.luevar.exceptions.PersonNotFoundException;
import com.luevar.models.Person;
import com.luevar.models.Product;

import java.io.IOException;
import java.util.Collection;

public interface PersonsRepositoryInterface {

    void updateBasket(Product product, Integer personId) throws IOException, EmptyFileException, PersonNotFoundException;

    Collection<Product> provideBasketContent(Integer personId) throws IOException, EmptyFileException, PersonNotFoundException;

    int addPerson(Person person) throws IOException;
}