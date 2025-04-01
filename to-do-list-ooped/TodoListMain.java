// Navya Jain 
// 12/5/2024
// CSE 122 
// C3: OOP it!
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class allows a user to create and manage a to-do list. The user can 
// add to-dos, mark to-dos as complete, save their to-do list to a file, and 
// load a saved list from a file.
public class TodoListMain {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to your TODO List Manager!");
        Scanner console = new Scanner(System.in);
        String userChoice = "";
        TodoList todoList = new TodoList();
        while (!userChoice.equalsIgnoreCase("Q")) {
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, " + 
                             "(S)ave TODOs, (Q)uit? ");
            userChoice = console.nextLine();
            if (userChoice.equalsIgnoreCase("A")) {
                addItem(console, todoList);
            }
            else if (userChoice.equalsIgnoreCase("M")) {
                markItemAsDone(console, todoList);
            }
            else if (userChoice.equalsIgnoreCase("L")) {
                loadItems(console, todoList);
            }
            else if (userChoice.equalsIgnoreCase("S")) {
                saveItems(console, todoList);
            }
            else if (!userChoice.equalsIgnoreCase("Q")) {
                System.out.println("Unknown input: " + userChoice);
            }
            if (!userChoice.equalsIgnoreCase("Q")) {
                todoList.printTodos();
            }
        }
    }

    // Behavior: 
    //   - This method allows the user to add items to their to-do list by typing 
    //     the task's name, and the location they would like it to be in the to-do
    //     list (if the list is not empty). The user can also add a task to the end.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as creating a task.
    //   - todos: the TodoList containing the user's current to-do list. 
    // Returns: none
    // Exceptions: none
    public static void addItem(Scanner console, TodoList todos) {
        System.out.print("What would you like to add? ");
        String toAdd = console.nextLine();
        if (todos.getTodoListSize() > 0) {
            System.out.print("Where in the list should it be (1-" + 
                               (todos.getTodoListSize() + 1) + ")? (Enter for end): ");
            todos.addItem(console.nextLine(), toAdd);
        } else {
            todos.addItem("", toAdd);
        }
    }

    // Behavior: 
    //   - This method allows the user to mark items as complete on their to-do list by
    //     taking in the location of the task the user wishes to mark as complete.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as choosing which
    //              task is complete. 
    //   - todos: the TodoList containing the user's current to-do list. 
    // Returns: none
    // Exceptions: none
    public static void markItemAsDone(Scanner console, TodoList todos) {
        if (todos.getTodoListSize() > 0) {
            System.out.print("Which item did you complete (1-" + 
                             todos.getTodoListSize() + ")? ");
            todos.markItemAsDone(console.nextLine());
        } else {
            System.out.println("All done! Nothing left to mark as done!");
        }
    }
    
    // Behavior: 
    //   - This method allows a user to entirely replace their current to-do list 
    //     with one saved in a file by asking the user for the file name (which contains the
    //     the replacement to-do list).  
    // Parameters:
    //   - console: the Scanner accepting user input for features such as choosing a file
    //              name to load. 
    //   - todos: the TodoList containing the user's current to-do list. 
    // Returns: none
    // Exceptions: none
    public static void loadItems(Scanner console, TodoList todos) throws FileNotFoundException {
        System.out.print("File name? ");
        todos.loadItems(console.nextLine());
    }

    // Behavior: 
    //   - This method allows a user to save their to-do list to a new file with name
    //     specified by the user. This file will have each to-do task on a seperate line.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as choosing a file
    //              name to save the to-do list to. 
    //   - todos: the TodoList containing the user's current to-do list. 
    // Returns: none
    // Exceptions: none
    public static void saveItems(Scanner console, TodoList todos) throws FileNotFoundException {
        System.out.print("File name? ");
        todos.saveItems(console.nextLine());
    }
}
