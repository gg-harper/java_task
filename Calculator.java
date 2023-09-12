import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String nextElement;
        Double result = 0d;
        int valueCounter = 2;
        Deque<String> expression = new ArrayDeque<>();

        System.out.println("Enter first value: ");
        while (true) {
            nextElement = scanner.nextLine();
            if (nextElement.matches("=")) {
                Runtime.getRuntime().exec("clear");
                System.exit(0);
            }
            if (expression.size() == 0) {
                if (!nextElement.matches("\\d") || nextElement.matches("")) {
                    System.out.println("Start with digit!");
                    continue;
                }
                expression.addLast(nextElement);
                System.out.println("Which arithmetic operation (+|-|*|/| = to finish) you require?");
                result = Double.parseDouble(nextElement);
                continue;
            }


            if (isOperator(nextElement)) {
                expression.addLast(nextElement);
                System.out.printf("Enter %d value: ", valueCounter);
                continue;
            }
            try {
                result = calculate(result, expression.getLast(), nextElement);
            } catch (RuntimeException re) {
                System.out.println(re.getMessage());System.out.printf("Enter %d value: ", valueCounter);
                continue;
            }
            System.out.println("Pre-result = " + result);
            System.out.println("Which arithmetic operation (+|-|*|/| = to finish) you require?");
            expression.addLast(nextElement);
            valueCounter++;
        }
    }

    private static boolean isOperator(String element) {
        return (element.matches("[\\+\\*/-]"));
    }

    private static Double calculate(Double resultNumerical, String operator, String operand) {

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
        if (!element.matches("[\\d\\*\\+-/\\W]+")) {
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

    private static void printExpression(Deque<String> expression) {
        boolean flag = false;
        int idx = -1;
        List<String> list = new ArrayList<>();
        String previousOperator = "";
        String operator = "";
        for (int i = 1; i < expression.size(); i++) {
            String element = expression.getFirst();
            if (!flag && element.matches("\\D") && i != 0) {
                list.add("(");
                idx = i;
                flag = true;
            }
            if (isOperator(element)) {
                if (greaterOperatorPriority(element, previousOperator)) {
                    list.add(")");
                    flag = false;
                    idx = -1;
                } else {

                }

                previousOperator = element;
            }
        }
        if (!flag) {
            list.remove(idx);
        }
    }

    private static Boolean greaterOperatorPriority(String operator, String previousOperator) {
        if (operator.matches("[\\*/]") && previousOperator.matches("[\\+-]")) {
            return true;
        }
        return false;
    }
}
