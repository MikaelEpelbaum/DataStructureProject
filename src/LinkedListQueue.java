class LinkedListQueue<T>
{
    private Node<T> front, rear;
    private int queueSize; // queue size

    //linked list node
    private class Node<T>{
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

    public void emptyQueue() {
        front = null;
        rear = null;
        queueSize = 0;
    }

    //Remove item from the front of the queue.
    public T dequeue()
    {
        T data = front.data;
        front = front.next;
        if (isEmpty())
        {
            rear = null;
        }
        queueSize--;
        return data;
    }

    //Add data at the rear of the queue.
    public void enqueue(T data)
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
    }

}

class Queues{
    public static void tranfer(LinkedListQueue from, LinkedListQueue to){
        while (!from.isEmpty()){
            to.enqueue(from.dequeue());
        }
    }
}