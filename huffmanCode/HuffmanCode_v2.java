

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A class to represent the HuffmanCode. Performs Huffman code encoding and decoding on given alphabet
 */
public class HuffmanCode {

    /**
     * The root node of the huffmanTree
     */
    Node huffmanTreeRoot;
    /**
     * A map that has one to one mapping between an english alphabet and its huffman code string
     * This is useful while encoding a string
     */
    Map<Character, String> huffmanCodesEncode;

    /**
     * A map that has one to one mapping between a huffman code and its corresponding english alphabet
     * This is useful while decoding a string
     */
    Map<String, Character> huffmanCodesDecode;

    /**
     * Initializes the HuffmanCode class
     *
     * @param a the alphabet for which encoding is required
     * @param f the frequency of the alphabet
     */
    public HuffmanCode(char[] a, int[] f) {
        huffmanCodesEncode = new HashMap<>();
        huffmanCodesDecode = new HashMap<>();
        // forms huffman codes tree
        huffmanTreeRoot = formHuffmanCodesTree(a, f);
        // forms encoding and decoding map
        formCodesMap(huffmanTreeRoot, "");
    }

    /**
     * This method takes the alphabet and frequency of the alphabet and creates a huffman code tree for the given alphabet
     *
     * @param a the alphabet array
     * @param f the alphabet frequency array
     * @return the root of the huffman code tree
     */
    private Node formHuffmanCodesTree(char[] a, int[] f) {
        // priority queue that prioritizes the Tree based on the frequency of the characters in the tree
        PriorityQueue<Tree> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(t -> t.root.freq));
        // add all characters' tree in the priority queue
        for (int i = 0; i < a.length; i++) {
            Tree tree = new Tree(1, 0, a[i], new Node(a[i], f[i]));
            priorityQueue.add(tree);
        }
        for (int i = 1; i < a.length; i++) {
            // get the two least frequency nodes and merge them into one
            Tree min1 = priorityQueue.remove();
            Tree min2 = priorityQueue.remove();
            boolean min1Right = true;
            // apply rules to get the right heavy tree
            if (min2.height > min1.height) {
                min1Right = false;
            } else if (min2.height == min1.height) {
                if (min2.nodesCount > min1.nodesCount) {
                    min1Right = false;
                } else if (min2.nodesCount == min1.nodesCount) {
                    if (min2.asciiValueSum > min1.asciiValueSum) {
                        min1Right = false;
                    }
                }
            }
            Node right = min2.root;
            Node left = min1.root;
            if (min1Right) {
                right = min1.root;
                left = min2.root;
            }
            int freq = right.freq + left.freq;
            // merge two trees
            Node node = new Node(freq);
            node.left = left;
            node.right = right;
            /*
            update the height, nodes count, ascii value sum and root of the tree of any one of the tree with new values
            and add it back to the priority queue
             */
            min1.root = node;
            min1.height = Math.max(min1.height, min2.height) + 1;
            min1.nodesCount = min1.nodesCount + min2.nodesCount + 1;
            min1.asciiValueSum = min1.asciiValueSum + min2.asciiValueSum;
            priorityQueue.add(min1);

        }
        // return the root of the huffman tree
        return priorityQueue.remove().root;
    }

    /**
     * Populates the huffman encode and decode maps using the huffman codes tree
     *
     * @param root The current node of the tree
     * @param code The huffman code of the current node
     */
    private void formCodesMap(Node root, String code) {
        // if the node alphabet is not the default one, get the code string and add it to the maps
        if (root.alphabet != '\u0000') {
            huffmanCodesEncode.put(root.alphabet, code);
            huffmanCodesDecode.put(code, root.alphabet);
            return;
        }
        // adding 0 when going left
        formCodesMap(root.left, code + "0");
        // adding 1 when going right
        formCodesMap(root.right, code + "1");
    }

    /**
     * This method prints the huffman codes of the all alphabets
     */
    public void printCodeWords() {
        // call the recursive method with huffman code tree root as the start point
        printCodesRec(huffmanTreeRoot);
    }

    /**
     * This method prints the huffman codes of the all alphabets with pre-order traversal
     *
     * @param root the current node in the tree
     */
    private void printCodesRec(Node root) {
        // if the current alphabet is not the default one, print the huffman code of the alphabet
        if (root.alphabet != '\u0000') {
            System.out.printf("%c[%d]:%s (%d)\n", root.alphabet, (int) root.alphabet, huffmanCodesEncode.get(root.alphabet), root.freq);
            return;
        }
        // recursive left call
        printCodesRec(root.left);
        // recursive right call
        printCodesRec(root.right);

    }

    /**
     * This method encodes the given text with the huffman codes and returns the encoded string
     *
     * @param text The text that needs to be encoded
     * @return The encoded string
     */
    public String encode(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char c : chars) {
            stringBuilder.append(huffmanCodesEncode.get(c));
        }
        return stringBuilder.toString();
    }

    /**
     * This method decodes the given huffman codes string to the normal string
     *
     * @param text The text that needs to be decoded
     * @return The decoded string
     */
    public String decode(String text) {
        String s = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : text.toCharArray()) {
            s = s + c;
            Character character = huffmanCodesDecode.get(s);
            if (character != null) {
                s = "";
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }
}

/**
 * A class to represent a tree
 * This class helps to avoid repeated calculation of height of the tree, number of nodes in tree and ascii sum of the characters in the tree by storing the information
 */
class Tree {
    /**
     * Number of nodes in this tree
     */
    int nodesCount;
    /**
     * The height of this tree
     */
    int height;
    /**
     * The ascii sum of every character in the current tree
     */
    int asciiValueSum;
    /**
     * The root node of the tree
     */
    Node root;

    /**
     * Creates a new tree
     *
     * @param nodesCount    the count of nodes in the tree
     * @param height        the height of the tree
     * @param asciiValueSum the sum of asciiValues of all characters in the tree
     * @param root          the root node of the tree
     */
    public Tree(int nodesCount, int height, int asciiValueSum, Node root) {
        this.nodesCount = nodesCount;
        this.height = height;
        this.asciiValueSum = asciiValueSum;
        this.root = root;
    }
}

/**
 * A class to represent the Node of a tree
 */
class Node {
    /**
     * Stores the frequency of the alphabet
     */
    int freq;
    /**
     * Stores the alphabet
     */
    char alphabet;
    /**
     * the left node of the current node
     */
    Node left;
    /**
     * the right node of the current node
     */
    Node right;

    /**
     * creates a node with the given frequency
     *
     * @param freq The sum of frequency of all characters in the current tree
     */
    public Node(int freq) {
        this.freq = freq;
    }

    /**
     * creates a node with given alphabet and frequency
     *
     * @param alphabet the alphabet
     * @param freq     the frequency of the alphabet
     */
    public Node(char alphabet, int freq) {
        this.freq = freq;
        this.alphabet = alphabet;
    }
}
