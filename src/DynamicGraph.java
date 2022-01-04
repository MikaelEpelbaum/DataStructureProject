public class DynamicGraph {

    GraphNode masterSource;
    private LinkedListQueue<GraphNode> NodesQueue;
    private LinkedListQueue<GraphEdge> EdgesQueue;


    public DynamicGraph() {
    }

    public GraphNode insertNode(int nodeKey) {
        GraphNode n = new GraphNode(nodeKey);
        if (masterSource == null) {
            masterSource = n;
            NodesQueue = new LinkedListQueue<GraphNode>();
            EdgesQueue = new LinkedListQueue<GraphEdge>();
        }
        n.isDynamic = true;
        NodesQueue.enqueue(n);
        return n;
    }

    public void deleteNode(GraphNode node) {
//        if (node.OutEdge.isEmpty() && node.InEdge.isEmpty()) {
        if (node.OutEdge.isEmpty() &&  node.getInDegree() == 0) {
            node.isDynamic = false;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to) {
        GraphEdge ge = new GraphEdge(from, to);
        EdgesQueue.enqueue(ge);
        ge.isDynamic = true;
        return ge;
    }

    public void deleteEdge(GraphEdge edge) {
        boolean prev = edge.Previous != null;
        boolean next = edge.NextEdge != null;
        edge.isDynamic = false;
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
        GraphNode s = new GraphNode(0);
        rt.setSource(s);
        StackList<GraphNode> st = new StackList<>();
        dfs(masterSource, st);
        Transpose();
        int len = st.queueSize;
        for(int i = 0; i<len; i++){
            NodesQueue.enqueue(st.pop());
        }
        StackList<GraphNode> st2 = new StackList<>();
        dfs(masterSource, st2);
        int max = -1;
        for(int i = 0; i<len; i++){
            GraphNode gn = st2.pop();
            max = (int) Math.max(max, gn.getDistance());
            s.setDistance(max);
            if(gn.getDistance() == 1)
                new GraphEdge(s, gn);
        }
        return rt;
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

    private void Transpose(){
        int EdgesLen = EdgesQueue.getSize();
        for (int i = 0; i < EdgesLen; i++){
            EdgesQueue.enqueue(EdgesQueue.dequeue().inverse());
        }
        int NodesLen = NodesQueue.getSize();
        for (int i = 0; i < NodesLen; i++){
            GraphNode temp = NodesQueue.dequeue();
            LinkedListQueue<GraphEdge> tempIn = new LinkedListQueue<>();
            Queues.transfer(temp.InEdge, tempIn);
            Queues.transfer(temp.OutEdge, temp.InEdge);
            Queues.transfer(tempIn, temp.OutEdge);
        }
    }

    private void dfs(GraphNode source, StackList<GraphNode> st){
        LinkedListQueue<GraphNode> temp = new LinkedListQueue();
        while (!NodesQueue.isEmpty()){
            GraphNode head = (GraphNode) NodesQueue.dequeue();
            head.setColor(0);
            head.setParent(null);
            temp.enqueue(head);
        }
        Queues.transfer(temp, NodesQueue);
        int time = 0;
        while (!NodesQueue.isEmpty()){
            GraphNode head = (GraphNode) NodesQueue.dequeue();
            if (head.getColor() == 0)
                DFSVisit(head, time, st);
            temp.enqueue(head);
        }
        Queues.transfer(temp, NodesQueue);
    }

    private void DFSVisit(GraphNode u, int time, StackList<GraphNode> st){
        time +=1;
        u.setDistance(time);
        u.setColor(1);
        LinkedListQueue<GraphEdge> kids = u.OutEdge;
        LinkedListQueue<GraphEdge> temp = new LinkedListQueue<>();
        GraphEdge kid = kids.dequeue();
        while (kid != null){
            temp.enqueue(kid);
            if(kid.Destination.getColor() == 0){
                kid.Destination.setParent(u);
                DFSVisit(kid.Destination, time, st);
            }
            kid = kids.dequeue();
        }
        Queues.transfer(temp, u.OutEdge);
        u.setColor(2);
        time +=1;
        u.setRetraction(time);
        st.push(u);
    }

    private void BFSInitialise(GraphNode S, LinkedListQueue q){
        GraphNode head = (GraphNode) NodesQueue.dequeue();
        LinkedListQueue temp = new LinkedListQueue();
        try{
        while (head!=null && head != S){
            head.setColor(0);
            head.setDistance(Double.POSITIVE_INFINITY);
            head.setParent(null);
            temp.enqueue(head);
            head = (GraphNode) NodesQueue.dequeue();
            }
        } catch (NullPointerException e){}
        Queues.transfer(temp, NodesQueue);
        S.setColor(1);
        S.setDistance(0);
        S.setParent(null);
        q.emptyQueue();
        q.enqueue(S);
    }
}
