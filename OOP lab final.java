import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Abstraction
abstract class QuestionBase {
    protected String questionText;

    public abstract void display();
    public abstract boolean checkAnswer(int answer);
}

// 2. Inheritance & Encapsulation
class QuizQuestion extends QuestionBase {
    private String[] options;
    private int correctOption;

    public QuizQuestion(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    // Polymorphism (Overriding)
    @Override
    public void display() {
        System.out.println("\n" + questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    // Method Overloading
    public void display(boolean showAnswer) {
        System.out.println("Question: " + questionText);
        if (showAnswer) {
            System.out.println(
                "Correct Answer: Option " + correctOption +
                " (" + options[correctOption - 1] + ")"
            );
        }
    }

    @Override
    public boolean checkAnswer(int answer) {
        return answer == correctOption;
    }
}

// 3. Association
class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// 4. Aggregation
class Quiz {
    private List<QuestionBase> questions;

    public Quiz(List<QuestionBase> questions) {
        this.questions = questions;
    }

    public void takeQuiz(User user) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        List<QuizQuestion> wrongAnswers = new ArrayList<>();

        System.out.println("===================================");
        System.out.println("Welcome, " + user.getName() + "! Let's start the quiz.");
        System.out.println("===================================");

        for (QuestionBase q : questions) {
            q.display();
            System.out.print("Enter your choice (1-4): ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
                choice = -1;
            }

            if (q.checkAnswer(choice)) {
                score++;
            } else {
                if (q instanceof QuizQuestion) {
                    wrongAnswers.add((QuizQuestion) q);
                }
            }
        }

        displayResults(score, wrongAnswers);

        scanner.close();
    }

    private void displayResults(int score, List<QuizQuestion> wrongAnswers) {
        System.out.println("\n=== Quiz Over ===");
        System.out.println("Total Right Answers: " + score + " out of " + questions.size());

        if (!wrongAnswers.isEmpty()) {
            System.out.println("\n--- Review of Wrong Answers ---");
            for (QuizQuestion wrongQ : wrongAnswers) {
                wrongQ.display(true);
                System.out.println();
            }
        } else {
            System.out.println("Perfect score! Great job.");
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        List<QuestionBase> questionBank = new ArrayList<>();

        questionBank.add(new QuizQuestion(
                "What is 2 + 2?",
                new String[]{"1", "2", "3", "4"},
                4));

        questionBank.add(new QuizQuestion(
                "Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Venus"},
                2));

        questionBank.add(new QuizQuestion(
                "What is the capital of France?",
                new String[]{"London", "Berlin", "Paris", "Madrid"},
                3));

        questionBank.add(new QuizQuestion(
                "Which programming language is this quiz written in?",
                new String[]{"Python", "C++", "Java", "Ruby"},
                3));

        questionBank.add(new QuizQuestion(
                "How many legs does a spider have?",
                new String[]{"6", "8", "10", "12"},
                2));

        User player = new User("Student");
        Quiz javaQuiz = new Quiz(questionBank);
        javaQuiz.takeQuiz(player);
    }
}
