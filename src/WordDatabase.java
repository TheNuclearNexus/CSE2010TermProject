import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class WordDatabase {
    private static final TrieNode root = new TrieNode();


    /**
     * Loads a given file path into the database
     * @param file
     * @throws FileNotFoundException
     */
    public static void loadFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));

        while (scanner.hasNextLine()) {
            char[] word = scanner.nextLine().toLowerCase().toCharArray();

            if (word.length < 3 || word.length > 16)
                continue;
            
            TrieNode root = WordDatabase.root;
            for (int i = 0; i < word.length; i++) {
                int curChar = (word[i] - 97) % 26;

                if (root.children[curChar] == null)
                    root.children[curChar] = new TrieNode();
                root = root.children[curChar];
            }
        }

        scanner.close();
    }

    /**
     * Checks if a word is valid in the database
     * 
     * @param word
     * @return
     */
    public static boolean contains(String word) {
        if (word.length() < 3 || word.length() > 16)
            return false;

        char[] chars = word.toLowerCase().toCharArray();


        TrieNode root = WordDatabase.root;

        for(int i = 0; i < chars.length; i++) {
            int curChar = (chars[i] - 97) % 26;
            if (root.children[curChar] == null)
                return false;
            root = root.children[curChar];
        }

        return true;
    }
    
    static class TrieNode {
        // private char data;
        private TrieNode[] children;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }
}

