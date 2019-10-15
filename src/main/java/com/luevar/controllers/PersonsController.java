package com.luevar.controllers;

import com.luevar.exceptions.EmptyFileException;
import com.luevar.exceptions.ItemNotFoundException;
import com.luevar.exceptions.PersonNotFoundException;
import com.luevar.exceptions.WrongInputException;
import com.luevar.models.Person;
import com.luevar.models.Product;
import com.luevar.services.PersonsServiceInterface;

import java.io.IOException;
import java.util.Collection;

//В теории принимает http запросы от клиента и пробрасывает их в сервис
public class PersonsController {

    private final PersonsServiceInterface service;

    public PersonsController(final PersonsServiceInterface service) {
        this.service = service;
    }

    //Добавление продукта в корзину по id пользователя
    public void updateBasket(String productLine, Integer personId) {
        try {
            service.updateBasket(productLine, personId);
        } catch (IOException io) {
            System.out.println("Некорректная работа считывания/записи файла.");
        } catch (EmptyFileException em) {
            System.out.println("В базе данных нет пользователей.");
        } catch (PersonNotFoundException nf) {
            System.out.println("Пользователя с id = " + nf.getId() + " нет в базе данных.");
        } catch (WrongInputException wie) {
            System.out.println("Неверный ввод товара, пожалуйста, для обновления корзины введите название продукта и через пробел его количество.");
        } catch (ItemNotFoundException inf) {
            System.out.println("Продукта " + inf.getProductName() + " нет в прайслисте.");
        }
    }

    //Возврат списка продуктов по id пользователя
    public Collection<Product> provideBasketContent(Integer personId) {
        try {
            return service.provideBasketContent(personId);
        } catch (IOException io) {
            System.out.println("Некорректная работа считывания/записи файла.");
        } catch (EmptyFileException em) {
            System.out.println("В базе данных нет пользователей.");
        } catch (PersonNotFoundException nf) {
            System.out.println("Пользователя с id = " + nf.getId() + " нет в базе данных");
        }
        return null;
    }

    //Возврат прайслиста
    public String providePriceList() {
        return service.providePriceList();
    }

    //Создание нового пользователя
    public int addPerson(Person person) {
        try {
            return service.addPerson(person);
        } catch (IOException io) {
            System.out.println("Некорректная работа считывания/записи файла.");
            return -1;
        }
    }
}
