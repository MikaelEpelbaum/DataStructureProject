public class DynamicGraph {

    GraphNode masterSource;
    private ArrayList queue;

    public DynamicGraph() {
    }

    public GraphNode insertNode(int nodeKey) {
        GraphNode n = new GraphNode(nodeKey);
        if (masterSource == null) {
            masterSource = n;
            queue = new ArrayList();
        } else queue.enqueue(n);
        return n;
    }

    public void deleteNode(GraphNode node) {
        if (node.OutEdge == null && node.InEdge.Origin == masterSource) {
            node.InEdge.Previuos.NextEdge = node.InEdge.NextEdge;
            node.InEdge.NextEdge = node.InEdge.Previuos.NextEdge;
            node.isDynamic = false;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to) {
        if (from.InEdge != null)
            return new GraphEdge(from, to, from.InEdge);
        return new GraphEdge(from, to);
    }

    public void deleteEdge(GraphEdge edge) {
        if (edge.Previuos != null && edge.NextEdge != null) {
            edge.Previuos.NextEdge = edge.NextEdge;
            edge.NextEdge.Previuos = edge.Previuos;
        }
        if (edge.Previuos != null && edge.NextEdge == null)
            edge.Previuos.NextEdge = null;
        if (edge.Previuos == null && edge.NextEdge != null)
            edge.NextEdge.Previuos = null;
    }

    public RootedTree scc() {
        RootedTree rt = new RootedTree();
        GraphNode S = new GraphNode(0);
        rt.setSource(S);

        StackList st = new StackList();

        for (int i = 0; i < queue.getSize(); i++)
            ((GraphNode) queue.getAt(i)).setColor(0);

        for (int i = 0; i < queue.getSize(); i++)
             if(((GraphNode) queue.getAt(i)).getColor() == 0)
                fillOreder((GraphNode) queue.getAt(i), st);

         DynamicGraph transposed = getTranspose();

        for (int i = 0; i < queue.getSize(); i++)
            ((GraphNode) queue.getAt(i)).setColor(0);

        while (!st.isEmpty()){
            GraphNode v = (GraphNode) st.pop();
            if(v.getColor() == 0)
                new GraphEdge(S, transposed.DFSUtil(v.OutEdge).Origin);
        }
        return rt;
    }

    private GraphEdge DFSUtil(GraphEdge v){
        v.Destination.setColor(1);
        while (v.NextEdge != null){
            v = v.NextEdge;
            if(v.Destination.getColor() == 0)
                DFSUtil(v.Destination.OutEdge);
        }
        return v;
    }

    private void fillOreder(GraphNode v, StackList st){
        v.setColor(1);
        GraphEdge kids = v.OutEdge;
        while (kids.NextEdge != null){
            kids = kids.NextEdge;
            if(kids.Destination.getColor() == 0)
                fillOreder(kids.Destination, st);
        }
        st.push(v);
    }

    private DynamicGraph getTranspose(){
        DynamicGraph g = new DynamicGraph();
        for (int i = 0; i < queue.getSize(); i++){
            GraphNode v = (GraphNode) queue.dequeue();
            GraphEdge kids = v.OutEdge;
            while (kids != null){
                g.queue.enqueue(new GraphEdge(kids.Destination, kids.Origin));
                kids = kids.NextEdge;
            }
        }
        return g;
    }

    private void dfs(GraphNode source){
        LinkedListQueue temp = new LinkedListQueue();
        while (!queue.isEmpty()){
            GraphNode head = (GraphNode) queue.dequeue();
            head.setColor(0);
            head.setParent(null);
            temp.enqueue(head);
        }
        Queues.tranfer(temp, queue);
        int time = 0;
        while (!queue.isEmpty()){
            GraphNode head = (GraphNode) queue.dequeue();
            if (head.getColor() == 0)
                DFSVisit(head, time);
            temp.enqueue(head);
        }
        Queues.tranfer(temp, queue);
    }

    private void DFSVisit(GraphNode u, int time){
        time +=1;
        u.setDistance(time);
        u.setColor(1);
        GraphEdge kids = u.OutEdge;
        while (kids != null){
            if(kids.Destination.getColor() == 0){
                kids.Destination.setParent(u);
                DFSVisit(kids.Destination, time);
                kids = kids.NextEdge;
            }
        }
        u.setColor(2);
        time +=1;
        u.setRetraction(time);
    }

    public RootedTree bfs(GraphNode source){
        LinkedListQueue q = new LinkedListQueue();
        BFSInitialise(source, q);
        while (!q.isEmpty()){
            GraphNode u = (GraphNode) q.dequeue();
            GraphEdge kids = u.OutEdge;
            while (kids != null){
                if (kids.Destination.getColor() == 0){
                    kids.Destination.setColor(1);
                    kids.Destination.setDistance(u.getDistance() + 1);
                    kids.Destination.setParent(u);
                    q.enqueue(kids.Destination);
                    kids = kids.NextEdge;
                }
            }
            u.setColor(2);
        }
        return new RootedTree();
    }

    private void BFSInitialise(GraphNode S, LinkedListQueue q){
        GraphNode head = (GraphNode) queue.dequeue();
        while (head!=null){
            head.setColor(0);
            head.setDistance(Double.POSITIVE_INFINITY);
            head.setParent(null);
            queue.enqueue(head);
            head = (GraphNode) queue.dequeue();
        }
        S.setColor(1);
        S.setDistance(0);
        S.setParent(null);
        q.emptyQueue();
        q.enqueue(S);
    }
}
