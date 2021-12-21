import java.io.*;

public class main {
    public static void main(String[] args) throws IOException {
        GraphNode Source = new GraphNode(7);
        RootedTree rt = new RootedTree();
        rt.setSource(Source);
        GraphNode l1 = new GraphNode(4);
        GraphEdge sl1 = new GraphEdge(Source, l1);
        GraphNode r1 = new GraphNode(2);
        GraphEdge sr1 = new GraphEdge(Source, r1, sl1);
        GraphNode l2 = new GraphNode(1);
        GraphEdge l1l2 = new GraphEdge(l1, l2);
        GraphNode rl2 = new GraphNode(5);
        GraphEdge r1l2 = new GraphEdge(r1, rl2);
        GraphNode l3 = new GraphNode(9);
        GraphNode llr3 = new GraphNode(8);
        GraphEdge l1l2l3 = new GraphEdge(l2, l3);
        GraphEdge l1l2r3 = new GraphEdge(l2, llr3, l1l2l3);




        File f = new File("test.txt");
        FileOutputStream fo = new FileOutputStream(f);
        DataOutputStream dos = new DataOutputStream(fo);
        rt.printByLayer(dos);
        fo.close();
        dos.close();
        FileInputStream fi = new FileInputStream(f);
        DataInputStream di = new DataInputStream(fi);
        System.out.println(di.readInt());
        System.out.println(di.readInt());
        System.out.println(di.readChar());
        System.out.println(di.readInt());
        System.out.println(di.readInt());
        System.out.println(di.readChar());
        System.out.println(di.readInt());
        System.out.println(di.readInt());
        System.out.println(di.readChar());
        System.out.println(di.readInt());
        di.close();



    }
}
