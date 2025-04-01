// Navya Jain 
// 12/5/2024
// CSE 122 
// C3: OOP it!
// TA: Caleb Hsu

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;

public class TodoListTests {
    @Test 
    @DisplayName("TodoList Constructor AND Get TodoList method") 
    // testing the constructor isn't necessary considering the constructor doesn't set any fields.
    // thus, I 'tested' the constructor and the getter method together.
    public void testTodoListGetter() {
        TodoList todosActual = new TodoList();
        assertEquals(new ArrayList<String>(), todosActual.getTodos(), "To-do list should be " +
                     "empty when created!");
    }

    @Test
    @DisplayName("TodoList Size Getter") 
    public void testTodoListSizeGetter() {
        TodoList todosActual = new TodoList();
        assertEquals(0, todosActual.getTodoListSize(), "To-do list should be empty when created!");
    }

    @Test 
    @DisplayName("Adding An Item")
    public void testAddItemTodoList() {
        TodoList todosActual = new TodoList();
        List<String> expected = new ArrayList<>();
        expected.add("complete chemistry quiz");
        todosActual.addItem("", "complete chemistry quiz");
        assertEquals(1, todosActual.getTodoListSize(), 
                     "To-do list should only add one item when method is called!");
        assertEquals(expected, todosActual.getTodos(),
                     "To-do list should only add one item when method is called!");
        todosActual.addItem("1", "finish FIG presentation");
        expected.add(0, "finish FIG presentation");
        assertEquals(expected, todosActual.getTodos(),
                     "To-do list should add items according to the number of" 
                     + " location provided (not the index)!");
        todosActual.addItem("", "study for math final");
        expected.add("study for math final");
        assertEquals(expected, todosActual.getTodos(), "not specifying a location should" +
                     " add the item to the end of the list!");
        todosActual.addItem("4", "finish chemistry pre-lab");
        expected.add("finish chemistry pre-lab");
        assertEquals(expected, todosActual.getTodos(), "manually specfiying the location as" + 
                     " the end of the list should work!");
        todosActual.addItem("3", "buy groceries");
        expected.add(2, "buy groceries");
        assertEquals(expected, todosActual.getTodos(), "specifying a location in the middle of " +
                     "the to-do list should work!");
    }

    @Test 
    @DisplayName("Marking an Item as Complete")
    public void testMarkItemTodoList() {
        TodoList todosActual = new TodoList();
        List<String> expected = new ArrayList<>();
        todosActual.addItem("", "complete chemistry quiz");
        expected.add("complete chemistry quiz");
        todosActual.addItem("1", "finish FIG presentation");
        expected.add(0, "finish FIG presentation");
        todosActual.addItem("", "study for math final");
        expected.add("study for math final");
        todosActual.markItemAsDone("1");
        expected.remove(0);
        assertEquals(expected, todosActual.getTodos(), "marking an item as complete should " +
                     "remove the item with the number of the location (which is not the index)!");
        todosActual.markItemAsDone("2");
        expected.remove(1);
        assertEquals(expected, todosActual.getTodos(), "marking an item as complete should " +
                     "remove the item with the number of the location (which is not the " +
                     "index)! make sure this works for the last item!");
        expected.remove(0);
        todosActual.markItemAsDone("1");
        assertEquals(expected, todosActual.getTodos(), "marking an item as complete should " +
                     "remove the item with the number of the location (which is not the index)!" +
                     " this should work for a 1-item to do list");
    }

    @Test 
    @DisplayName("Load Items")
    public void testLoadItemsTodoList() throws FileNotFoundException {
        TodoList todosActual = new TodoList();
        todosActual.loadItems("TestingFileSaveLoad1.txt");
        List<String> expected = new ArrayList<>();
        expected.add("Comment CSE assignment");
        expected.add("Finish math homework");
        expected.add("Prepare for presentation");
        assertEquals(expected, todosActual.getTodos(), "load items should work on an empty to-do" +
                     " list!");
        todosActual.loadItems("TestingFileLoad2.txt");
        expected.clear();
        expected.add("Cry about chemistry");
        expected.add("Think about switching majors");
        expected.add("Write my resume");
        assertEquals(expected, todosActual.getTodos(), "load items should entirely replace the " +
                     "old to-do list if there are already items in the list");
    }

    @Test 
    @DisplayName("Save Items")
    public void testSaveItemsTodoList() throws FileNotFoundException {
        TodoList todosActual = new TodoList();
        todosActual.addItem("", "Comment CSE assignment");
        todosActual.addItem("", "Finish math homework");
        todosActual.addItem("", "Prepare for presentation");
        todosActual.saveItems("TestingFileActual1.txt");
        Scanner fileReaderExpected = new Scanner(new File("TestingFileSaveLoad1.txt"));
        Scanner fileReaderActual = new Scanner(new File("TestingFileActual1.txt"));
        while (fileReaderExpected.hasNextLine()) {
            assertEquals(true, fileReaderActual.hasNextLine(), "each item in the to-do " +
                       "list should appear on a new line!");
            assertEquals(fileReaderExpected.nextLine(), fileReaderActual.nextLine(), "the " +
                        "file should have exactly the same to-dos as the list with no extra text");
        }
    }
}
