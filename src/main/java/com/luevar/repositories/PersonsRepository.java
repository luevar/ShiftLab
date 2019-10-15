package com.luevar.repositories;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.luevar.exceptions.EmptyFileException;
import com.luevar.exceptions.PersonNotFoundException;
import com.luevar.models.Person;
import com.luevar.models.Product;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

//Класс, отвечающий за взаимодействие с базой данных
public class PersonsRepository implements PersonsRepositoryInterface {

    private File file;
    private int previousId;

    public PersonsRepository() {

        String relativeFilePath = "./src/main/resources/database.json";
        file = new File(relativeFilePath);
        //Очищаю файл для удобства, так как мы работаем лишь с 1 пользователем
        clearFile();
        //Если не очищать файл и хранить базу данных между запусками, то можно использовать
        // данный блок кода для того, чтобы определять есть ли уже в файле записанные пользователи
        try {
            List<Person> personList = readPersonList();
            previousId = personList.get(personList.size() - 1).getId();
        } catch (IOException io) {
            //сделать логгирование исключения вместо вывода в консоль
            System.out.println("Проблема со считыванием файла репозитория.");
        } catch (EmptyFileException ef) {
            previousId = -1;
        }
    }

    //Добавление продукта в корзину по id пользователя
    @Override
    public void updateBasket(Product product, Integer personId) throws IOException, EmptyFileException, PersonNotFoundException {
        List<Person> personList = readPersonList();
        if (personId >= personList.size()) {
            throw new PersonNotFoundException(personId);
        }
        personList.get(personId).getBasket().addProduct(product);
        writePersonList(personList);
    }

    //Возврат списка продуктов по id пользователя
    @Override
    public Collection<Product> provideBasketContent(Integer personId) throws IOException, EmptyFileException, PersonNotFoundException {
        List<Person> personList = readPersonList();
        if (personId >= personList.size()) {
            throw new PersonNotFoundException(personId);
        }
        return personList.get(personId).getBasket().getProducts();
    }

    //Создание нового пользователя
    @Override
    public int addPerson(Person person) throws IOException {
        int id = previousId + 1;
        List<Person> personList;
        person.setId(id);
        try {
            personList = readPersonList();
        } catch (EmptyFileException io) {
            personList = new ArrayList<>();
        }
        personList.add(person);
        writePersonList(personList);
        previousId++;
        return id;
    }

    //Очистка файла от результатов предыдущих выполнений программы
    private void clearFile() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(("").getBytes());
        } catch (IOException io) {
            //сделать логгирование исключения вместо вывода в консоль
            System.out.println("Неудачная очистка файла перед работой программы.");
        }
    }

    //Считывать приходится, к сожалению, весь список пользователей целиком, так как не успел
    //разобраться как с помощью gson десериализовать нужный мне объект по заданному значению поля
    //Как вариант, можно считывать через буфер для ускорения работы и на случай очень большого количества пользователей

    //Считывание списка пользователей из файла
    private List<Person> readPersonList() throws IOException, EmptyFileException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
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
    }

    //Запись списка пользователей в файл
    private void writePersonList(List<Person> personList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonPersonArray = gson.toJson(personList);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = jsonPersonArray.getBytes();
        fos.write(buffer, 0, buffer.length);
        fos.close();
    }
}
