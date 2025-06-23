
import java.util.InputMismatchException; 
import java.util.Scanner;


public class Calculator {

    /**
     *     
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Java Console Calculator!");

       
        while (true) {
            
            System.out.println("1. Addition (+)");
            System.out.println("2. Subtraction (-)");
            System.out.println("3. Multiplication (*)");
            System.out.println("4. Division (/)");
            System.out.println("5. Exit");
           
            int choice;
            try {
                choice = scanner.nextInt(); 
            } catch (InputMismatchException e) {
               
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next(); 
                continue; 
            }

            
            if (choice == 5) {
                System.out.println("Exiting Calculator. Goodbye!");
                break; 
            }

            
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                continue; 
            }

            
            double num1, num2;

            
            System.out.print("Enter the first number: ");
            try {
                num1 = scanner.nextDouble(); 
            } catch (InputMismatchException e) {
                
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); 
                continue; 
            }

            
            System.out.print("Enter the second number: ");
            try {
                num2 = scanner.nextDouble();
            } catch (InputMismatchException e) {
                
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); 
                continue; 
            }

            
            double result;
            switch (choice) {
                case 1: 
                    result = add(num1, num2);
                    System.out.println("Result: " + num1 + " + " + num2 + " = " + result);
                    break;
                case 2: 
                    result = subtract(num1, num2);
                    System.out.println("Result: " + num1 + " - " + num2 + " = " + result);
                    break;
                case 3: 
                    result = multiply(num1, num2);
                    System.out.println("Result: " + num1 + " * " + num2 + " = " + result);
                    break;
                case 4: 
                    if (num2 == 0) {
                        System.out.println("Error: Cannot divide by zero.");
                    } else {
                        result = divide(num1, num2);
                        System.out.println("Result: " + num1 + " / " + num2 + " = " + result);
                    }
                    break;
                default:
                    
                    System.out.println("An unexpected error occurred. Please try again.");
                    break;
            }
        }

        
        scanner.close();
    }

    /**
     * Performs addition of two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The sum of a and b.
     */
    public static double add(double a, double b) {
        return a + b;
    }

    /**
     * Performs subtraction of two numbers.
     *
     * @param a The first number (minuend).
     * @param b The second number (subtrahend).
     * @return The difference of a and b.
     */
    public static double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Performs multiplication of two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The product of a and b.
     */
    public static double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Performs division of two numbers.
     * It assumes the caller has already handled the division-by-zero case.
     *
     * @param a The dividend.
     * @param b The divisor.
     * @return The quotient of a and b.
     */
    public static double divide(double a, double b) {
       
        return a / b;
    }
}
