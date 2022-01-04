public class GraphEdge {

    public GraphNode Origin;
    public GraphNode Destination;
    public GraphEdge NextEdge;
    public GraphEdge Previous;
    public boolean isDynamic;

    public GraphEdge(GraphNode origin, GraphNode destination){
        this.Origin = origin;
        this.Destination = destination;
        UpdateBelligerent(origin, destination);
        if(origin.isExtremeLeft && Previous == null) destination.setExtreme();
    }

    private void UpdateBelligerent(GraphNode origin, GraphNode destination)throws NullPointerException{
        try{
            this.Previous = origin.OutEdge.getRear().data;
            this.Previous.NextEdge = this;
        }
        catch (NullPointerException e) {}
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
