// Navya Jain 
// 10/25/2024
// CSE 122 
// P1: Music Playlist
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class allows a user to create and manage a music playlist. The user can add songs to it, 
// or play songs from it (in order), at which point they will be removed from the playlist and 
// added to the user's music history. The user can print this history in reverse-chronological  
// order, clear this history, or delete a specified amount of items from the history from the most
// recent or least recent sides of it.
public class MusicPlaylist {
    public static void main(String[] arg) {
        System.out.println("Welcome to the CSE 122 Music Playlist!");
        Queue<String> musicPlaylistQueue = new LinkedList<>();
        Stack<String> musicHistory = new Stack<>();
        Scanner console = new Scanner(System.in);
        String userChoice = "";
        while (!userChoice.equalsIgnoreCase("Q")) {
            System.out.println("(A) Add song");
            System.out.println("(P) Play song");
            System.out.println("(Pr) Print history");
            System.out.println("(C) Clear history");
            System.out.println("(D) Delete from history");
            System.out.println("(Q) Quit");
            System.out.println();
            System.out.print("Enter your choice: ");
            userChoice = console.nextLine();
            if (userChoice.equalsIgnoreCase("A")) {
                addSong(console, musicPlaylistQueue);
            }
            else if (userChoice.equalsIgnoreCase("P")) {
                playSong(musicPlaylistQueue, musicHistory);
            }
            else if (userChoice.equalsIgnoreCase("Pr")) {
                printHistory(musicHistory);
            }
            else if (userChoice.equalsIgnoreCase("C")) {
                musicHistory.clear();
            }
            else if (userChoice.equalsIgnoreCase("D")) {
                deleteHistory(console, musicHistory);
            }
        }
    }

    // Behavior: 
    //   - This method allows the user to add a song to their music playlist by 
    //     typing the song's name. The song will be added to the end of the playlist queue.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as adding a song.
    //   - musicPlaylistQueue: the Queue of strings containing the user's current music playlist.
    // Returns: none
    // Exceptions: none
    public static void addSong(Scanner console, Queue<String> musicPlaylistQueue) {
        System.out.print("Enter song name: ");
        String songName = console.nextLine();
        musicPlaylistQueue.add(songName);
        System.out.println("Successfully added " + songName);
    }

    // Behavior: 
    //   - This method plays the next song in the user's music playlist by retrieving 
    //     the song at the front of the queue. The song is added to the user's music 
    //     history stack once played. If the playlist is empty, an exception is thrown.
    // Parameters:
    //   - musicPlaylistQueue: the Queue of strings containing the user's current music playlist.
    //   - musicHistory: the Stack of strings storing songs that have been played.
    // Returns: none
    // Exceptions: 
    //   - IllegalStateException: thrown if the playlist is empty when trying to play a song.
    public static void playSong(Queue<String> musicPlaylistQueue, Stack<String> musicHistory) {
        if (musicPlaylistQueue.isEmpty()) {
            throw new IllegalStateException("you don't have any songs to play!");
        }
        System.out.println("Playing song: " + musicPlaylistQueue.peek());
        musicHistory.push(musicPlaylistQueue.peek());
        musicPlaylistQueue.remove();
    }

    // Behavior: 
    //   - This method prints the user's music history by displaying all songs that have 
    //     been played in reverse chronological order. If there are no songs in the history, 
    //     an exception is thrown.
    // Parameters:
    //   - musicHistory: the Stack of strings storing songs that have been played.
    // Returns: none
    // Exceptions: 
    //   - IllegalStateException: thrown if the music history is empty when attempting to print.
    public static void printHistory(Stack<String> musicHistory) {
        if (musicHistory.isEmpty()) {
            throw new IllegalStateException("you don't have any music in your history!");
        }
        Queue<String> auxiliaryQueue = new LinkedList<>();
        stackToQueue(musicHistory, auxiliaryQueue);
        while (!auxiliaryQueue.isEmpty()) {
            System.out.println(auxiliaryQueue.peek());
            musicHistory.push(auxiliaryQueue.remove());
        }
        stackToQueue(musicHistory, auxiliaryQueue);
        queueToStack(musicHistory, auxiliaryQueue);
    }
    
    // Behavior: 
    //   - This method transfers all elements from a stack to a queue.
    // Parameters:
    //   - toRemoveFrom: the Stack of strings containing elements to be transferred.
    //   - toPutInto: the Queue of strings where elements from the stack will be placed.
    // Returns: none
    // Exceptions: none
    public static void stackToQueue(Stack<String> toRemoveFrom, Queue<String> toPutInto) {
        while (!toRemoveFrom.isEmpty()) {
            toPutInto.add(toRemoveFrom.pop());
        }
    }
    
    // Behavior: 
    //   - This method transfers all elements from a queue to a stack.
    // Parameters:
    //   - toPutInto: the Stack of strings where elements from the queue will be placed.
    //   - toRemoveFrom: the Queue of strings containing elements to be transferred.
    // Returns: none
    // Exceptions: none
    public static void queueToStack(Stack<String> toPutInto, Queue<String> toRemoveFrom) {
        while (!toRemoveFrom.isEmpty()) {
            toPutInto.push(toRemoveFrom.remove());
        }
    }

    // Behavior: 
    //   - This method deletes a specified number of songs from the user's music history, 
    //     based on user input. A positive number deletes songs from the most recent 
    //     history, while a negative number deletes songs from the earliest history. 
    //     If the specified number exceeds the size of the history, an exception is thrown.
    // Parameters:
    //   - console: the Scanner accepting user input for features such as choosing how many
    //              songs to delete. 
    //   - musicHistory: the Stack of strings storing songs that have been played.
    // Returns: none
    // Exceptions: 
    //   - IllegalArgumentException: thrown if the user attempts to delete more songs than 
    //                               exist in the music history.
    public static void deleteHistory(Scanner console, Stack<String> musicHistory) {
        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        String toDeleteString = console.nextLine();
        int toDelete = Integer.parseInt(toDeleteString);
        if (Math.abs(toDelete) > musicHistory.size()) {
            throw new IllegalArgumentException("you are deleting too many items!");
        }
        if (toDelete > 0) {
            for (int i = 0; i < toDelete; i++) {
                musicHistory.pop();
            }
        }
        else if (toDelete < 0) {
            Stack<String> auxiliaryStack = new Stack<>();
            stackToStack(musicHistory, auxiliaryStack);
            for (int i = 0; i > toDelete; i--) {
                auxiliaryStack.pop();
            }
            stackToStack(auxiliaryStack, musicHistory);
        }
    }

    // Behavior: 
    //   - This method transfers all elements from one stack to another.
    // Parameters:
    //   - toRemoveFrom: the Stack of strings containing elements to be transferred.
    //   - toPutInto: the Stack of strings where elements from the source stack will be placed.
    // Returns: none
    // Exceptions: none
    public static void stackToStack(Stack<String> toRemoveFrom, Stack<String> toPutInto) {
        while (!toRemoveFrom.isEmpty()) {
            toPutInto.push(toRemoveFrom.pop());
        }
    }
}
