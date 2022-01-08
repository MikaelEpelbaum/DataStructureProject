import java.io.*;

public class main {
    public static void main(String[] args) throws IOException {
        GraphNode Source = new GraphNode(7);
        RootedTree rt = new RootedTree();
        rt.setSource(Source);
        GraphNode l1 = new GraphNode(4);
        new GraphEdge(Source, l1);
        GraphNode r1 = new GraphNode(2);
        new GraphEdge(Source, r1);
        GraphNode l2 = new GraphNode(1);
        new GraphEdge(l1, l2);
        GraphNode rl2 = new GraphNode(5);
        new GraphEdge(r1, rl2);
        GraphNode l3 = new GraphNode(9);
        GraphNode llr3 = new GraphNode(8);
        new GraphEdge(l2, l3);
        new GraphEdge(l2, llr3);

        GraphNode llr4 = new GraphNode(10);
        new GraphEdge(l2, llr4);




        File f = new File("test.txt");
        FileOutputStream fo = new FileOutputStream(f);
        DataOutputStream dos = new DataOutputStream(fo);
        rt.printByLayer(dos);

//        rt.preorderPrint(dos);
        fo.close();
        dos.close();
        FileInputStream fi = new FileInputStream(f);
        DataInputStream di = new DataInputStream(fi);
        String printable;
        try {
            printable = di.readUTF();
            while (printable != null) {
                System.out.print(printable);
                printable = di.readUTF();
            }
        } catch (IOException e) {}
        di.close();



    }
}
