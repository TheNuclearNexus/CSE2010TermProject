import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.concurrent.Flow.Subscriber;

public class WordSolver {

    // visited matrix
    // mark the cell as visited
    // check neighbors
    // unmark cell

    // Finds all possible combinations in the boggle board and compares them to the dictionary file
    public static Word[] solve(char[][] board) {
        Word[] myWords = new Word[20];
        
        // List of valid words that are found in the boggle board
        ArrayList<Word> foundWords = new ArrayList<>();

        // Priority queue of valid words found in the boggle board
        // PriorityQueue<Word> solvedWords = new PriorityQueue<>((s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // Keeps track of visited cells
        boolean[][] visited = new boolean[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }

        /*
         *  WRLN
            EIRG
            OATU
            IAAR
         */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                traverseCell(i, j, board, visited, foundWords, WordDatabase.getRoot(), 0, new ArrayList<Location>());
            }
        }
        // System.out.println(foundWords);

        // Sorts the words so that duplicates can easily be removed
        foundWords.sort(Comparator.comparing(Word::getWord));
        // Removes the duplicates
        foundWords = removeDuplicates(foundWords);
        // Sorts foundWords in order from longest to shortest
        foundWords.sort((a, b) -> {
            return a.getWord().length() - b.getWord().length();
        });


        myWords = foundWords
            .subList(Math.max(foundWords.size() - 20, 0), foundWords.size())
            .toArray(myWords);

        return myWords;
    }
    
    // Recursively traverses each cell, finding every path possible and comparing words to the hashset
    private static void traverseCell(
            int row,
            int col,
            char[][] board,
            boolean[][] visited,
            ArrayList<Word> foundWords,
            WordDatabase.TrieNode node,
            int offset,
            ArrayList<Location> path) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4)
            return;

        if (visited[row][col])
            return;

        WordDatabase.TrieNode nextNode = null;
        if (node.getFragment() == null || (node.getFragment().length == 1 && node.getFragment()[0] == Character.toLowerCase(board[row][col])) || offset == node.getFragment().length - 1) {
            offset = 0;
            for (WordDatabase.TrieNode child : node.getChildren()) {
                if (child.getFragment()[0] == Character.toLowerCase(board[row][col])) {
                    nextNode = child;
                    break;
                }
            }
        } else {
            offset++;
            if (node.getFragment()[offset] != Character.toLowerCase(board[row][col])) {
                return;
            }
            nextNode = node;
        }
        
        if (nextNode == null) {
            return;
        }

        Location curLocation = new Location(row, col);

        
        visited[row][col] = true;
        path.add(curLocation);

        if (nextNode.getWord() != null && offset == nextNode.getFragment().length - 1) {
            // System.out.println(nextNode.getWord());
            Word found = new Word(nextNode.getWord().toUpperCase());

            found.setPath((ArrayList<Location>)path.clone());
            foundWords.add(found);
        }

        

        // Each unvisited adjacent cell is traversed
        traverseCell(row - 1, col - 1, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row - 1, col, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row - 1, col + 1, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row, col - 1, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row, col + 1, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row + 1, col - 1, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row + 1, col, board, visited, foundWords, nextNode, offset, path);
        traverseCell(row + 1, col + 1, board, visited, foundWords, nextNode, offset, path);
        
        path.remove(curLocation);
        visited[row][col] = false;
    }

    // print method to trace the path
    private static void print(boolean[][] visited) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(visited[i][j] ? "x" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Removes the duplicates from the foundWords arrayList
    public static ArrayList<Word> removeDuplicates(ArrayList<Word> a) {
        ArrayList<Word> noDuplicates = new ArrayList<>();
        if (!a.isEmpty()) { // nothing needs to happen if the array is empty
            for (int i = 0; i < a.size() - 1; i++) {
                if (!a.get(i).getWord().equals(a.get(i + 1).getWord())) {
                    noDuplicates.add(a.get(i));
                }
            }
            noDuplicates.add(a.get(a.size() - 1));
        }        
        return noDuplicates;
    }
}
