/*

  Authors (group members): Evan Gunderson, Timothy Shane, Younghoon Cho, Yavanni Ensley 
  Email addresses of group members: yensley2022@my.fit.edu ycho2021@my.fit.edu egunderson2022@my.fit.edu tshane2022@my.fit.edu
  Group name: Group #1

  Course: cse2010
  Assigment: termProjectS34Final

  Description of the overall algorithm and key data structures: All words in the dictionary are put into a trie, after filtering out all words that are less than 3 letters and more than 16 letters.  The trie is then 
  compressed to save memory space.  The program then finds paths, starting at each letter on the board.  If a path will generate a word that is NOT in the trie, the program will skip that path, ensuring that no time is
  wasted generating paths that do not make a valid word.  To save space, the program only keeps track of the 20 longest words, which are then returned in an array.


*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class BogglePlayer {

    // initialize BogglePlayer with a file of English words
    public BogglePlayer(String wordFile) throws IOException {
        WordDatabase.loadFile(wordFile);
        WordDatabase.compress();
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

    // returns the results
    public Word[] getWords(char[][] board) {
        return WordSolver.solve(board);
    }
}
