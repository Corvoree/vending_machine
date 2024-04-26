import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private int currentBalance = 0;

    private PaymentMethod paymentMethod;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        currentBalance=0;

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Выберите способ оплаты (1 - Монеты, 2 - Купюры):");
            while (!scanner.hasNextInt()) {
                System.out.println("Неверный ввод. Пожалуйста, введите 1 или 2.");
                scanner.next(); // Очищаем некорректный ввод
            }
            choice = scanner.nextInt();
        } while (choice != 1 && choice != 2);

        switch (choice) {
            case 1:
                paymentMethod = new CoinAcceptor();
                break;
            case 2:
                paymentMethod = new BillAcceptor();
                break;
        }

    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);
        print("Текущий баланс: " + currentBalance);
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (currentBalance >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        if (products.size() == 0) {
            print("Недостаточно средств. Пополните баланс.");
        } else {
            showActions(products);
        }
        print(" h - Выйти");
        while (true) {
            String action = fromConsole().trim();
            if (action.isEmpty()) {
                print("Ввод не может быть пустым. Пожалуйста, введите букву действия.");
            } else if ("a".equalsIgnoreCase(action)) {
                int addedAmount = paymentMethod.addFunds(new Scanner(System.in));
                currentBalance += addedAmount;
                System.out.println("Вы пополнили баланс на " + addedAmount);
                return;
            } else if ("h".equalsIgnoreCase(action)) {
                isExit = true;
                return;
            } else {
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                            currentBalance -= products.get(i).getPrice();
                            print("Вы купили " + products.get(i).getName());
                            return;
                        }
                    }
                    print("Недопустимая буква. Доступные опции: a, h, или буквы продуктов.");
            }
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
