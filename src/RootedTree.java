import com.sun.jdi.connect.spi.TransportService;

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
        try {
            int h = 0;
            if(Source.getKey() != 0){
                discolor(Source);
                h = maxDepth(Source) + 1;
                discolor(Source);
            }
            else{
                discolorSCC(Source);
                h = maxDepthSCC(Source);
                discolorSCC(Source);
            }
            for (int i = 1; i <= h; i++) {
                if(Source.getKey() != 0)
                    printGivenLevel(out, Source, i);
                else
                    printGivenLevelSCC(out, Source, i);
                if (i != h) {
                    last = true;
//                    out.writeUTF("\n");
                    out.writeBytes("\n");
                }
            }
        } catch (NullPointerException e) {}
    }

    public void preorderPrint(DataOutputStream out)throws IOException {
        if (Source.getKey() != 0){
            discolor(Source);
            preorderPrintRecursive(out, Source);
        }
        else {
            discolorSCC(Source);
            preorderPrintRecursiveSCC(out, Source);
            GraphEdge kid = Source.InEdge.dequeue();
            while(kid != null){
                kid.deleted = true;
                kid = Source.InEdge.dequeue();
            }
            Source.deleted = true;
        }
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
                int len = g.OutEdge.getSize();
                Queues.revert(g.OutEdge);
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

    public void preorderPrintRecursiveSCC(DataOutputStream out, GraphNode g)throws IOException {
        if ( g == null || g.getColor()>1)
            return;
        else {
            if(g.getKey() == 0)
                out.writeBytes(String.valueOf(g.getKey()));
            else {
                out.writeBytes(",");
                out.writeBytes(String.valueOf(g.getKey()));
                g.setColor(2);
            }
            //endregion
            try{
                if(g.getKey() != 0)
                    Queues.revert(g.InEdge);
                int len = g.InEdge.getSize();
                for(int i = 0; i< len; i++){
                    GraphEdge edge = g.InEdge.dequeue();
                    g.InEdge.enqueue(edge);
                    if(g == edge.Origin.getParent()) {
                        preorderPrintRecursiveSCC(out, edge.Origin);
                    }
                }
                if(g.getKey() != 0)
                    Queues.revert(g.InEdge);
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

    /* Print nodes at a given level */
    private void printGivenLevelSCC(DataOutputStream out, GraphNode root, int level)throws IOException{
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
                root.setColor(1);
            }
        }
        else if (level > 1)
        {
            try {
                if (!root.InEdge.isEmpty()) {
                    if(root.getKey() != 0)
                        Queues.revert(root.InEdge);
                    int len = root.InEdge.getSize();
                    LinkedListQueue.Node node = root.InEdge.getFront();
                    for(int i = 0; i < len; i++){
                        GraphEdge edge = (GraphEdge) node.data;
                        if(edge.Origin.getParent() == root && !edge.deleted)
                        printGivenLevelSCC(out, edge.Origin, level - 1);
                        node = node.next;
                    }
                    if(root.getKey() != 0)
                        Queues.revert(root.InEdge);
                }
            }
            catch (NullPointerException e) {return;}
        }
    }

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

    private int maxDepthSCC(GraphNode s) {
        int depth = 0;
        if (s.InEdge.isEmpty() || s.getColor() > 0)
            return depth;
        else {
            int len = s.InEdge.getSize();
            s.setColor(1);
            for (int i = 0; i< len; i++){
                GraphEdge edge = s.InEdge.dequeue();
                s.InEdge.enqueue(edge);
                if(!edge.deleted) {
                    if(edge.Origin.getParent() == s)
                        depth = Math.max(depth, this.maxDepthSCC(edge.Origin));
                }
            }
            return depth+1;
        }
    }

    protected void discolorSCC(GraphNode s){
        if (s.getColor() == 0 && s.getKey() != 0)
            return;
        try{
            s.setColor(0);
            int len = s.InEdge.getSize();
            for (int i = 0; i< len; i++){
                GraphEdge edge = s.InEdge.dequeue();
                if(!edge.deleted) {
                    s.InEdge.enqueue(edge);
                    discolorSCC(edge.Origin);
                }
            }
        }
        catch (NullPointerException e) {return;}
    }

}