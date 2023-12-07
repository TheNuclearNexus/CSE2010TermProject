import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class WordDatabase {
    private static final TrieNode root = new TrieNode();

    public static void main(String[] args) throws IOException {
        loadFile("input/words.txt");
        System.out.println("Done loading");
        compress();
        // System.out.println(root);

        System.out.println(contains("add"));
        System.out.println(contains("wreat"));
        System.out.println(contains("abc"));   
        System.out.println(contains("abiogenesis"));             
    }

    /**
     * Loads a given file path into the database.  We are using a compressed Trie 
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
            // System.out.println("Add word: " + line);
            TrieNode curNode = WordDatabase.root;
            for (int i = 0; i < word.length; i++) {
                char curChar = word[i];

                TrieNode nextNode = null;
                for (TrieNode child : curNode.children) {
                    if (child.fragment[0] == curChar)
                        nextNode = child;
                }

                if (nextNode == null) {
                    nextNode = new TrieNode();
                    nextNode.fragment = new char[] { curChar };
                    curNode.children.add(nextNode);
                }
                    
                curNode = nextNode;
            }
            if (curNode.word == null)
                curNode.word = line.toLowerCase();
        }

        reader.close();
    }

    public static void compress() {
        for (TrieNode c : root.children) 
            compress(c, null);
    }

    static void compress(TrieNode node, TrieNode parent) {
        for (TrieNode n : node.children)
            compress(n, node);

        if (parent != null && parent.children.size() == 1 && parent.word == null) {
            char[] newFragment = new char[parent.fragment.length + node.fragment.length];

            for (int i = 0; i < parent.fragment.length; i++) 
                newFragment[i] = parent.fragment[i];
            for (int i = 0; i < node.fragment.length; i++) 
                newFragment[parent.fragment.length + i] = node.fragment[i];

            parent.fragment = newFragment;
            parent.children = node.children;
            parent.word = node.word;
            return;
        }
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
        
        TrieNode curNode = WordDatabase.root;

        int offset = 0;
        for(int i = 0; i < chars.length; i++) {
            if (curNode.fragment == null || curNode.fragment.length == 1 || offset == curNode.fragment.length - 1) {
                offset = 0;
                TrieNode nextNode = null;
                for (TrieNode child : curNode.children) {
                    if (child.getFragment()[0] == chars[i]) {
                        nextNode = child;
                        break;
                    }
                }

                if (nextNode == null)
                    return false;


                curNode = nextNode;
            } else {
                offset++;
                if (curNode.fragment[offset] != chars[i]) {
                    return false;
                }
            }
            
        }
        System.out.println(curNode.word);
        if (curNode.getFragment().length > 1 && offset != curNode.getFragment().length - 1)
            return false;
        return curNode.word != null;
    }

    public static TrieNode getRoot() {
        return root;
    }
    
    static class TrieNode {
        private char[] fragment;
        private String word;
        private LinkedList<TrieNode> children;

        public TrieNode() {
            children = new LinkedList<>();
        }

        public String getWord() {
            return word;
        }

        public char[] getFragment() {
            return fragment;
        }

        public List<TrieNode> getChildren() {
            return children;
        }

        public TrieNode getChild(char fragment) {
            for (TrieNode node : children) {
                if (node.fragment[0] == Character.toLowerCase(fragment));
                    return node;
            }
            return null;
        }
        public TrieNode getChild(String fragment) {
            for (TrieNode node : children) {
                if (node.fragment.equals(fragment.toLowerCase().toCharArray())) {
                    return node;
                }
            }

            return null;
        }

        public String toString() {
            return toString(0);
        }

        public String toString(int depth) {
            String spaces = " ".repeat(depth * 2);
            String out = "";
            out += spaces + "- fragment=\"" + Arrays.toString(fragment) + "\"\n";
            if (word != null)
                out += spaces + "  word=\"" + word + "\"\n";
            else
                out += spaces + "  word=null\n";

            if (children.size() > 0) {
                out += spaces + "  children:\n";
                for (TrieNode c : children) {
                    out += c.toString(depth + 1);
                }
            }
            return out;
        }
    }
}

