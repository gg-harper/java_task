import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nextElement;
        Double result = 0d;
        Deque<String> expression = new ArrayDeque<>();
        while (true) {
            System.out.println("Enter next: ");
            nextElement = scanner.nextLine();
            if (expression.size() == 0) {
                if (nextElement.matches("\\D+") || nextElement.matches("")) {
                    System.out.println("Start with digit!");
                continue;
                }
            expression.addLast(nextElement);
            result = Double.parseDouble(nextElement);
            continue;
        }
        if (!checkElement(expression.getLast(), nextElement)) {
            System.out.println(nextElement);
            continue;
        }

        if (isOperator(nextElement)) {
            expression.addLast(nextElement);
            continue;
        }
        try {
            result = calculate(result, expression.getLast(), nextElement);
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
            continue;
        }
            System.out.println("Result=" + result);
        expression.addLast(nextElement);
        }
    }

    private static boolean isOperator(String element) {
        return  (element.matches("[\\+\\*/-]"));
    }

    private static Double calculate(Double resultNumerical, String operator, String operand) {
        System.out.println("operator=" + operator + ", operand=" + operand);

        Double operandNumerical = Double.parseDouble(operand);

        switch (operator) {
            case "+":
                resultNumerical += operandNumerical;
                break;
            case "-":
                resultNumerical -= operandNumerical;
                break;
            case "*":
                resultNumerical *= (Double) operandNumerical;
                break;
            case "/":
                if (operandNumerical == 0) {
                    throw new RuntimeException("Division by 0 is prohibited!");
                }
                resultNumerical /= operandNumerical;
                break;
            default:
                throw new RuntimeException("Wrong operation!");
        }
        return resultNumerical;
    }
    private static Boolean checkElement(String previousElement, String element) {
        System.out.println("Checking...\n previous = " + previousElement + " elem = " + element);
        if (!element.matches("[\\d\\*\\+-/]+")) {
            System.out.println("Check 1. Wrong symbol!");
            return false;
        }
        if (element.matches("")) {
            System.out.println("empty");
            return false;
        }
        if (isOperator(previousElement) && isOperator(element) || !isOperator(previousElement) && !isOperator(element)) {
            System.out.println("Check 2. Wrong symbol!");
            return false;
        }
        return true;
    }
}
