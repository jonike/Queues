import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;              // RandomizedQueue array
    private int N = 0;             // size of the RandomizedQueue

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }

    // Is this queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // Returns the number of items in this queue.
    public int size() {
        return N;     
    }

    // Adds the item to this queue.
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == q.length) resize(2*q.length);
        q[N++] = item;
    }

    // Removes and returns the item on this queue that was least recently added.
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue underflow");
        exchange(q, StdRandom.uniform(N), --N);
        Item item = q[N];        
        q[N] = null;
        if (N > 0 && N <= q.length/4) resize(q.length/2); 
        return item;
    }
    
    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }
    
    private void exchange(Item[] a, int i, int j) {
        if (i == j) return;
        Item swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) 
            throw new NoSuchElementException("RandomizedQueue underflow");        
        return q[StdRandom.uniform(N)];
    }


    // Returns an iterator that iterates over the items in this queue in FIFO order.
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private int counter = N;
        private int[] idx;
        
        public ListIterator() {
            idx = new int[N];
            for (int i = 0; i < N; i++) 
                idx[i] = i;
            StdRandom.shuffle(idx);
        }
        
        public boolean hasNext()  { return counter > 0;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[idx[--counter]];
        }
    }

    // Unit tests
    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                r.enqueue(item);
                //StdOut.print(r);          
            }
            else if (item.equals("-") && !r.isEmpty()) {
                r.dequeue();
                //StdOut.print(r);
            }
        }
        StdOut.println("(" + r.size() + " left on RandomizedQueue)");
    }
}