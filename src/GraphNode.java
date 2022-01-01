public class GraphNode {

    private int Key;
    public GraphEdge InEdge;
    public GraphEdge OutEdge;
    public boolean isExtremeLeft;

    public boolean isDynamic;
    public double distance;
    public GraphNode parent;
    public int color;
   /*
   white - 0 - undiscovered
   grey - 1 - discovered but it may have undiscovered neighbors
   black - 2 - v and all its neighbors have been discovered
   */


    public GraphNode(int nodeKey){
        this.Key = nodeKey;
        isDynamic = true;
    }

    public void setExtreme() {
        this.isExtremeLeft = true;
    }

    public int getKey(){return this.Key;}

    public int getColor() {return this.color;}
    public void setColor(int c) {this.color = c;}
    public void setDistance(double d) {this.distance = d;}
    public void setParent(GraphNode p) {this.parent = p;}

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
