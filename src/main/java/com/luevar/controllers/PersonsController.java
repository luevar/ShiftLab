package com.luevar.controllers;

import com.luevar.exceptions.ApplicationException;
import com.luevar.exceptions.EmptyFileException;
import com.luevar.models.Person;
import com.luevar.models.Product;
import com.luevar.services.PersonsServiceInterface;

import java.io.IOException;
import java.util.List;

/**
 * В теории принимает http запросы от клиента и пробрасывает их в сервис
 */
public class PersonsController {

    private final PersonsServiceInterface service;

    public PersonsController(final PersonsServiceInterface service) {
        this.service = service;
    }

    /**
     * Метод, который добавляет продукт в корзину по id пользователя
     *
     * @param productLine строка с продуктом и его количеством, введенная пользователем
     * @param personId    id пользователя, в чью корзину будет добавляться продукт
     */
    public void updateBasket(String productLine, Integer personId) {
        try {
            service.updateBasket(productLine, personId);
        } catch (ApplicationException ae) {
            System.out.println(ae.getMessage());
        } catch (EmptyFileException ef) {
            System.out.println("В базе данных нет пользователей.");
        }
    }

    /**
     * Метод возвращает список продуктов по id пользователя
     *
     * @param personId id пользователя, из корзины которого извлекаются продукты
     * @return список продуктов пользователя
     */
    public List<Product> provideBasketContent(Integer personId) {
        try {
            return service.provideBasketContent(personId);
        } catch (ApplicationException ae) {
            System.out.println(ae.getMessage());
        } catch (EmptyFileException em) {
            System.out.println("В базе данных нет пользователей.");
        }
        return null;
    }

    /**
     * Метод, возвращающий список товаров и цен на них
     *
     * @return строка, содержащая список товаров и их цен
     */
    public String providePriceList() {
        return service.providePriceList();
    }

    /**
     * Метод, добавляющий нового пользователя в базу данных
     *
     * @param person пользователь, добавляемый в базу данных
     * @return id нового пользователя
     */
    public int addPerson(Person person) {
        try {
            return service.addPerson(person);
        } catch (ApplicationException ae) {
            System.out.println(ae.getMessage());
            return -1;
        }
    }
}
