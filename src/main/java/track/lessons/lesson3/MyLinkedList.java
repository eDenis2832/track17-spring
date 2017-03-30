package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack, Queue {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    private Node head;
    private Node tail;

    public MyLinkedList() {
        super();
        head = null;
        tail = null;

    }

    @Override
    void add(int item) {

        Node newnode = new Node(tail, null, item);
        if (tail != null) {
            tail.next = newnode;
        }

        tail = newnode;
        if (head == null) {
            head = newnode;
        }
        size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        int it;
        Node node;

        if ((idx < 0) || (idx >= size)) {
            throw new NoSuchElementException();
        }

        node = head;
        for (it = 1; it <= idx; it++) {
            node = node.next;
        }

        int res;
        res = node.val;

        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;

        return res;

    }

    @Override
    int get(int idx) throws NoSuchElementException {
        int it;
        Node node;

        if ((idx < 0) || (idx >= size)) {
            throw new NoSuchElementException();
        }

        node = head;
        for (it = 1; it <= idx; it++) {
            node = node.next;
        }

        return node.val;
    }

    public void push(int value) {
        add(value);

    }

    public int pop() {
        return remove(size - 1);
    }

    public void enqueue(int value) {
        add(value);
    }

    public int dequeu() {
        return remove(0);
    }

    /*
    @Override
    int size() {
        return 0;
    }
    */
}
