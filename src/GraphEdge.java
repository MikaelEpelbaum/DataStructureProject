public class GraphEdge {

    public GraphNode Origin;
    public GraphNode Destination;

    public boolean hasPrevious;
    public boolean deleted;

    public GraphEdge(GraphNode origin, GraphNode destination){
        this.Origin = origin;
        this.Destination = destination;
        UpdateBelligerent(origin, destination);
        if(origin.isExtremeLeft && hasPrevious )
            destination.setExtreme();
    }

    private void UpdateBelligerent(GraphNode origin, GraphNode destination)throws NullPointerException{
        origin.OutEdge.enqueue(this);
        destination.InEdge.enqueue(this);
    }

    public GraphEdge inverse(){
        GraphNode temp = this.Origin;
        this.Origin = this.Destination;
        this.Destination = temp;
        return this;
    }
}
