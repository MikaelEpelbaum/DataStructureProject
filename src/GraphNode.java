public class GraphNode {

    private int Key;
    private GraphEdge LeftSonEdge;
    private GraphEdge SiblingEdge;

    public GraphNode(int nodeKey){
        this.Key = nodeKey;
    }

    public GraphNode getSibling(){ return SiblingEdge.Destination; }

    public void setSiblingEdge(GraphEdge siblingEdge) {
        this.SiblingEdge = siblingEdge;
    }

    public int getKey(){return this.Key;}

    public int getOutDegree(){
        int count = 0;
        GraphEdge sibling = this.LeftSonEdge;
        while (sibling != null){
            sibling = sibling.Destination.SiblingEdge;
            count +=1;
        }
        return count;
    }
    public int getInDegree(){
        if (this.Key == 0) return 0;
        return 1;
    }


}
