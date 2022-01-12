public class DynamicGraph {

    GraphNode masterSource;
    private LinkedListQueue<GraphNode> NodesQueue;
    private LinkedListQueue<GraphEdge> EdgesQueue;
    private int time;


    public DynamicGraph() {
    }

    public GraphNode insertNode(int nodeKey) {
        GraphNode n = new GraphNode(nodeKey);
        if (masterSource == null) {
            masterSource = n;
            NodesQueue = new LinkedListQueue<GraphNode>();
            EdgesQueue = new LinkedListQueue<GraphEdge>();
        }
        n.deleted = false;
        NodesQueue.enqueue(n);
        return n;
    }

    public void deleteNode(GraphNode node) {
        if (node.getOutDegree() == 0 &&  node.getInDegree() == 0) {
            node.deleted = true;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to) {
        GraphEdge ge = new GraphEdge(from, to);
        EdgesQueue.enqueue(ge);
        ge.deleted = false;
        return ge;
    }

    public void deleteEdge(GraphEdge edge) {
        edge.deleted = true;
    }


    public RootedTree scc() {
        RootedTree rt = new RootedTree();
        GraphNode s = new GraphNode(0);
        rt.setSource(s);
        StackList<GraphNode> st = new StackList<>();
        dfs(st);
        Transpose();
        int len = st.queueSize;
        for(int i = 0; i<len; i++){
            GraphNode temp = st.pop();
            Queues.revert(temp.OutEdge);
            NodesQueue.enqueue(temp);
        }
        Queues.revert(NodesQueue);
        dfs(st);

        for(int i = 0; i<len; i++) {
            GraphNode gn = st.pop();
//            Queues.revert(gn.OutEdge);
            if (gn.getParent() == null){
                gn.setParent(s);
                new GraphEdge(s, gn);
            }
        }
        // in order to come back to the original layout
//        Transpose();
//        s.setDistance(depth);
        return rt;
    }

    public RootedTree bfs(GraphNode source){
        LinkedListQueue q = new LinkedListQueue();
        BFSInitialise(source, q);
        while (!q.isEmpty()){
            GraphNode u = (GraphNode) q.dequeue();
            if(!u.deleted) {
                LinkedListQueue<GraphEdge> kids = u.OutEdge;
                Queues.revert(kids);
                int len = kids.queueSize;
                for (int i = 0; i < len; i++) {
                    GraphEdge kid = kids.dequeue();
                    if (!kid.deleted) {
                        if (kid.Destination.getColor() == 0) {
                            kid.Destination.setColor(1);
                            kid.Destination.setDistance(u.getDistance() + 1);
                            kid.Destination.setParent(u);
                        }
                        if (kid.Destination.getColor() !=2)
                            q.enqueue(kid.Destination);
                        u.OutEdge.enqueue(kid);
                    }
                }
                u.setColor(2);
                Queues.revert(kids);
            }
        }
        RootedTree rt = new RootedTree();
        rt.setSource(source);
        rt.discolor(source);
        return rt;
    }

    private void Transpose(){
        int EdgesLen = EdgesQueue.getSize();
        for (int i = 0; i < EdgesLen; i++){
            GraphEdge temp = EdgesQueue.dequeue();
            if(!temp.deleted) {
                temp.inverse();
                EdgesQueue.enqueue(temp);
            }
        }
        int NodesLen = NodesQueue.getSize();
        for (int i = 0; i < NodesLen; i++){
            GraphNode temp = NodesQueue.dequeue();
            LinkedListQueue<GraphEdge> tempIn = new LinkedListQueue<>();
            Queues.transfer(temp.InEdge, tempIn);
            Queues.transfer(temp.OutEdge, temp.InEdge);
            Queues.transfer(tempIn, temp.OutEdge);
//            NodesQueue.enqueue(temp);
        }
    }

    private void dfs(StackList<GraphNode> st){
        int len = NodesQueue.getSize();
        for(int i =0; i< len; i++){
            GraphNode head = (GraphNode) NodesQueue.dequeue();
            head.setColor(0);
            head.setParent(null);
            NodesQueue.enqueue(head);
        }
        Queues.revert(NodesQueue);
        this.time = 0;
        for(int i =0; i< len; i++){
            GraphNode head = (GraphNode) NodesQueue.dequeue();
            NodesQueue.enqueue(head);
            if (head.getColor() == 0)
                DFSVisit(head, st);
        }
    }

    private void DFSVisit(GraphNode u, StackList<GraphNode> st){
        this.time +=1;
        u.setDistance(this.time);
        u.setColor(1);
//        Queues.revert(u.OutEdge);
        int len = u.OutEdge.getSize();
        LinkedListQueue.Node node = u.OutEdge.getFront();
        for(int i =0; i< len; i++){
            GraphEdge kid = (GraphEdge) node.data;
            if(!kid.deleted) {
//                u.OutEdge.enqueue(kid);
                if (kid.Destination.getColor() == 0) {
                    kid.Destination.setParent(u);
                    DFSVisit(kid.Destination, st);
                }
            }
            node = node.next;
        }
        u.setColor(2);
        this.time +=1;
        u.setRetraction(this.time);
        st.push(u);
    }

    private void BFSInitialise(GraphNode S, LinkedListQueue q){
        int len = NodesQueue.getSize();
        try{
        for (int i = 0; i< len; i++) {
            GraphNode head = (GraphNode) NodesQueue.dequeue();
            if (!head.deleted){
                NodesQueue.enqueue(head);
                if (head != null && head != S) {
                    head.setColor(0);
                    head.setDistance(Double.POSITIVE_INFINITY);
                    head.setParent(null);
                }
            }
        }
        } catch (NullPointerException e){}
        S.setColor(1);
        S.setDistance(0);
        S.setParent(null);
        q.emptyQueue();
        q.enqueue(S);
    }
}
