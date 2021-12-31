public class GraphEdge {

    public GraphNode Origin;
    public GraphNode Destination;
    public GraphEdge NextEdge;
    public GraphEdge Previuos;
    public boolean isFirst;

    public GraphEdge(GraphNode origin, GraphNode destination){
        this.Origin = origin;
        this.Destination = destination;
        UpdateBelligerent(origin, destination);
        if(origin.isExtremeLeft)
            destination.setExtreme();
    }
    public GraphEdge(GraphNode origin, GraphNode destination, GraphEdge previous){
        this.Origin = origin;
        this.Destination = destination;
        this.isFirst = false;
        previous.NextEdge = this;
        this.Previuos = previous;
    }

    private void UpdateBelligerent(GraphNode origin, GraphNode destination){
        origin.setOutEdge(this);
        destination.setInEdge(this);
        this.Previuos = null;
    }
}
