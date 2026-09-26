package assignment2;

public class LinkedList {
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private int size;
    public long elementAccesses = 0;
    public long elementMovements = 0;
    public long comparisons = 0;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(int x) {
        add(size, x);
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
            elementMovements++;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                elementAccesses++;
            }
            newNode.next = current.next;
            current.next = newNode;
            elementMovements++;
        }
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        int removedValue;
        if (index == 0) {
            removedValue = head.value;
            head = head.next;
            elementMovements++;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                elementAccesses++;
            }
            removedValue = current.next.value;
            current.next = current.next.next;
            elementMovements++;
        }
        size--;
        return removedValue;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
            elementAccesses++;
        }
        elementAccesses++;
        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            comparisons++;
            if (current.value == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void resetMetrics() {
        elementAccesses = 0;
        elementMovements = 0;
        comparisons = 0;
    }
}