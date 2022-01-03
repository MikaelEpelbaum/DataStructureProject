public class ArrayList<T> extends LinkedListQueue<T>{
    ArrayList() {}

    public T getAt(int i){
        if(i <= this.getSize()) {
            LinkedListQueue temp = new LinkedListQueue();
            int cnt = 0;
            T val = null;
            while (!super.isEmpty()) {
                if (cnt == i) {
                    val = super.dequeue();
                    temp.enqueue(val);
                } else temp.enqueue(super.dequeue());
                cnt+=1;
            }
            Queues.tranfer(temp, this);
            return val;
        }
        else return null;
    }

//    public void setAt(int i, T val) throws IndexOutOfBoundsException{
//        if(i <= this.len) {
//            LinkedListQueue temp = new LinkedListQueue();
//            int cnt = 1;
//            while (!super.isEmpty()) {
//                if (cnt == i) {
//                    temp.enqueue(val);
//                } else temp.enqueue(super.dequeue());
//            }
//            Queues.tranfer(temp, this);
//        }
//        else throw new IndexOutOfBoundsException();
//    }
}
