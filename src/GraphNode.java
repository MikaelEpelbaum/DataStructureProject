public class GraphNode {

    private int Key;
    public GraphEdge InEdge;
    public GraphEdge OutEdge;
    public boolean isExtremeLeft;


    public GraphNode(int nodeKey){
        this.Key = nodeKey;
    }

    public void setExtreme() {
        this.isExtremeLeft = true;
    }

    public int getKey(){return this.Key;}

    public int getOutDegree(){
        if (OutEdge == null) return 0;
        return getSumEdges(this.OutEdge);
    }
    public int getInDegree(){
        if (InEdge == null) return 0;
        return getSumEdges(this.InEdge);
    }
    private int getSumEdges(GraphEdge edge){
        int count = 1;
        GraphEdge iterator = edge;
        while (iterator != null){
            iterator = iterator.NextEdge;
            count +=1;
        }
        return count;
    }

    public void setInEdge(GraphEdge inEdge) {
        this.InEdge = inEdge;
    }
    public void setOutEdge(GraphEdge outEdge) {
        this.OutEdge = outEdge;
    }
}
