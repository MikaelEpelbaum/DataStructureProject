class LinkedListQueue<T>
{
    protected Node<T> front, rear;
    protected int queueSize; // queue size

    //linked list node
    protected class Node<T>{
        T data;
        Node<T> next;
    }

    //default constructor - initially front & rear are null; size=0; queue is empty
    public LinkedListQueue()
    {
        front = null;
        rear = null;
        queueSize = 0;
    }

    //check if the queue is empty
    public boolean isEmpty() {return (queueSize == 0);}
    public int getSize(){return queueSize;}
    public void setSize(int size){this.queueSize = size;}
    public Node<T> getFront() throws NullPointerException {
        try {
            return this.front;
        }
        catch (NullPointerException e) {return null;}
    }
    public void setFront(Node<T> front) {this.front = front;}
    public Node<T> getRear() throws NullPointerException {
        try {
            return this.rear;
        }
        catch (NullPointerException e) {return null;}
    }
    public void setRear(Node<T> rear) {this.rear = rear;}

    public void emptyQueue() {
        front = null;
        rear = null;
        queueSize = 0;
    }

    //Remove item from the front of the queue.
    public T dequeue()
    {
        try{
            T data = front.data;
            front = front.next;
            queueSize--;
            if (isEmpty()) {
                rear = null;
            }
            return data;
        } catch (NullPointerException e) {return null;}
    }

    //Add data at the rear of the queue.
    public Node<T> enqueue(T data)
    {
        Node oldRear = rear;
        rear = new Node();
        rear.data = data;
        rear.next = null;
        if (isEmpty())
        {
            front = rear;
        }
        else  {
            oldRear.next = rear;
        }
        queueSize++;
        return oldRear;
    }

}

class Queues{
    public static void transfer(LinkedListQueue from, LinkedListQueue to){
        while (!from.isEmpty()){
            to.setFront(from.getFront());
            to.setRear(from.getRear());
            to.setSize(from.getSize());
            from.emptyQueue();
        }
    }

    public static void revert(LinkedListQueue queue) {
        StackList st = new StackList();
        int len = queue.getSize();
        for (int i= 0; i < len; i++)
            st.push(queue.dequeue());
        for (int i= 0; i < len; i++)
            queue.enqueue(st.pop());
//        return queue;
    }
}