import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Huffman {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in0 = new Scanner(System.in);
        //create the queue S
        Queue<BinaryTree<Pair>> S = new LinkedList<>();

        File file = new File("LettersProbability.txt");
        Scanner in = new Scanner(file);

        //read the file and store all of them into the queue S
        while(in.hasNext()){
            BinaryTree<Pair> node = new BinaryTree<>();
            node.setData(new Pair(in.next().charAt(0), in.nextDouble()));
            S.add(node);
        }

        //create the queue T
        Queue<BinaryTree<Pair>> T = new LinkedList<>();

        //creating the Huffman tree
        while(!S.isEmpty()) {
            //A and B are the two nodes with the smallest probability
            BinaryTree<Pair> A = new BinaryTree<>();
            BinaryTree<Pair> B = new BinaryTree<>();

            if (T.isEmpty()) {
                A = S.poll();
                B = S.poll();
            } else {
                //pick one smallest node from S and one from T, and compare them. Select the smallest one as A
                if (S.peek().getData().getProbability() < T.peek().getData().getProbability()) {
                    A = S.poll();
                    //selecting using the same method
                    if (S.peek().getData().getProbability() < T.peek().getData().getProbability()) {
                        B = S.poll();
                    } else if (S.peek().getData().getProbability() > T.peek().getData().getProbability()) {
                        B = T.poll();
                    }
                } else if (S.peek().getData().getProbability() > T.peek().getData().getProbability()) {
                    A = T.poll();
                    //make sure the T is not empty before peaking, if it is empty, poop the smallest node from S
                    if(T.isEmpty()){
                        B = S.poll();
                    }else {
                        if (S.peek().getData().getProbability() < T.peek().getData().getProbability()) {
                            B = S.poll();
                        }else if (S.peek().getData().getProbability() > T.peek().getData().getProbability()) {
                            B = T.poll();
                        }
                    }

                }
            }

            //create the empty nodes in the Huffman tree, and attach A and B to left and right respectively
            BinaryTree<Pair> P = new BinaryTree<>();
            P.setData(new Pair('0', (A.getData().getProbability()) + (B.getData().getProbability())));
            P.attachLeft(A);
            P.attachRight(B);

            T.add(P);
        }

        //when S is empty, but there are still nodes in the T, poop them and add tp the Huffman tree just like the previous steps
        while(T.size() > 1){
            BinaryTree<Pair> A = T.poll();
            BinaryTree<Pair> B = T.poll();
            BinaryTree<Pair> P = new BinaryTree<>();
            P.setData(new Pair('0', (A.getData().getProbability()) + (B.getData().getProbability())));
            P.attachLeft(A);
            P.attachRight(B);
            T.add(P);
        }

        //The last node remaining in the queue T will be the final Huffman tree.
        BinaryTree<Pair> huffman = T.poll();

        //encode the huffman tree
        String[] encoded = findEncoding(huffman);

        //output
        System.out.print("Enter a line of text (uppercase letters only): ");
        String words = in0.nextLine();
        System.out.println("Display the encoded line");
        System.out.print("Here’s the encoded line: ");
        String finalEncoded = "";
        //iterate each character of the user input, and generate the Huffman code for each one, and combine them at last.
        for(int i = 0; i < words.length(); i++){
            if(words.charAt(i) != ' '){
                finalEncoded+= encoded[Integer.valueOf(words.charAt(i))-65];
                System.out.print(encoded[Integer.valueOf(words.charAt(i))-65]);
            }else{
                finalEncoded+= " ";
                System.out.print(" ");
            }
        }
        System.out.println("");
        System.out.print("Next decode the above line and display the original line. ");
        //call the decode function
        System.out.print(findDecoding(finalEncoded, encoded));

    }

    private static String[] findEncoding(BinaryTree<Pair> bt){
        String[] result = new String[26];
        findEncoding(bt, result, "");
        return result;
    }
    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix){
// test is node/tree is a leaf
        if (bt.getLeft()==null && bt.getRight()==null){
            a[bt.getData().getValue() - 65] = prefix;
        }
// recursive calls
        else{
            findEncoding(bt.getLeft(), a, prefix+"0");
            findEncoding(bt.getRight(), a, prefix+"1");
        }
    }

    private static String findDecoding(String finalEncoded, String[] encoded){
        String result = "";
        String curr = "";
        //iterate the Huffman code, and store each "1" or "0" to the "curr" string
        for(int i = 0; i < finalEncoded.length(); i++){
            //when we meet the space, we add the space to the "result"
            if(finalEncoded.charAt(i) == ' '){
                result += " ";
            }else{
                curr += finalEncoded.charAt(i);
                //and compare the "curr" to each characters' Huffman code
                for(int j = 0; j < encoded.length; j++){
                    //once the "curr" equals a character's huffman code, store that character to the "result", and clear the "curr" to empty
                    if(encoded[j].equals(curr)){
                        result += String.valueOf((char)(j + 65));
                        curr = "";
                    }

                }
            }
        }
        return result;
    }
}


