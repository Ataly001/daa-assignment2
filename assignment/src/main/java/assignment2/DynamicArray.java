package assignment2;

public class DynamicArray {
    private int[] data;
    private int size;
    private int capacity;

    public long elementAccesses = 0;
    public long elementMovements = 0;
    public long comparisons = 0;

    public DynamicArray() {
        this(10);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }

        capacity = Math.max(1, initialCapacity);
        data = new int[capacity];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == capacity) {
            int newCapacity = capacity * 2;
            int[] newData = new int[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
                elementMovements++;
            }
            data = newData;
            capacity = newCapacity;
        }
    }

    public void add(int x) {
        ensureCapacity();
        data[size] = x;
        size++;
        elementAccesses++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity();

        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
            elementMovements++;
        }
        data[index] = x;
        size++;
        elementAccesses++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int removedValue = data[index];
        elementAccesses++;


        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            elementMovements++;
        }
        size--;
        return removedValue;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elementAccesses++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            comparisons++;
            if (data[i] == x) {
                return true;
            }
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