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

    public RootedTree bfs(GraphNode source){
        LinkedListQueue llq = new LinkedListQueue();
        BFSInitialise(source, llq);
        while (!llq.isEmpty()){
            GraphNode u = llq.dequeue();
            GraphEdge kids = u.OutEdge;
            while (kids != null){
                if (kids.Destination.color == 0){
                    kids.Destination.setColor(1);
                    kids.Destination.setDistance(u.distance + 1);
                    kids.Destination.parent.setParent(u);
                    llq.enqueue(kids.Destination);
                    kids = kids.NextEdge;
                }
            }
            u.setColor(2);
        }
        return new RootedTree();
    }

    public RootedTree dfs(GraphNode source){
        LinkedListQueue q = new LinkedListQueue();

    }

    private void BFSInitialise(GraphNode S, LinkedListQueue q){
        GraphNode head = queue.dequeue();
        while (head!=null){
            head.color = 0;
            head.distance = Double.POSITIVE_INFINITY;
            head.parent = null;
            queue.enqueue(head);
            head = queue.dequeue();
        }
        S.color = 1;
        S.distance = 0;
        S.parent = null;
        q.emptyQueue();
        q.enqueue(S);
    }
}
