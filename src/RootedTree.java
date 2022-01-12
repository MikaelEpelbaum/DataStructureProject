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
            int h = maxDepth(Source)+1;
            discolor(Source);

            for (int i=1; i<=h; i++){
                printGivenLevel(out, Source, i);
                if(i!=h){
                    last = true;
//                    out.writeUTF("\n");
                    out.writeBytes("\n");
                }
            }
        } catch (NullPointerException e) {}
    }

    public void preorderPrint(DataOutputStream out)throws IOException {
        discolor(Source);
        preorderPrintRecursive(out, Source);
//        discolor(Source);
    }

    public void preorderPrintRecursive(DataOutputStream out, GraphNode g)throws IOException {
        if ( g == null || g.getColor()>1)
            return;
        else {
            //region Printings
            if ((g.getInDegree() == 0 && g.isExtremeLeft) || (g.getParent() == null && g.isExtremeLeft)) {
                out.writeBytes(String.valueOf(g.getKey()));
//                out.writeUTF(String.valueOf(g.getKey()));
                }
            else {
                out.writeBytes(",");
                out.writeBytes(String.valueOf(g.getKey()));
//                out.writeUTF(",");
//                out.writeUTF(String.valueOf(g.getKey()));
                g.setColor(2);
            }
            //endregion
            try{
                Queues.revert(g.OutEdge);
                int len = g.OutEdge.getSize();
                for(int i = 0; i< len; i++){
                    GraphEdge edge = g.OutEdge.dequeue();
                    g.OutEdge.enqueue(edge);
                    if(g == edge.Destination.getParent()) {
                        preorderPrintRecursive(out, edge.Destination);
                    }
                }
                Queues.revert(g.OutEdge);
            }
            catch (NullPointerException e) {return;}
        }
    }

    protected void discolor(GraphNode s){
        if (s.getColor() == 0 && s.getKey() != 0)
            return;
        try{
            s.setColor(0);
            int len = s.OutEdge.getSize();
            for (int i = 0; i< len; i++){
                GraphEdge edge = s.OutEdge.dequeue();
                if(!edge.deleted) {
                    s.OutEdge.enqueue(edge);
                    discolor(edge.Destination);
                }
            }
        }
        catch (NullPointerException e) {return;}
    }

    /* Print nodes at a given level */
    private void printGivenLevel(DataOutputStream out, GraphNode root, int level)throws IOException{
        if (root == null)
            return;
        if (level == 1) {
            if(root.getColor() != 1){
                if(!last) {
                    out.writeBytes(",");
//                    out.writeUTF(",");
                }
                else last = false;
                out.writeBytes(String.valueOf(root.getKey()));
//                out.writeUTF(String.valueOf(root.getKey()));

            }
            root.setColor(1);
        }
        else if (level > 1)
        {
            try {
                if (!root.OutEdge.isEmpty()) {
                    int len = root.OutEdge.getSize();
                    Queues.revert(root.OutEdge);
                    for(int i = 0; i < len; i++){
                        GraphEdge edge = root.OutEdge.dequeue();
                        root.OutEdge.enqueue(edge);
                        if(edge.Destination.getColor() < 1 && level == 2)
                            printGivenLevel(out, edge.Destination, level - 1);
                        if(level > 2)
                            printGivenLevel(out, edge.Destination, level - 1);
                    }
                    Queues.revert(root.OutEdge);
                }
            }
            catch (NullPointerException e) {return;}
        }
    }


//    private int maxDepth(GraphNode s) {
//        int depth = 0;
//        if (s.OutEdge.isEmpty() || s.getColor() > 0)
//            return depth;
//        else {
//            int len = s.OutEdge.getSize();
//            for (int i = 0; i< len; i++){
//                GraphEdge edge = s.OutEdge.dequeue();
//                if(!edge.deleted){
//                    s.OutEdge.enqueue(edge);
//                    s.setColor(1);
//                    if(!edge.Destination.deleted)
//                        depth = Math.max(depth, this.maxDepth(edge.Destination));
//                }
//            }
//            return depth+1;
//        }
//    }
    private int maxDepth(GraphNode s){
        int depth = (int) s.getDistance();
        int len = s.OutEdge.getSize();
        LinkedListQueue.Node node = s.OutEdge.getFront();
        for (int i = 0; i< len; i++){
            GraphEdge edge = (GraphEdge) node.data;
            if(!edge.deleted && !edge.Destination.deleted){
                int temp = 0;
                if(s.getDistance() < edge.Destination.getDistance()) {
                    temp = maxDepth(edge.Destination);
                }
                depth = Math.max(depth, temp);
            }
            node = node.next;
        }
        return depth;
    }
}