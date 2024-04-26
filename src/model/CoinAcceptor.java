package model;

import java.util.Scanner;

public class CoinAcceptor implements PaymentMethod {
    @Override
    public int addFunds(Scanner scanner) {
        System.out.print("Введите сумму монет (кратную 10): ");
        int amount = scanner.nextInt();
        if (amount % 10 != 0) {
            System.out.println("Неверная сумма монет. Пожалуйста, введите сумму, кратную 10.");
            return 0;
        }
        return amount;
    }
}
