package Motioncut_Project3;
import java.io.*;
import java.util.*;

class Expense {
    String date;
    String category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }
}

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses(); // Load expenses from file
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("Expense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Category-wise Summation");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewCategoryWiseSummation();
                    break;
                case 4:
                    saveExpenses(); // Save expenses to file before exiting
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter category: ");
        String category = scanner.next();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        expenses.add(new Expense(date, category, amount));
        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("Expenses:");
        for (Expense expense : expenses) {
            System.out.println("Date: " + expense.date + ", Category: " + expense.category + ", Amount: " + expense.amount);
        }
    }

    private static void viewCategoryWiseSummation() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Map<String, Double> categoryWiseSum = new HashMap<>();
        for (Expense expense : expenses) {
            categoryWiseSum.put(expense.category, categoryWiseSum.getOrDefault(expense.category, 0.0) + expense.amount);
        }
        System.out.println("Category-wise Summation:");
        for (Map.Entry<String, Double> entry : categoryWiseSum.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Total: " + entry.getValue());
        }
    }

    private static void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.println(expense.date + "," + expense.category + "," + expense.amount);
            }
            System.out.println("Expenses saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    expenses.add(new Expense(date, category, amount));
                }
            }
            System.out.println("Expenses loaded successfully.");
        } catch (IOException e) {
            System.out.println("No previous expenses found.");
        }
    }
}
