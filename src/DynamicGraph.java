public class DynamicGraph {

    public DynamicGraph() {}

    public GraphNode insertNode(int nodeKey) {
        return new GraphNode(nodeKey);
    }

    public void deleteNode(GraphNode node){
        if (node.getInDegree() == 0 && node.getOutDegree() == 0){
            //todo
            int x = 0;
        }
    }

//    public GraphEdge insertEdge(GraphNode from, GraphNode to){
//        to.setSiblingEdge(new GraphEdge(to, from.getSibling()));
//        GraphEdge edge = new GraphEdge(from, to);
//        from.setSiblingEdge(edge);
//        return edge;
//    }

    public void deleteEdge(GraphEdge edge){
        //todo
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
