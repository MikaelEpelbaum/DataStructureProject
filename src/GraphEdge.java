public class GraphEdge {

    public GraphNode Origin;
    public GraphNode Destination;
    public GraphEdge NextEdge;
    public GraphEdge Previous;

    public GraphEdge(GraphNode origin, GraphNode destination){
        this.Origin = origin;
        this.Destination = destination;
        UpdateBelligerent(origin, destination);
        if(origin.isExtremeLeft && Previous == null) destination.setExtreme();
    }

    private void UpdateBelligerent(GraphNode origin, GraphNode destination)throws NullPointerException{
        this.Previous = origin.OutEdge.getRear();
        try{
            this.Previous.NextEdge = this;
        }
        catch (NullPointerException e) {}
        origin.OutEdge.enqueue(this);
        destination.InEdge.enqueue(this);

    }
}
