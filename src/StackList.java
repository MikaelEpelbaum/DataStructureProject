public class StackList<T> extends LinkedListQueue<T>{

    private Node<T> head;
    private int queueSize; // queue size

    protected class Node<T> extends LinkedListQueue.Node{
        Node<T> previous;
    }

    //default constructor - initially head is null; size=0; stack is empty
    public StackList() {
        super();
        head = null;
        queueSize = super.queueSize;
    }

//    //check if the queue is empty
//    public boolean isEmpty()
//    {
//        return (queueSize == 0);
//    }

//    public void push(T data){
//        Node oldHead = head;
//        Node newHead = new Node();
//        newHead.data = data;
//        newHead.next = oldHead;
//        head = newHead;
//        queueSize +=1;
//    }

    public void push(T data){
        Node n = new Node();
        n.data = data;
        n.previous = head;
        this.head = n;
        this.queueSize +=1;
    }

    public T pop(){
        if (head != null){
            Node cur = head;
            this.head = this.head.previous;
            return (T) cur.data;
        }
        return null;
    }

//    public T top(){
//        if (head != null) return head.data;
//        return null;
//    }

//    public T pop(){
//        if (head != null){
//            T top = head.data;
//            this.head = this.head.next;
//            queueSize -= 1;
//            return top;
//        }
//        return null;
//    }
}
