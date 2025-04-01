// Navya Jain 
// 12/5/2024
// CSE 122 
// C3: OOP it!
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class manages a to-do list, allowing users to add items, remove items, 
// mark items as done, load items from a file, save items to a file, and view 
// the current list of to-dos (or get the number of to-dos on it).
public class TodoList {
    private List<String> todos;

    // Behavior:
    //   - This method creates an empty to-do list.
    // Parameters: none.
    // Returns: none.
    // Exceptions: none.
    public TodoList() {
        todos = new ArrayList<>();
    }

    // Behavior: 
    //   - This method prints the user's current to-do list in order, with each task
    //     numbered before the task name. If there are no items on the to-do list, 
    //     a message indicating this will be printed.
    // Parameters: none.
    // Returns: none.
    // Exceptions: none.
    public void printTodos() {
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
    //   - This method adds a new item to the to-do list. If a location is specified, the item is 
    //     added at that position. Otherwise, the item is added to the end of the list.
    // Parameters:
    //   - location: the item number (a String) for where to add the item, or an empty string
    //     to add to the end.
    //   - toAdd: the item (a String) to add to the to-do list.
    // Returns: none.
    // Exceptions: none.
    public void addItem(String location, String toAdd) {
        if (location.equals("")) {
            todos.add(toAdd);
        } else {
            todos.add((Integer.parseInt(location) - 1), toAdd);
        }
    }

    // Behavior:
    //   - This method removes a specific item from the to-do list given its position.
    // Parameters:
    //   - itemCompleted: the item number (a String) of the item to remove.
    // Returns: none.
    // Exceptions: none.
    public void markItemAsDone(String itemCompleted) {
        todos.remove(Integer.parseInt(itemCompleted) - 1);
    }

    // Behavior:
    //   - This method loads to-do items from a specified file, replacing any existing items 
    //     in the list.
    // Parameters:
    //   - fileName: the name of the file (a String) containing to-do items to load.
    // Returns: none.
    // Exceptions: none.
    public void loadItems(String fileName) throws FileNotFoundException {
        Scanner fileRead = new Scanner(new File (fileName));
        todos.removeAll(todos);
        while (fileRead.hasNextLine()) {
            todos.add(fileRead.nextLine());
        }
    }

    // Behavior:
    //   - This method saves the current to-do list to a file with a specified name.
    // Parameters:
    //   - fileName: the name of the file (a String) to save the to-do list to.
    // Returns: none.
    // Exceptions: none.
    public void saveItems(String fileName) throws FileNotFoundException {
        PrintStream savedTodoList = new PrintStream(new File(fileName));
        for (int i = 0; i < todos.size(); i++) {
            savedTodoList.println(todos.get(i));
        }
    }

    // Behavior:
    //   - This method returns the current to-do list.
    // Parameters: none.
    // Returns:
    //   - List<String>: the current to-do list.
    // Exceptions: none.
    public List<String> getTodos() {
        return todos;
    }

    // Behavior:
    //   - This method gets and returns the number of items in the to-do list.
    // Parameters: none.
    // Returns:
    //   - int: the number of items in the to-do list.
    // Exceptions: none.
    public int getTodoListSize() {
        return todos.size();
    }
}
