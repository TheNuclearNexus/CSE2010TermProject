/*

  Authors (group members): Evan Gunderson, Timothy Shane, Younghoon Cho, Yavanni Ensley 
  Email addresses of group members: yensley2022@my.fit.edu ycho2021@my.fit.edu egunderson2022@my.fit.edu tshane2022@my.fit.edu
  Group name: Group #1

  Course: cse2010
  Assigment: termProjectS34Initial

  Description of the overall algorithm and key data structures: We are currently using a HashSet to store all the words, and are using a brute-force algorithm to try every possible combination in the boggle board.


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
