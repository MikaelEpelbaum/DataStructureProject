class GraphNode {

    private int Key;
    public LinkedListQueue<GraphEdge> InEdge;
    public LinkedListQueue<GraphEdge> OutEdge;
    public boolean isExtremeLeft;
    public boolean deleted;
    private double distance;
    private int retraction;
    int in;
    int out;
    private GraphNode parent;
    private int color;
    public LinkedListQueue<GraphNode> sons;
   /*
   white - 0 - undiscovered
   grey - 1 - discovered but it may have undiscovered neighbors
   black - 2 - v and all its neighbors have been discovered
   */

    public GraphNode(int nodeKey){
        this.Key = nodeKey;
        this.InEdge = new LinkedListQueue<>();
        this.OutEdge = new LinkedListQueue<>();
        this.deleted = false;
        this.in = 0;
        this.out = 0;
    }

    public void setExtreme() {
        this.isExtremeLeft = true;
    }

    public int getKey(){return this.Key;}

    public int getColor() {return this.color;}
    public void setColor(int c) {this.color = c;}
    public double getDistance() {return this.distance;}
    public void setDistance(double d) {this.distance = d;}
    public GraphNode getParent() {return this.parent;}
    public void setParent(GraphNode p) {this.parent = p;}
    public void setRetraction(int t) {this.retraction = t;}

    public int getOutDegree(){
        return this.out;
    }
    public int getInDegree(){
        return this.in;
    }
}
