public class GraphEdge {

    public GraphNode Origin;
    public GraphNode Destination;
    public GraphEdge NextEdge;

    public GraphEdge(GraphNode origin, GraphNode destination){
        this.Origin = origin;
        this.Destination = destination;
        UpdateBelligerent(origin, destination);
    }
    public GraphEdge(GraphNode origin, GraphNode destination, GraphEdge previous){
        this.Origin = origin;
        this.Destination = destination;
        previous.NextEdge = this;
    }

    private void UpdateBelligerent(GraphNode origin, GraphNode destination){
        origin.setOutEdge(this);
        destination.setInEdge(this);
    }
}
