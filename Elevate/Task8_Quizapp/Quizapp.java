import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // For accepting user input

public class Quizapp {

    private List<Question> questions;
    private int score;
    private Scanner scanner;

    public QuizApp() {
        questions = new ArrayList<>();
        score = 0;
        scanner = new Scanner(System.in);
        initializeQuestions(); // Populate the quiz with questions
    }

    private void initializeQuestions() {
        // Example Questions
        questions.add(new Question(
            "What is the largest planet in our solar system?",
            Arrays.asList("Mars", "Jupiter", "Venus", "Saturn"),
            1 // Jupiter is at index 1
        ));

        questions.add(new Question(
            "Which programming language is this project using?",
            Arrays.asList("Python", "Java", "C++", "JavaScript"),
            1 // Java is at index 1
        ));

        questions.add(new Question(
            "What is 7 + 8?",
            Arrays.asList("12", "13", "14", "15"),
            3 // 15 is at index 3
        ));
        // Add more questions as needed
    }

    public void startQuiz() {
        System.out.println("Welcome to the Online Quiz App!");
        System.out.println("---------------------------------");

        // Loop through each question
        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ":");
            currentQuestion.displayQuestion();

            int userAnswer = getUserAnswer();
            checkAnswer(currentQuestion, userAnswer);
        }

        displayResults();
        scanner.close(); // Close the scanner to prevent resource leaks
    }

    private int getUserAnswer() {
        int answer = -1;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.print("Enter your answer (1-" + currentQuestion.getOptions().size() + "): ");
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
                if (answer >= 1 && answer <= currentQuestion.getOptions().size()) {
                    isValidInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + currentQuestion.getOptions().size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
        return answer;
    }


    private void checkAnswer(Question question, int userAnswer) {
        // User answers are 1-based, correct answer index is 0-based
        if (userAnswer - 1 == question.getCorrectAnswerIndex()) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " +
                                (question.getCorrectAnswerIndex() + 1) + ". " +
                                question.getOptions().get(question.getCorrectAnswerIndex()));
        }
    }

    private void displayResults() {
        System.out.println("\n--- Quiz Finished ---");
        System.out.println("You scored " + score + " out of " + questions.size() + " questions.");
        System.out.println("---------------------");
    }

    public static void main(String[] args) {
        QuizApp app = new QuizApp();
        app.startQuiz();
    }
}