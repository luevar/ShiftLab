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
import java.util.List;

/**
 * Принимает запросы из контроллера и проверяет их на отсутствие ошибочных входных данных
 */
public class PersonsService implements PersonsServiceInterface {

    private final PersonsRepositoryInterface repository;
    private PriceList priceList;

    public PersonsService(PersonsRepositoryInterface productsRepository) {
        this.repository = productsRepository;
        priceList = new PriceList();
    }


    /**
     * Метод, который добавляет продукт в корзину по id пользователя
     * @param productLine строка с продуктом и его количеством, введенная пользователем
     * @param personId id пользователя, в чью корзину будет добавляться продукт
     * @throws IOException пробрасывается в случае ошибки чтения/записи файла
     * @throws EmptyFileException пробрасывается в случае отсутствия пользователей в базе данных
     * @throws PersonNotFoundException пробрасывается в случае отсутствия запрашиваемого пользователя в базе данных
     * @throws ItemNotFoundException пробрасывается в случае если в прайслисте нет продукта, введеного пользователем
     * @throws WrongInputException пробрасывается в случае если строка, введеная пользователем, не соответствует ожидаемой
     */
    @Override
    public void updateBasket(String productLine, Integer personId) throws IOException, EmptyFileException, PersonNotFoundException, ItemNotFoundException, WrongInputException {
        checkProductLine(productLine);
        String[] info = productLine.split(" ");
        Product product = new Product(info[0], Integer.parseInt(info[1]));
        priceList.setPrice(product);
        repository.updateBasket(product, personId);
    }


    /**
     * Метод возвращает список продуктов по id пользователя
     * @param personId id пользователя, из корзины которого извлекаются продукты
     * @return список продуктов пользователя
     * @throws IOException пробрасывается в случае ошибки чтения/записи файла
     * @throws EmptyFileException пробрасывается в случае отсутствия пользователей в базе данных
     * @throws PersonNotFoundException пробрасывается в случае отсутствия запрашиваемого пользователя в базе данных
     */
    @Override
    public List<Product> provideBasketContent(Integer personId) throws IOException, EmptyFileException, PersonNotFoundException  {
        return repository.provideBasketContent(personId);
    }


    /**
     * Метод, возвращающий список товаров и цен на них
     * @return строка, содержащая список товаров и их цен
     */
    @Override
    public String providePriceList() {
        return priceList.providePriceList();
    }


    /**
     * Метод, добавляющий нового пользователя в базу данных
     * @param person пользователь, добавляемый в базу данных
     * @return id нового пользователя
     * @throws IOException пробрасывается в случае ошибки чтения/записи файла
     */
    @Override
    public int addPerson(Person person) throws IOException {
        return repository.addPerson(person);
    }


    /**
     * Метод для проверки введеной пользователем информации с помощью регулярного выражения
     * @param productLine введеная пользователем строка
     * @throws WrongInputException пробрасывается в случае если строка, введеная пользователем, не соответствует ожидаемой
     */
    private void checkProductLine(String productLine) throws WrongInputException {
        if (!productLine.matches("[а-яА-Я_0-9]{1,50}\\s\\d{1,20}")) {
            throw new WrongInputException();
        }
    }
}
