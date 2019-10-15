package com.luevar.services;

import com.luevar.exceptions.ApplicationException;
import com.luevar.exceptions.EmptyFileException;
import com.luevar.models.Person;
import com.luevar.models.PriceList;
import com.luevar.models.Product;
import com.luevar.repositories.PersonsRepositoryInterface;

import java.io.IOException;
import java.util.List;

/**
 * Принимает запросы из контроллера и проверяет их на отсутствие ошибочных входных данных
 */
public class PersonsService implements PersonsServiceInterface {

    private final PersonsRepositoryInterface repository;
    private final PriceList priceList;

    public PersonsService(PersonsRepositoryInterface productsRepository) {
        this.repository = productsRepository;
        priceList = new PriceList();
    }


    /**
     * Метод, который добавляет продукт в корзину по id пользователя
     *
     * @param productLine строка с продуктом и его количеством, введенная пользователем
     * @param personId    id пользователя, в чью корзину будет добавляться продукт
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     * @throws EmptyFileException   пробрасывается в случае отсутствия пользователей в базе данных и невозможности их считать
     */
    @Override
    public void updateBasket(String productLine, Integer personId) throws ApplicationException, EmptyFileException {
        checkProductLine(productLine);
        String[] info = productLine.split(" ");
        Product product = new Product(info[0], Integer.parseInt(info[1]));
        priceList.setPrice(product);
        repository.updateBasket(product, personId);
    }


    /**
     * Метод возвращает список продуктов по id пользователя
     *
     * @param personId id пользователя, из корзины которого извлекаются продукты
     * @return список продуктов пользователя
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     * @throws EmptyFileException   пробрасывается в случае отсутствия пользователей в базе данных и невозможности их считать
     */
    @Override
    public List<Product> provideBasketContent(Integer personId) throws ApplicationException, EmptyFileException {
        return repository.provideBasketContent(personId);
    }


    /**
     * Метод, возвращающий список товаров и цен на них
     *
     * @return строка, содержащая список товаров и их цен
     */
    @Override
    public String providePriceList() {
        return priceList.providePriceList();
    }


    /**
     * Метод, добавляющий нового пользователя в базу данных
     *
     * @param person пользователь, добавляемый в базу данных
     * @return id нового пользователя
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     */
    @Override
    public int addPerson(Person person) throws ApplicationException {
        return repository.addPerson(person);
    }


    /**
     * Метод для проверки введеной пользователем информации с помощью регулярного выражения
     *
     * @param productLine введеная пользователем строка
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     */
    private void checkProductLine(String productLine) throws ApplicationException {
        if (!productLine.matches("[а-яА-Я_0-9]{1,50}\\s\\d{1,20}")) {
            throw new ApplicationException("Неверный ввод товара, пожалуйста, для обновления корзины введите название продукта и через пробел его количество.");
        }
    }
}
