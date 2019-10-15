package com.luevar.services;

import com.luevar.exceptions.ApplicationException;
import com.luevar.exceptions.EmptyFileException;
import com.luevar.models.Person;
import com.luevar.models.Product;

import java.io.IOException;
import java.util.List;

public interface PersonsServiceInterface {

    void updateBasket(String productLine, Integer personId) throws ApplicationException, EmptyFileException;

    List<Product> provideBasketContent(Integer personId) throws ApplicationException, EmptyFileException;

    String providePriceList();

    int addPerson(Person person) throws ApplicationException;
}
