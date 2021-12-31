public class DynamicGraph {

    GraphNode masterSource;
    GraphNode lastSo;

    public DynamicGraph() {}

    public GraphNode insertNode(int nodeKey) {
        GraphNode n = new GraphNode(nodeKey);
        if (masterSource == null)
            masterSource = n;
        return n;
    }

    public void deleteNode(GraphNode node){
        if (node.OutEdge == null && node.InEdge.Origin == masterSource){
            node.InEdge.Previuos.NextEdge = node.InEdge.NextEdge;
            node.InEdge.NextEdge = node.InEdge.Previuos.NextEdge;
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
        //todo recopier
        return new RootedTree();
    }
}
