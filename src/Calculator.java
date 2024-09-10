import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    private static float result = 0;
    private static boolean isContinuing = false;
    private static String selectedOperation = "";
    private static final Scanner scanner = new Scanner(System.in);

    private static final String PROMPT_ENTER_NUMBER = "Пожалуйста, введите число:";
    private static final String PROMPT_ENTER_OPERATION_OR_EXIT = "Введите одно из следующих арифметических действий: +, -, *, /, или <C> для сброса результата, либо <s> для завершения работы.";
    private static final String ERROR_INFINITE_RESULT = "Результат вычислений оказался бесконечным.";
    private static final String ERROR_NAN_RESULT = "Результат вычислений не является числом (NaN).";
    private static final String DISPLAY_RESULT = "Текущий результат:\t";
    private static final String ERROR_INVALID_OPERATION = "Введено некорректное арифметическое действие.";
    private static final String MESSAGE_PROGRAM_EXIT = "Программа завершена.\n-----------------";
    private static final String MESSAGE_VALUES_CLEARED = "----Все ранее введенные значения сброшены----";

    public static void main(String[] args) {
        while (true) {
            float numbFirst = result;
            if (!isContinuing) {
                numbFirst = getInputNumber(PROMPT_ENTER_NUMBER);
            }

            while (!checkArithmeticSymbol(promptInput(PROMPT_ENTER_OPERATION_OR_EXIT))) {
                clear();
            }

            float numbSecond = getInputNumber(PROMPT_ENTER_NUMBER);

            result = calculate(numbFirst, numbSecond);

            if (Float.isInfinite(result)) {
                System.out.println(ERROR_INFINITE_RESULT);
                clear();
                continue;
            }

            if (Float.isNaN(result)) {
                System.out.println(ERROR_NAN_RESULT);
                clear();
                continue;
            }

            System.out.println(DISPLAY_RESULT + result);
            isContinuing = true;
        }
    }

    private static boolean checkArithmeticSymbol(String symbol) {
        switch (symbol) {
            case "+", "-", "*", "/" -> {
                selectedOperation = symbol;
                return true;
            }
            case "C" -> {
                return false;
            }
            case "s" -> stopProgram();
            default -> {
                System.out.println(ERROR_INVALID_OPERATION);
                return false;
            }
        }
        return true;
    }

    private static float calculate(float numbFirst, float numbSecond) {
        return switch (selectedOperation) {
            case "+" -> numbFirst + numbSecond;
            case "-" -> numbFirst - numbSecond;
            case "*" -> numbFirst * numbSecond;
            case "/" -> numbFirst / numbSecond;
            default -> 0;
        };
    }

    private static String promptInput(String message) {
        System.out.println(message);
        return scanner.next();
    }

    private static float getInputNumber(String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("Некорректное значение. Пожалуйста, введите число.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static void stopProgram() {
        System.out.println(MESSAGE_PROGRAM_EXIT);
        System.exit(0);
    }

    private static void clear() {
        System.out.println(MESSAGE_VALUES_CLEARED);
        isContinuing = false;
        result = 0;
    }
}