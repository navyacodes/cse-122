// Navya Jain 
// 10/03/2024
// CSE 122 
// C0: Warm Up
// TA: Caleb Hsu

import java.util.*;

// This class composes a song in a 2D array and prints it. It takes in the number of  
// melodies, the length of each melody, and the notes for each melody as specified by  
// the user. It also tracks the most common natural note used in each melody.
public class MusicBox {
    public static final String NOTES = "CDEFGAB";
    public static final String SHARP = "♯";
    public static final String FLAT = "♭";
    
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String[][] song = composeSong(console);
        System.out.println();
        System.out.println("Returned song 2D array:");
        for (int i = 0; i < song.length; i++) {
            for (int j = 0; j < song[0].length; j++) {
                System.out.print(song[i][j] + " "); 
            }
            System.out.println();
        }
      String[] commonNaturals = mostCommonNaturals(song);
    }

    // Behavior: 
    //   - This method calculates the most common natural for each melody that appears 
    //     in a user-inputted song. It does this by counting how many times each natural 
    //     note appears in the considered melody, and comparing these counts amongst 
    //     each other.
    // Parameters:
    //   - song: the user's complete String[][] (2D array) representing the song inputted
    // Returns:
    //   - String[]: the most common natural notes used in each melody, with each melody
    //               corresponding to one natural note. 
    // Exceptions: none
    public static String[] mostCommonNaturals(String[][] song){
        String[] commonNaturals = new String[song.length];

        for (int i = 0; i < song.length; i++){
            int[] naturalsCount = new int[NOTES.length()];
            for (int j = 0; j < song[i].length; j++){
                String fullNote = song[i][j];
                countNaturals(naturalsCount, fullNote);
                int indexGreatestOccur = compareNaturalCounts(naturalsCount);
                char mostCommonNaturalChar= NOTES.charAt(indexGreatestOccur);
                String mostCommonNatural = String.valueOf(mostCommonNaturalChar);
                commonNaturals[i] = mostCommonNatural;
            }
        }
        return commonNaturals;
    }
    // Behavior: 
    //   - This method identifies which natural note appears the most by comparing the 
    //     number of times each natural note has appeared in a melody. When two notes 
    //     are tied, the note appearing first in CDEFGAB is chosen as the one appearing
    //     most. 
    // Parameters:
    //   - naturalsCount: an int array where each index corresponds to a natural 
    //                    note, and the value of the number represents how often
    //                    that note appeared.
    // Returns:
    //   - int: the index of the letter in CDEFGAB that corresponds to the most 
    //          common natural in the given melody
    // Exceptions: none
    public static int compareNaturalCounts(int[] naturalsCount){
        int greatestOccurrence = naturalsCount[0];
        int indexGreatestOccur = 0;
        for (int k = 0; k < naturalsCount.length; k++){
            if (greatestOccurrence < naturalsCount[k]){
                greatestOccurrence = naturalsCount[k];
                indexGreatestOccur = k;
            }
        }
        return indexGreatestOccur;
    }
    // Behavior: 
    //   - This method counts how many times each natural note appears. It does 
    //     this by identifying whether a note is natural. If it is natural, then 
    //     the method counts the note as one tally for the note it represents. 
    // Parameters:
    //   - naturalsCount: an int array where each index corresponds to a natural 
    //                    note, and the value of the number represents how often
    //                    that note appeared.
    //   - fullNote: the current note being checked in the array song.
    // Returns: none
    // Exceptions: none
    public static void countNaturals(int[] naturalsCount, String fullNote){
        if (fullNote.length() == 1){
            char note = fullNote.charAt(0);
            int noteIndex = NOTES.indexOf(note);
            naturalsCount[noteIndex] += 1;
        }
    }
    // Behavior: 
    //   - This method composes a song in a 2D array. It does this taking in the number of  
    //     melodies, the length of each melody, and the notes for each melody as specified by  
    //     the user.
    // Parameters:
    //   - console: the Scanner taking user input such as the length and number of melodies.
    // Returns: 
    //   - String[][]: this returns the user-inputted song as a 2D array. 
    // Exceptions: none
    public static String[][] composeSong(Scanner console){
        System.out.print("Enter the number of melodies: ");
        String mNumInput = console.nextLine();
        int melodyNum = Integer.parseInt(mNumInput);
        System.out.print("Enter the length of each melody: ");
        String mLengthInput = console.nextLine();
        int melodyLength = Integer.parseInt(mLengthInput);  
        String[][] song = new String[melodyNum][melodyLength];
        if (melodyLength > 0 && melodyNum > 0){
            assignSongValues(melodyNum, melodyLength, console, song);
        }
        return song;
    }

    // Behavior: 
    //   - This method asks for user input for each note of each melody, and fills these 
    //     notes into the output array. 
    // Parameters:
    //   - melodyNum: the number of melodies included in the song, requested by user. 
    //   - melodyLength: the length of the melodies included in the song, requested by user.  
    //   - console: the Scanner that takes user input such as which notes to include.
    //   - song: the String[][] (String array) tracking which notes the user inputs in
    //           each melody
    // Returns: none 
    // Exceptions: none
    public static void assignSongValues(int melodyNum, int melodyLength,
                                            Scanner console, String[][] song){
        for (int i = 0; i < melodyNum; i++){
            System.out.println();
            System.out.println("Composing melody #" + (i + 1));
            for (int j = 0; j < melodyLength; j++){
                System.out.print("  Enter note #" + (j + 1) + ": ");
                String noteInput = console.nextLine();
                song[i][j] = noteInput;
            }
        }
    }
}
