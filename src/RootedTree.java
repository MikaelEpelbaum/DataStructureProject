import java.io.DataOutputStream;
import java.io.IOException;

class RootedTree {

    public GraphNode Source;

    public RootedTree(){}

    public void setSource(GraphNode source) {
        this.Source = source;
        source.setExtreme();
    }

    /* Function to line by line print level order traversal a tree*/
    public void printByLayer(DataOutputStream out)throws IOException{
        int h = maxDepth(Source) + 1;                                   /*o(n) if tree is linear*/
        for (int i=1; i<=h; i++){                                       /*o(n) if tree is linear*/
            printGivenLevel(out, Source, i);
            if(i!=h)
                out.writeUTF("\n");
        }
    }

    public void preorderPrint(DataOutputStream out)throws IOException {
        preorderPrintRecursive(out, Source);
    }

    public void preorderPrintRecursive(DataOutputStream out, GraphNode g)throws IOException {
        if ( g == null)
            return;
        else {
            if (g.getInDegree() == 0 && g.isExtremeLeft)
//            if(g.isExtremeLeft)
                out.writeUTF(String.valueOf(g.getKey()));
            else {
                out.writeUTF(",");
                out.writeUTF(String.valueOf(g.getKey()));
            }
            LinkedListQueue<GraphEdge> kids = g.OutEdge;
            try{
                GraphEdge kid = kids.getFront().data;
                while (kid != null){
                    preorderPrintRecursive(out, kid.Destination);
                    kid = kid.NextEdge;
                }
            }
            catch (NullPointerException e) {return;}
        }
    }


    /* Print nodes at a given level */
    private void printGivenLevel(DataOutputStream out, GraphNode root, int level)throws IOException{
        if (root == null)
            return;
        if (level == 1) {
            if(!root.isExtremeLeft)
                out.writeUTF(",");
            out.writeUTF(String.valueOf(root.getKey()));
        }
        else if (level > 1)
        {
            printGivenLevel(out, root.OutEdge.getFront().data.Destination, level-1);
            try {
                GraphEdge nextEdge = root.OutEdge.getFront().data.NextEdge;
                while (nextEdge != null) {
                    printGivenLevel(out, nextEdge.Destination, level - 1);
                    nextEdge = nextEdge.NextEdge;
                }
            }
            catch (NullPointerException e) {return;}
        }
    }


    private int maxDepth(GraphNode treeNode) {
        int depth = 0;
        if (treeNode.OutEdge.getSize() == 0)
            return depth;
        else {
            GraphEdge outers = treeNode.OutEdge.getFront().data;
            while (outers != null){
                depth = Math.max(depth, this.maxDepth(outers.Destination));
                outers = outers.NextEdge;
            }
            return depth + 1;
        }
    }



}