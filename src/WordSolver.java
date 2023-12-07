import java.util.ArrayList;

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

        // Keeps track of visited cells
        boolean[][] visited = new boolean[4][4];

        // Makes a traversal of the board, starting at each cell
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                traverseCell(i, j, board, visited, foundWords, WordDatabase.getRoot(), 0, new ArrayList<Location>());
            }
        }

        // Puts all elements from the foundWords ArrayList into the myWords array
        for (int i = 0; i < 20; i++) {
            myWords[i] = foundWords.get(i);
        }
        return myWords;
    }
    
    /**
     * Recursively traverses each cell, finding every path possible and comparing words to the hashset
     * @param row
     * @param col
     * @param board
     * @param visited
     * @param foundWords
     * @param node
     * @param offset
     * @param path
     */
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
        // Will not visit a cell twice
        if (visited[row][col])
            return;

        WordDatabase.TrieNode nextNode = null;

        // Cells are only traversed if doing so will generate a word that is in the trie
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

        // If the word generated by the traversal is valid, add it to foundWords
        if (nextNode.getWord() != null && offset == nextNode.getFragment().length - 1 && (nextNode.getWord().length() > findShortestLength(foundWords) || foundWords.size() < 20)) {
            Word found = new Word(nextNode.getWord().toUpperCase());
            found.setPath((ArrayList<Location>)path.clone());
            // The first 20 found words are put into foundWords
            if (foundWords.size() < 20 && !containsWord(foundWords, found)) {
                foundWords.add(found);
            // After foundWords is full, the shortest word is replaced by a longer word if a longer word is found
            } else if (found.getWord().length() > findShortestLength(foundWords) && !containsWord(foundWords, found)){
                foundWords.set(findShortest(foundWords), found);
            }
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

    /**
     * Finds if a word exists in an ArrayList
     * @param foundWords
     * @param word
     * @return
     */
    public static boolean containsWord(ArrayList<Word> foundWords, Word word) {
        boolean contains = false;
        for (int i = 0; i < foundWords.size(); i++) {
            if (foundWords.get(i).getWord().equals(word.getWord())) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Finds the location of the shortest word in the ArrayList
     * @param foundWords
     * @return
     */
    public static int findShortest(ArrayList<Word> foundWords) {
        int length = 17;
        int index = 0;
        for (int i = 0; i < foundWords.size(); i++) {
            if (foundWords.get(i).getWord().length() < length) {
                length = foundWords.get(i).getWord().length();
                index = i;
            }
        }
        return index;
    }

    /**
     * Finds the length of the shortest word in the ArrayList
     * @param foundWords
     * @return
     */
    public static int findShortestLength(ArrayList<Word> foundWords) {
        int length = 1000;
        for (int i = 0; i < foundWords.size(); i++) {
            if (foundWords.get(i).getWord().length() < length) {
                length = foundWords.get(i).getWord().length();
            }
        }
        if (foundWords.size() == 0) {
            return 1000;
        } else {
            return length;
        }
    }
}
