public class StackList {

    private Node head;
    private int queueSize; // queue size

    //linked list node
    private class Node {
        GraphNode data;
        Node next;
    }

    //default constructor - initially head is null; size=0; stack is empty
    public StackList() {
        head = null;
        queueSize = 0;
    }

    //check if the queue is empty
    public boolean isEmpty()
    {
        return (queueSize == 0);
    }

    public void push(GraphNode data){
        Node oldHead = head;
        Node newHead = new Node();
        newHead.data = data;
        newHead.next = oldHead;
        head = newHead;
        queueSize +=1;
    }

    public GraphNode top(){
        if (head != null) return head.data;
        return null;
    }

    public GraphNode pop(){
        if (head != null){
            this.head = this.head.next;
            return head.data;
        }
        return null;
    }
}
