/*

  Authors (group members): Evan Gunderson, Timothy Shane, Younghoon Cho, Yavanni Ensley 
  Email addresses of group members:
  Group name: Group #1

  Course: Data Structures and Algorithms
  Section: 3/4

  Description of the overall algorithm and key data structures:


*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BogglePlayer {
    private HashSet<String> words = new HashSet<>();

    // initialize BogglePlayer with a file of English words
    public BogglePlayer(String wordFile) throws FileNotFoundException {
        loadFile(wordFile);
    }

    private void loadFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();

            if (word.length() < 3 || word.length() > 16)
                continue;

            word = word.toLowerCase();

            words.add(word);
        }

        scanner.close();
    }

    // based on the board, find valid words
    //
    // board: 4x4 board, each element is a letter, 'Q' represents "QU",
    // first dimension is row, second dimension is column
    // ie, board[row][col]
    //
    // Return at most 20 valid words in UPPERCASE and
    // their paths of locations on the board in myWords;
    // Use null if fewer than 20 words.
    //
    // See Word.java for details of the Word class and
    // Location.java for details of the Location class

    public Word[] getWords(char[][] board) {
        return WordSolver.solve(board);
    }
}
