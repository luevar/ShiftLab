package com.luevar.repositories;

import com.luevar.exceptions.ApplicationException;
import com.luevar.exceptions.EmptyFileException;
import com.luevar.models.Person;
import com.luevar.models.Product;

import java.io.IOException;
import java.util.List;

public interface PersonsRepositoryInterface {

    void updateBasket(Product product, Integer personId) throws ApplicationException, EmptyFileException;

    List<Product> provideBasketContent(Integer personId) throws ApplicationException, EmptyFileException;

    int addPerson(Person person) throws ApplicationException;
}
