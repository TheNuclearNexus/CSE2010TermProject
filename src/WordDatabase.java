import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class WordDatabase {
    private static final TrieNode root = new TrieNode();
    private static final HashSet<String> words = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        loadFile("input/words.txt");

        System.out.print(contains("oat"));
    }

    /**
     * Loads a given file path into the database.  We are uwing a HashSet at the moment, but will use a trie for the final submission
     * @param file
     * @throws FileNotFoundException
     */
    public static void loadFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().toLowerCase();

            if (word.length() < 3 || word.length() > 16)
                continue;
            
            words.add(word.replace("qu", "q"));

            // TrieNode root = WordDatabase.root;
            // for (int i = 0; i < word.length; i++) {
            //     int curChar = (word[i] - 97) % 26;

            //     if (root.children[curChar] == null)
            //         root.children[curChar] = new TrieNode();
            //     root = root.children[curChar];
            // }
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

        return words.contains(word.toLowerCase());

        // char[] chars = word.toLowerCase().toCharArray();
        
        // TrieNode root = WordDatabase.root;

        // for(int i = 0; i < chars.length; i++) {
        //     int curChar = (chars[i] - 97) % 26;
        //     if (root.children[curChar] == null)
        //         return false;
        //     root = root.children[curChar];
        // }

        // return true;
    }
    
    static class TrieNode {
        // private char data;
        private TrieNode[] children;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }
}

