/*
------------------------
Author: Matvii Repetskyi
------------------------
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class URLinkedList<E> implements URList<E> {
    private URNode<E> head; // Sentinel head
    private URNode<E> tail; // Sentinel tail
    private int size;

    // Constructor initializes sentinel nodes
    public URLinkedList() {
        head = new URNode<>(null, null, null); // Sentinel head (no data)
        tail = new URNode<>(head, null);        // Sentinel tail (no data)
        head.setNext(tail);
        size = 0;
    }

    // Adds an element to the end
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    // Adds an element at a specific index
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        // go to node at the specified index
        URNode<E> current = head;
        for (int i = 0; i <= index; i++) { // Move index+1 times to reach the node at 'index'
            current = current.next();
        }

        // Insert new node before 'current'
        URNode<E> newNode = new URNode<>(element, current.prev(), current);
        current.prev().setNext(newNode);
        current.setPrev(newNode);
        size++;
    }

    // Add all elements from a collection to the end
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            addLast(e);
            modified = true;
        }
        return modified;
    }

    // Add all elements from a collection starting at a specific index
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        boolean modified = false;
        URNode<E> current = head;
        for (int i = 0; i <= index; i++) { // Move index+1 times to reach the node at 'index'
            current = current.next();
        }

        for (E e : c) {
            URNode<E> newNode = new URNode<>(e, current.prev(), current);
            current.prev().setNext(newNode);
            current.setPrev(newNode);
            size++;
            modified = true;
        }
        return modified;
    }

    // Clears the list
    @Override
    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    // Checks if the list contains an element
    @Override
    public boolean contains(Object o) {

        return indexOf(o) != -1;
    }

    // Checks if the list contains all elements of a collection
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    // Compares the list with another object for equality
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof URList)) return false;
        URList<?> other = (URList<?>) o;
        if (size != other.size()) return false;
        Iterator<E> it1 = iterator();
        Iterator<?> it2 = other.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            E e1 = it1.next();
            Object e2 = it2.next();
            if (!e1.equals(e2))
                return false;
        }
        return !it1.hasNext() && !it2.hasNext();
    }

    // Retrieves the element at a specific index
    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        URNode<E> current = head.next();
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        return current.element();
    }

    // Finds the index of the first occurrence of an element
    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (URNode<E> current = head.next(); current != tail; current = current.next()) {
            if ((o == null && current.element() == null) || (o != null && o.equals(current.element())))
                return index;
            index++;
        }
        return -1;
    }

    // Checks if the list is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns an iterator over the elements
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private URNode<E> current = head.next();

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public E next() {
                if (current == tail) throw new NoSuchElementException();
                E data = current.element();
                current = current.next();
                return data;
            }
        };
    }

    // Removes an element at a specific index
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        URNode<E> current = head.next();
        for (int i = 0; i < index; i++) {
            current = current.next();
        }

        // Remove current node
        current.prev().setNext(current.next());
        current.next().setPrev(current.prev());
        size--;
        return current.element();
    }

    // Removes the first occurrence of an element
    @Override
    public boolean remove(Object o) {
        for (URNode<E> current = head.next(); current != tail; current = current.next()) {
            if ((o == null && current.element() == null) || (o != null && o.equals(current.element()))) {
                current.prev().setNext(current.next());
                current.next().setPrev(current.prev());
                size--;
                return true;
            }
        }
        return false;
    }

    // Removes all elements contained in a collection
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c)
            while (remove(e)) modified = true;
        return modified;
    }

    // Replaces the element at a specific index
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        URNode<E> current = head.next();
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        E old = current.element();
        current.setElement(element);
        return old;
    }

    // Returns the size of the list
    @Override
    public int size() {
        return size;
    }

    // Returns a sublist between two indices
    @Override
    public URList<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        URLinkedList<E> sublist = new URLinkedList<>();
        URNode<E> current = head.next();
        for (int i = 0; i < fromIndex; i++) {
            current = current.next();
        }
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.addLast(current.element());
            current = current.next();
        }
        return sublist;
    }

    // Returns an array containing all elements
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (URNode<E> current = head.next(); current != tail; current = current.next()) {
            array[i++] = current.element();
        }
        return array;
    }

    // Additional methods specific to URLinkedList
    // Adds an element at the beginning
    public void addFirst(E e) {
        URNode<E> newNode = new URNode<>(e, head, head.next());
        head.next().setPrev(newNode);
        head.setNext(newNode);
        size++;
    }

    // Adds an element at the end
    public void addLast(E e) {
        URNode<E> newNode = new URNode<>(e, tail.prev(), tail);
        tail.prev().setNext(newNode);
        tail.setPrev(newNode);
        size++;
    }

    // Retrieves the first element without removing it
    public E peekFirst() {

        return isEmpty() ? null : head.next().element();
    }

    // Retrieves the last element without removing it
    public E peekLast() {

        return isEmpty() ? null : tail.prev().element();
    }

    // Retrieves and removes the first element
    public E pollFirst() {
        if (isEmpty()) return null;
        URNode<E> first = head.next();
        E element = first.element();
        head.setNext(first.next());
        first.next().setPrev(head);
        size--;
        return element;
    }

    // Retrieves and removes the last element
    public E pollLast() {
        if (isEmpty()) return null;
        URNode<E> last = tail.prev();
        E element = last.element();
        tail.setPrev(last.prev());
        last.prev().setNext(tail);
        size--;
        return element;
    }
}
