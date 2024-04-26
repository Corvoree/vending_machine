package model;

import java.util.Scanner;

public class BillAcceptor implements PaymentMethod {
    @Override
    public int addFunds(Scanner scanner) {
        System.out.print("Введите сумму купюры (50 или 100): ");
        if (scanner.hasNextInt()) {
            int amount = scanner.nextInt();
            if (amount != 50 && amount != 100) {
                System.out.println("Неверная сумма купюры. Пожалуйста, введите 50 или 100.");
                return 0;
            }
            return amount;
        } else {
            System.out.println("Неверный ввод. Пожалуйста, введите число.");
            scanner.next();
            return 0;
        }
    }
}
