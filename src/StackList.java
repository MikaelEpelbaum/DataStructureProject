public class StackList<T> extends LinkedListQueue<T>{

    protected Node<T> head;
    protected Node<T> bottom;

    protected class Node<T> extends LinkedListQueue.Node{
        Node<T> previous;
    }

    //default constructor - initially head is null; size=0; stack is empty
    public StackList() {
        super();
        head = null;
        queueSize = super.queueSize;
    }

    public void push(T data){
        Node n = new Node();
        n.data = data;
        n.previous = head;
        if(head == null) {this.bottom = n;}
        this.head = n;
        this.queueSize +=1;
    }

    public T pop(){
        if (head != null){
            Node cur = head;
            this.head = this.head.previous;
            this.queueSize -=1;
            return (T) cur.data;
        }
        return null;
    }
}

class Stacks{
    public static StackList transferStackToQueue(StackList from){
        StackList to = new StackList();
        while (!from.isEmpty()){
            to.push(from.pop());
        }
        return to;
    }
}