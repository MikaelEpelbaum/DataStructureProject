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
        if (node.OutEdge.isEmpty() && node.InEdge.isEmpty()) {
            node.isDynamic = false;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to) {
        return new GraphEdge(from, to);
    }

    public void deleteEdge(GraphEdge edge) {
        boolean prev = edge.Previous != null;
        boolean next = edge.NextEdge != null;
        if (prev && next) {
            edge.Previous.NextEdge = edge.NextEdge;
            edge.NextEdge.Previous = edge.Previous;
        }
        if (prev && !next)
            edge.Previous.NextEdge = null;
        if (!prev && next)
            edge.NextEdge.Previous = null;
    }

    public RootedTree scc() {
        RootedTree rt = new RootedTree();
        GraphNode S = new GraphNode(0);
        rt.setSource(S);

        StackList st = new StackList();

        for (int i = 0; i< queue.getSize(); i++){
            GraphNode temp = ((GraphNode) queue.dequeue());
            temp.setColor(0);
            queue.enqueue(temp);
        }

        LinkedListQueue temp = new LinkedListQueue();
        for (int i = 0; i < queue.getSize(); i++) {
            GraphNode seeked = (GraphNode) queue.getAt(i);
            if (seeked.getColor() == 0)
                fillOreder((GraphNode) seeked, st, temp);
        }
        Queues.tranfer(temp, queue);

         DynamicGraph transposed = getTranspose();

        for (int i = 0; i < queue.getSize(); i++)
            ((GraphNode) queue.getAt(i)).setColor(0);

        while (!st.isEmpty()){
            GraphNode v = st.pop();
            if(v.getColor() == 0)
                new GraphEdge(S, transposed.DFSUtil(v.OutEdge.dequeue()).Origin);
        }
        return rt;
    }

    private GraphEdge DFSUtil(GraphEdge v){
        v.Destination.setColor(1);
        while (v.NextEdge != null){
            v = v.NextEdge;
            if(v.Destination.getColor() == 0)
                DFSUtil(v.Destination.OutEdge.dequeue());
        }
        return v;
    }

    private void fillOreder(GraphNode v, StackList st, LinkedListQueue temp){

        try {
            v.setColor(1);
            LinkedListQueue<GraphEdge> kids = v.OutEdge;
            GraphEdge kid = kids.dequeue();
            while (kid.NextEdge != null) {
                temp.enqueue(kid);
                if (kid.Destination.getColor() == 0)
                    fillOreder(kid.Destination, st, temp);
                kid = kids.dequeue();
            }
            st.push(v);
        } catch (NullPointerException e){return;}
    }

    private DynamicGraph getTranspose(){
        DynamicGraph g = new DynamicGraph();
        for (int i = 0; i < queue.getSize(); i++){
            GraphNode v = (GraphNode) queue.dequeue();
            LinkedListQueue<GraphEdge> kids = v.OutEdge;
            GraphEdge kid = kids.dequeue();
            while (kid != null){
                g.queue.enqueue(new GraphEdge(kid.Destination, kid.Origin));
                kid = kids.dequeue();
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
        LinkedListQueue<GraphEdge> kids = u.OutEdge;
        GraphEdge kid = kids.dequeue();
        while (kid != null){
            if(kid.Destination.getColor() == 0){
                kid.Destination.setParent(u);
                DFSVisit(kid.Destination, time);
                kid = kids.dequeue();
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
            LinkedListQueue<GraphEdge> kids = u.OutEdge;
            GraphEdge kid = kids.dequeue();
            while (kid != null){
                if (kid.Destination.getColor() == 0){
                    kid.Destination.setColor(1);
                    kid.Destination.setDistance(u.getDistance() + 1);
                    kid.Destination.setParent(u);
                    q.enqueue(kid.Destination);
                }
                kid = kids.dequeue();
            }
            u.setColor(2);
        }
        return new RootedTree();
    }

    private void BFSInitialise(GraphNode S, LinkedListQueue q){
        GraphNode head = (GraphNode) queue.dequeue();
        LinkedListQueue temp = new LinkedListQueue();
        try{
        while (head!=null && head != S){
            head.setColor(0);
            head.setDistance(Double.POSITIVE_INFINITY);
            head.setParent(null);
            temp.enqueue(head);
            head = (GraphNode) queue.dequeue();
            }
        } catch (NullPointerException e){}
        Queues.tranfer(temp, queue);
        S.setColor(1);
        S.setDistance(0);
        S.setParent(null);
        q.emptyQueue();
        q.enqueue(S);
    }
}
