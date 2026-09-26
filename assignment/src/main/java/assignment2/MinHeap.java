package assignment2;

public class MinHeap {
    private int[] heap;
    private int size;
    public long comparisons = 0;
    public long movements = 0;

    public MinHeap() {
        heap = new int[10];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            int newCapacity = size * 2;
            int[] newHeap = new int[newCapacity];
            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
                movements++;
            }
            heap = newHeap;
        }
    }

    public void insert(int value) {
        ensureCapacity();
        heap[size] = value;
        movements++;
        int current = size;
        size++;
        while (current > 0) {
            int parent = (current - 1) / 2;
            comparisons++;
            if (heap[current] >= heap[parent]) {
                break;
            }
            swap(current, parent);
            current = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    public int getMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        movements++;
        size--;
        int current = 0;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;

            if (left < size) {
                comparisons++;
                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }
            if (right < size) {
                comparisons++;
                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }
            if (smallest == current) {
                break;
            }
            swap(current, smallest);
            current = smallest;
        }

        return min;
    }

    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
        movements += 3;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[i] > heap[left]) {
                return false;
            }

            if (right < size && heap[i] > heap[right]) {
                return false;
            }
        }

        return true;
    }

    public void resetMetrics() {
        comparisons = 0;
        movements = 0;
    }
}