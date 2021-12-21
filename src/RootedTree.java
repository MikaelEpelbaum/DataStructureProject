import java.io.DataOutputStream;
import java.io.IOException;

class RootedTree {

    public GraphNode Source;

    public RootedTree(){}

    public void setSource(GraphNode source) {
        this.Source = source;
    }

//    public void printByLayer(DataOutputStream out) throws IOException {
//        printByLayerRecursive(out, Source);
//    }

    /* Function to line by line print level order traversal a tree*/
    public void printByLayer(DataOutputStream out)throws IOException{
        int h = maxDepth(Source) + 1;
        for (int i=1; i<=h; i++)
        {
            printGivenLevel(out, Source, i);
//            System.out.println();
        }
    }
    /* Print nodes at a given level */
    void printGivenLevel(DataOutputStream out, GraphNode root, int level)throws IOException{
        if (root == null)
            return;
        if (level == 1) {
//            System.out.println(root.getKey());
            out.writeInt(root.getKey());

        }
        else if (level > 1)
        {
            printGivenLevel(out, root.OutEdge.Destination, level-1);
            try {
                if (root.OutEdge.NextEdge.Destination != null) {
                    out.writeChars(",");
                    printGivenLevel(out, root.OutEdge.NextEdge.Destination, level - 1);
                }
            }
            catch (NullPointerException e) {return;}
        }
    }


//    public void printByLayerRecursive(DataOutputStream out, GraphNode s)throws IOException{
//        out.writeInt(s.getKey());
//        if (s.OutEdge == null){
//            return;
//        }
//
//        GraphEdge OutEdge = s.OutEdge;
//        while (OutEdge.NextEdge != null){
//            te(out, OutEdge.Destination);
//            OutEdge = OutEdge.NextEdge;
//        }
//        OutEdge = s.OutEdge;
//        while (OutEdge.NextEdge != null){
//            printByLayerRecursive(out, OutEdge.Destination);
//        }
//        out.writeInt(OutEdge.Destination.getKey());
//    }
//
//    private void te(DataOutputStream out, GraphNode s)throws IOException{
//        out.writeInt(s.getKey());
//        out.writeChars(",");
//    }

    public int maxDepth(GraphNode treeNode) {
        int depth = 0;
        if (treeNode.OutEdge == null)
            return depth;
        else {
            GraphEdge outers = treeNode.OutEdge;
            while (outers != null){
                depth = Math.max(depth, this.maxDepth(outers.Destination));
                outers = outers.NextEdge;
            }
            return depth + 1;
        }
    }


    public void preorderPrint(DataOutputStream out) {}

}