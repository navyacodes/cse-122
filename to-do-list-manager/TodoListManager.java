// Navya Jain 
// 10/17/2024
// CSE 122 
// C1: TodoListManager
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class allows a user to create and manage a to-do list. The user can 
// add to-dos, mark to-dos as complete, save their to-do list to a file, and 
// load a saved list from a file. If the extension is turned on, the user can
// also view their completed to-do items in a list.
public class TodoListManager {
    public static final boolean EXTENSION_FLAG = true;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to your TODO List Manager!");
        Scanner console = new Scanner(System.in);
        String userChoice = "";
        List<String> todos = new ArrayList<String>();
        List<String> completedTodos = new ArrayList<String>();
        while (!userChoice.equalsIgnoreCase("Q")) {
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, " + 
                             "(S)ave TODOs, (Q)uit");
            if (EXTENSION_FLAG) {
                System.out.print(", (C)ompleted TODO list? ");
            }
            else {
                System.out.print("? ");
            }
            userChoice = console.nextLine();
            if (userChoice.equalsIgnoreCase("A")) {
                addItem(console, todos);
            }
            else if (userChoice.equalsIgnoreCase("M")) {
                markItemAsDone(console, todos, completedTodos);
            }
            else if (userChoice.equalsIgnoreCase("L")) {
                loadItems(console, todos);
            }
            else if (userChoice.equalsIgnoreCase("S")) {
                saveItems(console, todos);
            }
            else if (userChoice.equalsIgnoreCase("C") && EXTENSION_FLAG) {
                printCompletedTodos(completedTodos);
            }
            else if (!userChoice.equalsIgnoreCase("Q")) {
                System.out.println("Unknown input: " + userChoice);
            }
            if (!userChoice.equalsIgnoreCase("Q")) {
                printTodos(todos);
            }
        }
    }

    // Behavior: 
    //   - This method prints the user's current to-do list in order, with each task
    //     numbered before the task name.
    // Parameters:
    //   - todos: the ArrayList of strings containing the user's current to-do list
    //            to be printed.
    // Returns: none
    // Exceptions: none
    public static void printTodos(List<String> todos) {
        System.out.println("Today's TODOs:");
        if (todos.size() > 0) {
            for (int i = 0; i < todos.size(); i++) {
                System.out.println((i + 1) + ": " + todos.get(i));
            }
        }
        else {
            System.out.println("You have nothing to do yet today! Relax!");
        }
    }

    // Behavior: 
    //   - This method allows the user to add items to their to-do list by typing 
    //     the task's name, and the location they would like it to be in the to-do
    //     list (if the list is not empty). The user can also add a task to the end.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as creating a task.
    //   - todos: the ArrayList of strings containing the user's current to-do list. 
    // Returns: none
    // Exceptions: none
    public static void addItem(Scanner console, List<String> todos) {
        System.out.print("What would you like to add? ");
        String toAdd = console.nextLine();
        if (todos.size() > 0) {
            System.out.print("Where in the list should it be (1-" + 
                               (todos.size() + 1) + ")? (Enter for end): ");
            String location = console.nextLine();
            if (location.equals("")) {
                todos.add(toAdd);
            }
            else {
                int locationInt = Integer.parseInt(location);
                todos.add((locationInt - 1), toAdd);
            }
        }
        else {
            todos.add(toAdd);
        }
    }

    // Behavior: 
    //   - This method allows the user to mark items as complete on their to-do list by
    //     taking in the location of the task the user wishes to mark as complete. If the
    //     extension is on, the items marked as complete will also save into an ArrayList
    //     containing completed items. 
    // Parameters:
    //   - console: the Scanner accepting user input for features such as choosing which
    //              task is complete. 
    //   - todos: the ArrayList of strings containing the user's current to-do list. 
    //   - completedTodos: the ArrayList of strings containing the todos the user has 
    //     marked as complete. 
    // Returns: none
    // Exceptions: none
    public static void markItemAsDone(Scanner console, List<String> todos, 
                                      List<String> completedTodos) {
        if (todos.size() > 0) {
            System.out.print("Which item did you complete (1-" + 
                             todos.size() + ")? ");
            String itemCompleted = console.nextLine();
            int itemCompletedInt = Integer.parseInt(itemCompleted);
            if (EXTENSION_FLAG) {
                completedTodos.add(todos.get(itemCompletedInt - 1));
            }
            todos.remove(itemCompletedInt - 1);
        }
        else {
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
    //   - todos: the ArrayList of strings containing the user's current to-do list.
    // Returns: none
    // Exceptions: none
    public static void loadItems(Scanner console, List<String> todos)
                                 throws FileNotFoundException {
        System.out.print("File name? ");
        Scanner fileRead = new Scanner(new File (console.nextLine()));
        todos.removeAll(todos);
        while (fileRead.hasNextLine()) {
            todos.add(fileRead.nextLine());
        }
    }
    
    // Behavior: 
    //   - This method allows a user to save their to-do list to a new file with name
    //     specified by the user. This file will have each to-do task on a seperate line.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as choosing a file
    //              name to save the to-do list to. 
    //   - todos: the ArrayList of strings containing the user's current to-do list.
    // Returns: none
    // Exceptions: none
    public static void saveItems(Scanner console, List<String> todos)
                                 throws FileNotFoundException {
        System.out.print("File name? ");
        PrintStream savedTodoList = new PrintStream(new File(console.nextLine()));
        for (int i = 0; i < todos.size(); i++) {
            savedTodoList.println(todos.get(i));
        }
    }

    // Behavior: 
    //   - This method prints the user's current completed to-do list in order of 
    //     completion, with each task on its own line, having a checkmark emoji next
    //     to it. 
    // Parameters:
    //   - completedTodos: the ArrayList of strings containing the todos the user has 
    //     marked as complete. 
    // Returns: none
    // Exceptions: none
    public static void printCompletedTodos(List<String> completedTodos) {
        if (completedTodos.size() > 0) {
            for (int i = 0; i < completedTodos.size(); i++) {
                System.out.println(completedTodos.get(i) + " ✅");
            }
        }
        else {
            System.out.println("You have no completed to-do items yet!");
        }
        System.out.println();
    }
}
