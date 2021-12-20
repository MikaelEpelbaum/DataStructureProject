import java.io.DataOutputStream;

class RootedTree {

    public GraphNode Source;

    public RootedTree(){}

    public void setSource(GraphNode source) {
        this.Source = source;
    }

    public void printByLayer(DataOutputStream out) {
        out.write(Source.getKey());
    }

    public void preorderPrint(DataOutputStream out) {}

}