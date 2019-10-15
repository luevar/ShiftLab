package com.luevar;


import com.luevar.controllers.PersonsController;
import com.luevar.models.Person;
import com.luevar.models.Product;
import com.luevar.repositories.PersonsRepository;
import com.luevar.repositories.PersonsRepositoryInterface;
import com.luevar.services.PersonsService;
import com.luevar.services.PersonsServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс-клиент приложения
 */
public class Client {

    public static void main(String[] args) {
        PersonsRepositoryInterface prodRepoInterface = new PersonsRepository();
        PersonsServiceInterface prodServiceInterface = new PersonsService(prodRepoInterface);
        PersonsController controller = new PersonsController(prodServiceInterface);

        //В базе данных будет использоваться лишь один пользователь и его продуктовая корзина
        Person person = new Person("Александр", "Александров", "Александрович");
        int personId = controller.addPerson(person);
        if (personId < 0) {
            System.out.println("Невозможно добавить нового пользователя.");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.println("Введите действие, которое хотите выполнить:\n1.Обновление корзины покупателя.\n2.Вывод содержимого данных по корзине.\n0.Выход.");
                String option = in.nextLine();
                if (option.matches("[120]")) {

                    switch (option) {
                        case "1": {
                            System.out.println("\nПрайслист: ");
                            System.out.println(controller.providePriceList());
                            System.out.println("Формат вводимых данных: (Название) (количество)");
                            System.out.println("Для возврата введите 0.");
                            while (true) {
                                String inputLine = in.nextLine();
                                if (inputLine.equals("0")) {
                                    break;
                                }
                                controller.updateBasket(inputLine, personId);
                            }
                            break;
                        }
                        case "2": {
                            Date date = new Date();
                            System.out.println("Заказ номер: " + person.getBasket().getId() + "    " + person.getLastName() + " " + person.getFirstName()
                                    + " " + person.getPatronymic() + "         " + formatter.format(date));
                            System.out.println("Название              Цена             Количество               Сумма");
                            double sum = 0;
                            for (Product p : controller.provideBasketContent(0)) {
                                System.out.println(p.printProduct());
                                sum += Math.round(p.getQuantity() * p.getPrice() * 100.0) / 100.0;
                            }
                            System.out.println("\nИтого: " + Math.round(sum * 100.0) / 100.0 + "\n");
                            break;
                        }
                        case "0": {
                            return;
                        }
                    }
                } else {
                    System.out.println("Введите 1, 2 или 0\n");
                }
            }
        }
    }
}
