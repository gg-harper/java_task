import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String nextElement;
        Double result = 0d;
        int valueCounter = 2;
        boolean flag = true;
        Deque<String> expression = new ArrayDeque<>();
        String previousOperator = "*";

        expression.addLast("(");
        System.out.println("Enter first value: ");
        while (true) {
            nextElement = scanner.nextLine();
            if (nextElement.matches("=")) {
                if (flag) {
                    expression.removeFirst();
                }
                expression.addLast("=");
                System.out.print("\033\143");
//                Runtime.getRuntime().exec("clear");
                expression.stream().forEach(System.out::print);
                System.out.println("\n");
                System.exit(0);
            }
            if (expression.size() == 1) {
                if (!nextElement.matches("\\d") || nextElement.matches("")) {
                    System.out.println("Start with digit!");
                    continue;
                }
                expression.addLast(nextElement);
                System.out.println("Which arithmetic operation (+|-|*|/| = to finish) you require?");
                result = Double.parseDouble(nextElement);
                continue;
            }
            if (!checkElement(expression.getLast(), nextElement)) {
                System.out.println("Wrong symbol!");
                continue;
            }

            if (isOperator(nextElement)) {
                if (previousOperatorLessPri(previousOperator, nextElement)) {
                    expression.addLast(")");
                    flag = false;
                }
                expression.addLast(nextElement);
                previousOperator = nextElement;
                System.out.printf("Enter %d value: ", valueCounter);
                continue;
            }
            try {
                result = calculate(result, expression.getLast(), nextElement);
            } catch (RuntimeException re) {
                System.out.println(re.getMessage());
                System.out.printf("Enter %d value: ", valueCounter);
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
                resultNumerical *= operandNumerical;
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
            return false;
        }
        if (element.matches("")) {
            System.out.println("empty");
            return false;
        }
        if (isOperator(previousElement) && isOperator(element) || !isOperator(previousElement) && !isOperator(element)) {
            return false;
        }
        return true;
    }

    private static Boolean previousOperatorLessPri (String previousOperator, String operator) {
        if (operator.matches("[\\*/]") && previousOperator.matches("[\\+-]")) {
            return true;
        }
        return false;
    }
}
