public class GraphEdge {

    public GraphNode Origin;
    public GraphNode Destination;
    public LinkedListQueue<GraphNode> childs;
    public LinkedListQueue<GraphEdge> parents;
//    public GraphEdge NextEdge;
//    public GraphEdge Previous;
    public boolean hasPrevious;
    public boolean deleted;

    public GraphEdge(GraphNode origin, GraphNode destination){
        this.Origin = origin;
        this.Destination = destination;
        UpdateBelligerent(origin, destination);
        if(origin.isExtremeLeft && hasPrevious ) //Previous == null)
            destination.setExtreme();
    }

    private void UpdateBelligerent(GraphNode origin, GraphNode destination)throws NullPointerException{
        try{
//            this.Previous = origin.OutEdge.getRear().data;
//            this.Previous.NextEdge = this;
        }
        catch (NullPointerException e) {}
        origin.OutEdge.enqueue(this);
        destination.InEdge.enqueue(this);

    }

    public GraphEdge inverse(){
        GraphNode temp = this.Origin;
        this.Origin = this.Destination;
        this.Destination = temp;
//        GraphEdge tempEgde;
//        if (NextEdge != null)
//        {
//            if(Previous != null){
//                tempEgde = Previous;
//                Previous = NextEdge;
//                NextEdge = tempEgde;
//            }
//            else {
//                Previous = NextEdge;
//                NextEdge = null;
//            }
//        }
//        if(NextEdge == null){
//            if (Previous != null){
//                NextEdge = Previous;
//                Previous = null;
//            }
//        }
        return this;
    }
}
