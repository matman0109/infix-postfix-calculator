/*
------------------------
Author: Matvii Repetskyi
------------------------
 */
import java.util.NoSuchElementException;

//Custom implementation of Queue based on my doubly-linked LinkedList implementation
public class Queue<E> {
    public URLinkedList<E> list;

    //Constructor
    public Queue() {
        list = new URLinkedList<>();
    }

    //Enqueue
    public void enqueue(E element) {
        list.addLast(element);
    }

    //Dequeue
    public E dequeue() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.pollFirst();
    }

    //Peek
    public E peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.peekFirst();
    }

    //isEmpty
    public boolean isEmpty() {
        return list.isEmpty();
    }

    //size
    public int size() {
        return list.size();
    }

    //clear
    public void clear() {
        list.clear();
    }

    //contains
    public boolean contains(E element) {
        return list.contains(element);
    }
}
