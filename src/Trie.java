import java.util.Optional;



public class Trie {

    //CHAR_SIZE will be 95, since the trie will store ascii values from 32-126, which
    //include lowercase letters, uppercase letters, and symbols.


    //could be useful in prefix matching transactions, but unsure of performance differences yet
    //probably has bad space complexity  ;.;
    final int CHAR_SIZE = 95;
    public class TrieNode {
        boolean isEnd;
        Optional<TrieNode>[] children;
        public TrieNode() {
            isEnd = false;
            children = new Optional[CHAR_SIZE];
            for(int i = 0; i < CHAR_SIZE; i++) {
                children[i] = Optional.empty();
            }
        }
    }
    private TrieNode root = new TrieNode();

    public boolean contains(String word) {
        TrieNode newRoot = root;
        for(int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 32;
            if(newRoot.children[index].isEmpty()) {
                return false;
            }
            newRoot = newRoot.children[index].get();
        }
        return newRoot.isEnd;
    }
    //returns false if already in trie, true if not in trie
    public boolean insert(String word) {
            TrieNode newRoot = root;
            for(int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 32;
                if (newRoot.children[index].isEmpty()) {
                    TrieNode newNode = new TrieNode();
                    newRoot.children[index] = Optional.of(newNode);
                }
                newRoot = newRoot.children[index].get();
            }
            if(newRoot.isEnd) return false;
            else {
                newRoot.isEnd = true;
                return true;
            }
    }
}
