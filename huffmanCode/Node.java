/*
This is a node class in which I am having some fields and a constructor
which assigns values whenever I create a new node
 */
class Node {
    char value;
    Node left;
    Node right;
    int frequency;
    int height;
    int numberOfNodes;
    int asciiValues;

    Node(char value,int frequency) {
        this.value = value;
        this.frequency = frequency;
        this.right = null;
        this.left = null;
        this.height = 0;
        this.numberOfNodes=1;
        if(value != '*'){
            this.asciiValues=value;
        }else{
            this.asciiValues = 0;
        }
    }

}