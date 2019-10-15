package com.luevar.services;

import com.luevar.exceptions.EmptyFileException;
import com.luevar.exceptions.ItemNotFoundException;
import com.luevar.exceptions.PersonNotFoundException;
import com.luevar.exceptions.WrongInputException;
import com.luevar.models.Person;
import com.luevar.models.Product;

import java.io.IOException;
import java.util.List;

public interface PersonsServiceInterface {

    void updateBasket(String productLine, Integer personId) throws EmptyFileException, PersonNotFoundException, IOException, ItemNotFoundException, WrongInputException;

    List<Product> provideBasketContent(Integer personId) throws EmptyFileException, PersonNotFoundException, IOException;

    String providePriceList();

    int addPerson(Person person) throws IOException;
}
