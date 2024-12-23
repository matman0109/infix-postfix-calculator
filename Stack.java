/*
------------------------
Author: Matvii Repetskyi
------------------------
 */
import java.util.NoSuchElementException;

//Custom implementation of Stack based on my doubly-linked LinkedList implementation

public class Stack<E> {
    public URLinkedList<E> list;

    //Constructor
    public Stack() {
        list = new URLinkedList<>();
    }

    //Push
    public void push(E element) {
        list.addFirst(element);
    }

    //Pop
    public E pop() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.pollFirst();
    }

    //Peek
    public E peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
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
