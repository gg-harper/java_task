import java.util.Scanner;

public class SnailCoder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input tasks: ");
        int inputTasks = Integer.parseInt(scanner.nextLine());
        System.out.println("Output tasks: ");
        int outputTasks = Integer.parseInt(scanner.nextLine());
        System.out.println("Backlog count: ");
        double backlog = Double.parseDouble(scanner.nextLine());

        double result = backlog / ((outputTasks - inputTasks) * 10);

        String answer = result > 0 ? "Успеет за " + (int) Math.ceil(result) + " спринт": "Не успеет";
        System.out.println(answer);
    }
}
