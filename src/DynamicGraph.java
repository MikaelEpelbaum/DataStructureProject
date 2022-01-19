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
        from.out++;
        to.in++;
        return ge;
    }

    public void deleteEdge(GraphEdge edge) {
        edge.deleted = true;
        edge.Origin.out--;
        edge.Destination.in--;
    }


    public RootedTree scc() {
        StackList<GraphNode> st = new StackList<>();
        LinkedListQueue<GraphNode> NodesCopy = new LinkedListQueue<>();
        LinkedListQueue<GraphNode> NodesCopy2 = new LinkedListQueue<>();
        int len = NodesQueue.queueSize;
        for(int i = 0; i<len; i++){
            GraphNode t = NodesQueue.dequeue();
            NodesCopy.enqueue(t);
            NodesQueue.enqueue(t);
        }
        dfs(st, NodesCopy, true);

        int stLen = st.getSize();
        for(int i = 0; i<stLen; i++){
            GraphNode temp = st.pop();
            if(temp.deleted)
                continue;
            NodesCopy.enqueue(temp);
            NodesCopy2.enqueue(temp);
        }
        Queues.revert(NodesCopy);
        dfs(st, NodesCopy, false);
        RootedTree rt = new RootedTree();
        GraphNode s = new GraphNode(0);
        rt.setSource(s);
        for(int i = 0; i<stLen; i++) {
            GraphNode gn = NodesCopy2.dequeue();
                if (gn.getParent() == null){
                gn.setParent(s);
                new GraphEdge(gn, s);
            }
        }
        return rt;
    }

    public RootedTree bfs(GraphNode source) {
        LinkedListQueue q = new LinkedListQueue();
        BFSInitialise(source, q);
        while (!q.isEmpty()) {
            GraphNode u = (GraphNode) q.dequeue();
            if (!u.deleted) {
                LinkedListQueue<GraphEdge> kids = u.OutEdge;
                Queues.revert(kids);
                int len = kids.queueSize;
                for (int i = 0; i < len; i++) {
                    GraphEdge kid = kids.dequeue();
                    kids.enqueue(kid);
                    if (!kid.deleted && kid.Destination.getColor() < 1) {
                        kid.Destination.setColor(1);
                        kid.Destination.setDistance(u.getDistance() + 1);
                        kid.Destination.setParent(u);
                        q.enqueue(kid.Destination);
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

    private int time;
    // fills the stack with GraphNodes in order of retraction. head - last retracted from bottom first retracted from.
    private void dfs(StackList<GraphNode> st, LinkedListQueue<GraphNode> nodes, boolean firstDFS){
        int len = nodes.getSize();
        Queues.revert(nodes);
        for(int i =0; i< len; i++){
            GraphNode head = (GraphNode) nodes.dequeue();
            if(!head.deleted) {
                head.setColor(0);
                head.setDistance(1);
                head.setParent(null);
                nodes.enqueue(head);
            }
        }
        len = nodes.getSize();
        this.time = 0;
        for(int i =0; i< len; i++){
            GraphNode head = (GraphNode) nodes.dequeue();
            if (head.getColor() == 0) {
                DFSVisit(head, st, firstDFS);
            }
        }
    }

    private void DFSVisit(GraphNode u, StackList<GraphNode> st, boolean FirstDFS){
        this.time +=1;
        u.setDistance(time);
        u.setColor(1);
        int len;
        LinkedListQueue.Node node;
        if(FirstDFS){
            len = u.OutEdge.getSize();
            Queues.revert(u.OutEdge);
            node = u.OutEdge.getFront();
        }
        else {
            len = u.InEdge.getSize();
            Queues.revert(u.InEdge);
            node = u.InEdge.getFront();
        }

        for(int i =0; i< len; i++){
            GraphEdge kid = (GraphEdge) node.data;
            if(!kid.deleted) {
                if(FirstDFS) {
                    if (kid.Destination.getColor() < 1) {
                        kid.Destination.setParent(u);
                        DFSVisit(kid.Destination, st, FirstDFS);
                    }
                }
                else {

                    if (kid.Origin.getColor() < 1) {
                        kid.Origin.setParent(u);
                        DFSVisit(kid.Origin, st, FirstDFS);
                    }
                }
            }
            node = node.next;
        }
        u.setColor(2);
        if(FirstDFS)
            Queues.revert(u.OutEdge);
        else
            Queues.revert(u.InEdge);
        this.time +=1;
        u.setRetraction(time);
        st.push(u);
    }

    private void BFSInitialise(GraphNode S, LinkedListQueue q){
        int len = NodesQueue.getSize();
        try{
        for (int i = 0; i< len; i++) {
            GraphNode head = (GraphNode) NodesQueue.dequeue();
            NodesQueue.enqueue(head);
            if (!head.deleted){
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
