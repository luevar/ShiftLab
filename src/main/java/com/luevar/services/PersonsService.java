package com.luevar.services;

import com.luevar.exceptions.EmptyFileException;
import com.luevar.exceptions.ItemNotFoundException;
import com.luevar.exceptions.PersonNotFoundException;
import com.luevar.exceptions.WrongInputException;
import com.luevar.models.Person;
import com.luevar.models.PriceList;
import com.luevar.models.Product;
import com.luevar.repositories.PersonsRepositoryInterface;

import java.io.IOException;
import java.util.Collection;

//Принимает запросы из контроллера и проверяет их на отсутствие ошибочных входных данных
public class PersonsService implements PersonsServiceInterface {

    private final PersonsRepositoryInterface repository;
    private PriceList priceList;

    public PersonsService(PersonsRepositoryInterface productsRepository) {
        this.repository = productsRepository;
        priceList = new PriceList();
    }

    //Добавление продукта в корзину по id пользователя
    @Override
    public void updateBasket(String productLine, Integer personId) throws EmptyFileException, PersonNotFoundException, IOException, ItemNotFoundException, WrongInputException {
        checkProductLine(productLine);
        String[] info = productLine.split(" ");
        Product product = new Product(info[0], Integer.parseInt(info[1]));
        priceList.setPrice(product);
        repository.updateBasket(product, personId);
    }

    //Возврат списка продуктов по id пользователя
    @Override
    public Collection<Product> provideBasketContent(Integer personId) throws EmptyFileException, PersonNotFoundException, IOException {
        return repository.provideBasketContent(personId);
    }

    //Возврат прайслиста
    @Override
    public String providePriceList() {
        return priceList.providePriceList();
    }

    //Создание нового пользователя
    @Override
    public int addPerson(Person person) throws IOException {
        return repository.addPerson(person);
    }

    //проверка введеной пользователем информации с помощью регулярного выражения
    private void checkProductLine(String productLine) throws WrongInputException {
        if (!productLine.matches("[а-яА-Я_0-9]{1,50}\\s\\d{1,20}")) {
            throw new WrongInputException();
        }
    }
}
