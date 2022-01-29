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

    public void printByLayer(DataOutputStream out)throws IOException {
        LinkedListQueue q = new LinkedListQueue();
        q.enqueue(Source);
        out.writeBytes(String.valueOf(Source.getKey()));
        if ((Source.InEdge.getSize() != 0 && Source.getKey() == 0)||(Source.sons.getSize() != 0 && Source.getKey() != 0))
            out.writeBytes("\n");
//        out.writeUTF(String.valueOf(Source.getKey()));
//        out.writeUTF("\n");
        if (Source.sons != null || Source.getKey() == 0) {
            int curShifts;
            if (Source.getKey() == 0)
                curShifts = Source.InEdge.getSize();
            else
                curShifts = Source.sons.getSize();
            int nextShift = 0;
            boolean first = true;
            while (!q.isEmpty()) {
                GraphNode cur = (GraphNode) q.dequeue();
                int len = curShifts;
                for (int i = 0; i < len; i++) {
                    GraphNode temp;
                    if (Source.getKey() == 0 && first) {
                        GraphEdge local = cur.InEdge.dequeue();
                        temp = local.Origin;
                        if (temp.deleted)
                            continue;
                        else cur.InEdge.enqueue(local);
                    } else
                        temp = cur.sons.dequeue();
                    if (temp == null)
                        continue;
                    q.enqueue(temp);
                    nextShift += temp.sons.getSize();
                    if (!last) {
                        out.writeBytes(",");
//                        out.writeUTF(",");
                    } else last = false;
                    curShifts--;
                    if (q.getSize() == 1 && temp.getKey() == ((GraphNode) q.front.data).getKey() && !first && temp.sons.isEmpty())
                        q.dequeue();
                    out.writeBytes(String.valueOf(temp.getKey()));
//                    out.writeUTF(String.valueOf(temp.getKey()));
                }
                first = false;
                if (curShifts == 0 && !q.isEmpty()) {
                    if (nextShift != 0) {
                        out.writeBytes("\n");
//                    out.writeUTF("\n");
                        last = true;
                    }
                    curShifts = nextShift;
                    nextShift = 0;
                }
            }
        }
        else {
            int curShifts = Source.OutEdge.getSize();
            int nextShift = 0;
            while (!q.isEmpty()) {
                GraphNode cur = (GraphNode) q.dequeue();
                int len = cur.OutEdge.getSize();
                for(int i = 0; i < len; i++){
                    GraphNode temp = cur.OutEdge.dequeue().Destination;
                    q.enqueue(temp);
                    nextShift += temp.OutEdge.getSize();
                    if (!last) {
                    out.writeBytes(",");
//                        out.writeUTF(",");
                    }
                    else {
                        last = false;
                    }
                out.writeBytes(String.valueOf(temp.getKey()));
//                    out.writeUTF(String.valueOf(temp.getKey()));
                    curShifts--;
                }
                if(curShifts == 0) {
                    out.writeBytes("\n");
//                    out.writeUTF("\n");
                    last = true;
                    curShifts = nextShift;
                    nextShift = 0;
                }
            }
        }
    }

    public void preorderPrint(DataOutputStream out)throws IOException {
        if (Source.getKey() != 0){
            discolor(Source);
            boolean example = true;
            if(!Source.OutEdge.isEmpty())
                if(Source.OutEdge.front.data.Destination.getDistance() > 0)
                    example = false;
            preorderPrintRecursive(out, Source, example);
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

    public void preorderPrintRecursive(DataOutputStream out, GraphNode g, boolean example)throws IOException {
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
                if(!example)
                    Queues.revert(g.OutEdge);
                for(int i = 0; i< len; i++){
                    GraphEdge edge = g.OutEdge.dequeue();
                    g.OutEdge.enqueue(edge);
                    if(g == edge.Destination.getParent()) {
                        preorderPrintRecursive(out, edge.Destination, example);
                    }
                }
                if(!example)
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