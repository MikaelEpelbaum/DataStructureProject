import java.io.DataOutputStream;
import java.io.IOException;

class RootedTree {

    public GraphNode Source;
    private boolean last;

    public RootedTree(){
        this.last = true;
    }

    public void setSource(GraphNode source) {
        this.Source = source;
        source.setExtreme();
    }

    /* Function to line by line print level order traversal a tree*/
    public void printByLayer(DataOutputStream out)throws IOException{
        try{
            int h;
            if (Source.getKey() == 0)
                h = (int) Source.getDistance()+1;
            else {
                h = maxDepth(Source) + 1;
                decolor(Source);
            }
            for (int i=1; i<=h; i++){
                printGivenLevel(out, Source, i);
                if(i!=h){
                    last = true;
                    out.writeUTF("\n");
                }
            }
        } catch (NullPointerException e) {}
    }

    public void preorderPrint(DataOutputStream out)throws IOException {
        preorderPrintRecursive(out, Source);
        decolor(Source);
    }

    protected void decolor(GraphNode s){
        if (s.getColor() == 0)
            return;
        try{
            s.setColor(0);
            GraphEdge kid = s.OutEdge.getFront().data;
            while (kid != null){
                decolor(kid.Destination);
                kid = kid.NextEdge;
            }
        }
        catch (NullPointerException e) {return;}
    }

    public void preorderPrintRecursive(DataOutputStream out, GraphNode g)throws IOException {
        if ( g == null || g.getColor()>1)
            return;
        else {
            if (g.getInDegree() == 0 && g.isExtremeLeft)
                out.writeUTF(String.valueOf(g.getKey()));
            else {
                out.writeUTF(",");
                out.writeUTF(String.valueOf(g.getKey()));
                g.setColor(2);
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
            if(root.getColor() != 1){
                if(!last)
                    out.writeUTF(",");
                else last = false;
                out.writeUTF(String.valueOf(root.getKey()));
            }
            root.setColor(1);
        }
        else if (level > 1)
        {
            try {
                printGivenLevel(out, root.OutEdge.getFront().data.Destination, level-1);
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
        try {
            if (treeNode.OutEdge.getSize() == 0 || treeNode.getColor() > 0)
                return depth;
            else {
                LinkedListQueue<GraphEdge> outers = treeNode.OutEdge;
                LinkedListQueue<GraphEdge> temp = new LinkedListQueue<>();
                GraphEdge kid = outers.dequeue();
                while (kid!= null){
                    temp.enqueue(kid);
                    treeNode.setColor(1);
                    depth = Math.max(depth, this.maxDepth(kid.Destination));
                    kid = outers.dequeue();
                }
                Queues.transfer(temp, outers);
                return depth + 1;
            }
        }catch (NullPointerException e){ return 0;}
    }



}