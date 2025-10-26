import java.io.*;
import java.util.*;

/**
 * Simple To-Do List Application in Java
 * Author: Your Name
 * Description: Console-based to-do list program with save/load functionality.
 */
public class ToDoListApp {

    // Task class to represent each to-do item
    static class Task implements Serializable {
        private String title;
        private boolean completed;

        public Task(String title) {
            this.title = title;
            this.completed = false;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void markCompleted() {
            completed = true;
        }

        @Override
        public String toString() {
            return (completed ? "[✔]" : "[ ]") + " " + title;
        }
    }

    // Main ToDoList class
    static class ToDoList {
        private ArrayList<Task> tasks;
        private final String FILE_NAME = "tasks.dat";

        public ToDoList() {
            tasks = new ArrayList<>();
            loadTasks();
        }

        public void addTask(String title) {
            tasks.add(new Task(title));
            System.out.println("✅ Task added successfully!");
        }

        public void viewTasks() {
            if (tasks.isEmpty()) {
                System.out.println("📭 No tasks available.");
                return;
            }
            System.out.println("\n---- Your To-Do List ----");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }

        public void markTaskAsDone(int index) {
            if (index < 1 || index > tasks.size()) {
                System.out.println("❌ Invalid task number!");
                return;
            }
            Task task = tasks.get(index - 1);
            if (task.isCompleted()) {
                System.out.println("⚠ Task already marked as completed!");
            } else {
                task.markCompleted();
                System.out.println("✔ Task marked as completed!");
            }
        }

        public void deleteTask(int index) {
            if (index < 1 || index > tasks.size()) {
                System.out.println("❌ Invalid task number!");
                return;
            }
            tasks.remove(index - 1);
            System.out.println("🗑 Task deleted successfully!");
        }

        public void saveTasks() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                oos.writeObject(tasks);
            } catch (IOException e) {
                System.out.println("⚠ Error saving tasks: " + e.getMessage());
            }
        }

        @SuppressWarnings("unchecked")
        public void loadTasks() {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                tasks = (ArrayList<Task>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("⚠ Error loading saved tasks: " + e.getMessage());
            }
        }
    }

    // Main Program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ToDoList toDoList = new ToDoList();
        int choice;

        System.out.println("=== 📝 Welcome to Your To-Do List ===");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Done");
            System.out.println("4. Delete Task");
            System.out.println("5. Save and Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String title = sc.nextLine();
                    toDoList.addTask(title);
                    break;

                case 2:
                    toDoList.viewTasks();
                    break;

                case 3:
                    toDoList.viewTasks();
                    System.out.print("Enter task number to mark as done: ");
                    int taskNo = sc.nextInt();
                    toDoList.markTaskAsDone(taskNo);
                    break;

                case 4:
                    toDoList.viewTasks();
                    System.out.print("Enter task number to delete: ");
                    int delNo = sc.nextInt();
                    toDoList.deleteTask(delNo);
                    break;

                case 5:
                    toDoList.saveTasks();
                    System.out.println("💾 Tasks saved! Exiting...");
                    break;

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}