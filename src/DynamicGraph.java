public class DynamicGraph {

    GraphNode masterSource;
    private LinkedListQueue queue;

    public DynamicGraph() {}

    public GraphNode insertNode(int nodeKey) {
        GraphNode n = new GraphNode(nodeKey);
        if (masterSource == null){
            masterSource = n;
            queue = new LinkedListQueue();
        }
        else queue.enqueue(n);
        return n;
    }

    public void deleteNode(GraphNode node){
        if (node.OutEdge == null && node.InEdge.Origin == masterSource){
            node.InEdge.Previuos.NextEdge = node.InEdge.NextEdge;
            node.InEdge.NextEdge = node.InEdge.Previuos.NextEdge;
            node.isDynamic = false;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to){
        if (from.InEdge != null)
            return new GraphEdge(from, to, from.InEdge);
        return new GraphEdge(from, to);
    }

    public void deleteEdge(GraphEdge edge) {
        if (edge.Destination != null)
            return;  /*error*/
        if (edge.Previuos != null && edge.NextEdge != null){
            edge.Previuos.NextEdge = edge.NextEdge;
            edge.NextEdge.Previuos = edge.Previuos;
        }
        if (edge.Previuos != null && edge.NextEdge == null)
            edge.Previuos.NextEdge = null;
    }

    public RootedTree ssc(){
        RootedTree rt = new RootedTree();
        rt.setSource(new GraphNode(0));
        //todo
        return rt;
    }

    private void dfs(GraphNode source){
        LinkedListQueue temp = new LinkedListQueue();
        while (!queue.isEmpty()){
            GraphNode head = queue.dequeue();
            head.setColor(0);
            head.setParent(null);
            temp.enqueue(head);
        }
        Queues.tranfer(temp, queue);
        int time = 0;
        while (!queue.isEmpty()){
            GraphNode head = queue.dequeue();
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
            GraphNode u = q.dequeue();
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
        GraphNode head = queue.dequeue();
        while (head!=null){
            head.setColor(0);
            head.setDistance(Double.POSITIVE_INFINITY);
            head.setParent(null);
            queue.enqueue(head);
            head = queue.dequeue();
        }
        S.setColor(1);
        S.setDistance(0);
        S.setParent(null);
        q.emptyQueue();
        q.enqueue(S);
    }
}
