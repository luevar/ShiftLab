package com.luevar.repositories;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.luevar.exceptions.ApplicationException;
import com.luevar.exceptions.EmptyFileException;
import com.luevar.models.Person;
import com.luevar.models.Product;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Класс, отвечающий за взаимодействие с базой данных
 */
public class PersonsRepository implements PersonsRepositoryInterface {

    private final File file;
    private int previousId;

    public PersonsRepository() {
        String relativeFilePath = "./src/main/resources/database.json";
        file = new File(relativeFilePath);
        //Если не очищать файл и хранить базу данных между запусками, то можно использовать
        //данный блок кода для того, чтобы определять есть ли уже в файле записанные пользователи
        try {
            //Очистка файла для удобства, так как работа идет лишь с 1 пользователем
            clearFile();
            List<Person> personList = readPersonList();
            previousId = personList.get(personList.size() - 1).getId();
        } catch (ApplicationException io) {
            //логгирование исключения вместо вывода в консоль
            System.out.println(io.getMessage());
        } catch (EmptyFileException ef) {
            previousId = -1;
        }
    }


    /**
     * Метод, который добавляет продукт в корзину по id пользователя
     *
     * @param product  продукт, который надо добавить пользователю в корзину
     * @param personId id пользователя, в чью корзину будет добавляться продукт
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     * @throws EmptyFileException   пробрасывается в случае отсутствия пользователей в базе данных и невозможности их считать
     */
    @Override
    public void updateBasket(Product product, Integer personId) throws ApplicationException, EmptyFileException {
        List<Person> personList = readPersonList();
        if (personId >= personList.size()) {
            throw new ApplicationException("Пользователя с id = " + personId + " нет в базе данных.");
        }
        personList.get(personId).getBasket().addProduct(product);
        writePersonList(personList);
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
        List<Person> personList = readPersonList();
        if (personId >= personList.size()) {
            throw new ApplicationException("Пользователя с id = " + personId + " нет в базе данных.");
        }
        return personList.get(personId).getBasket().getProducts();
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
        int id = previousId + 1;
        List<Person> personList;
        person.setId(id);
        try {
            personList = readPersonList();
        } catch (EmptyFileException ef) {
            personList = new ArrayList<>();
        }
        personList.add(person);
        writePersonList(personList);
        previousId++;
        return id;
    }


    /**
     * Метод для очистки файла от результатов предыдущих выполнений программы
     */
    private void clearFile() throws ApplicationException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(("").getBytes());
        } catch (IOException io) {
            throw new ApplicationException("Некорректная работа записи файла.", io);
        }
    }


    //разобраться как с помощью gson десериализовать нужный объект по заданному значению поля

    /**
     * Метод, считывающий список пользователей из файла
     *
     * @return считанный список пользователей
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     * @throws EmptyFileException   пробрасывается в случае отсутствия пользователей в базе данных и невозможности их считать
     */
    private List<Person> readPersonList() throws ApplicationException, EmptyFileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                json.append(line).append("\n");
            Type targetClassType = new TypeToken<ArrayList<Person>>() {}.getType();
            List<Person> personList = new GsonBuilder().create().fromJson(String.valueOf(json), targetClassType);

            if (personList == null) {
                throw new EmptyFileException();
            }
            reader.close();
            return personList;
        } catch (IOException io) {
            throw new ApplicationException("Некорректная работа считывания файла.", io);
        }
    }


    /**
     * Метод, записывающий список пользователей в файл
     *
     * @param personList список пользователей
     * @throws ApplicationException пробрасывается в случае возникновения проблемы в работе программы
     */
    private void writePersonList(List<Person> personList) throws ApplicationException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonPersonArray = gson.toJson(personList);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = jsonPersonArray.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException io) {
            throw new ApplicationException("Некорректная работа записи файла.", io);
        }
    }
}
