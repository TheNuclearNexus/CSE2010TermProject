import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class WordDatabase {
    private static final TrieNode root = new TrieNode();

    public static void main(String[] args) throws IOException {
        loadFile("input/words.txt");

        assert contains("oat") == true;
        System.out.println(contains("wreat"));
    }

    /**
     * Loads a given file path into the database.  We are using a Trie 
     * @param file
     * @throws IOException
     */
    public static void loadFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            char[] word = line.toLowerCase().toCharArray();

            if (word.length < 3 || word.length > 16)
                continue;
            
            // words.add(word.replace("qu", "q"));

            TrieNode root = WordDatabase.root;
            for (int i = 0; i < word.length; i++) {
                int curChar = (word[i] - 97) % 26;

                if (root.children[curChar] == null)
                    root.children[curChar] = new TrieNode();
                root = root.children[curChar];
            }
            root.word = line.toLowerCase();
        }

        reader.close();
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
        return root.word != null;
    }

    public static TrieNode getRoot() {
        return root;
    }
    
    static class TrieNode {
        // private char data;
        private String word;
        private TrieNode[] children;

        public TrieNode() {
            children = new TrieNode[26];
        }

        public String getWord() {
            return word;
        }

        public TrieNode[] getChildren() {
            return children;
        }

        public TrieNode getChild(char c) {
            return children[(int)Character.toLowerCase(c) - 97];
        }
    }
}

