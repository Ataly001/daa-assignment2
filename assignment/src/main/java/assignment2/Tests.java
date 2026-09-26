package assignment2;
import java.util.*;

public class Tests {
    public static void main(String[] args) {
        testLists();
        testHeap();
        testLargeInput();
        System.out.println("ALL TESTS PASSED");
    }

    static void check(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    static void compare(DynamicArray array, LinkedList list, ArrayList<Integer> expected) {
        check(array.size() == expected.size(), "Array size");
        check(list.size() == expected.size(), "List size");
        for (int i = 0; i < expected.size(); i++) {
            check(array.get(i) == expected.get(i), "Array value at " + i);
            check(list.get(i) == expected.get(i), "List value at " + i);
        }
    }

    static void testLists() {
        DynamicArray array = new DynamicArray(0);
        LinkedList list = new LinkedList();
        ArrayList<Integer> expected = new ArrayList<>();
        compare(array, list, expected);
        check(!array.contains(10) && !list.contains(10), "Empty search");
        invalidIndices(array, list);

        array.add(7);
        list.add(7);
        expected.add(7);
        compare(array, list, expected);
        check(array.remove(0) == expected.get(0), "One array value");
        check(list.remove(0) == expected.remove(0), "One list value");
        compare(array, list, expected);


        int[] values = {10, 20, 20, -5};
        for (int value : values) {
            array.add(value);
            list.add(value);
            expected.add(value);
        }
        compare(array, list, expected);
        invalidIndices(array, list);
        check(array.contains(20) && list.contains(20), "Find a value");
        check(!array.contains(99) && !list.contains(99), "Missing value");
        int[] positions = {0, 2, expected.size()};
        for (int i = 0; i < positions.length; i++) {
            int index = positions[i];
            array.add(index, 50 + i);
            list.add(index, 50 + i);
            expected.add(index, 50 + i);
            compare(array, list, expected);
        }
        int last = expected.size() - 1;
        int lastValue = expected.remove(last);
        check(array.remove(last) == lastValue, "Array last removal");
        check(list.remove(last) == lastValue, "List last removal");
        compare(array, list, expected);
        while (!expected.isEmpty()) {
            int index = expected.size() / 2;
            int value = expected.remove(index);
            check(array.remove(index) == value, "Array remove");
            check(list.remove(index) == value, "List remove");
            compare(array, list, expected);
        }
        invalidIndices(array, list);
        try {
            new DynamicArray(-1);
            throw new AssertionError("Negative capacity accepted");
        } catch (IllegalArgumentException correct) {

        }
        System.out.println("Array and list: passed");
    }

    static void invalidIndices(DynamicArray array, LinkedList list) {
        int[] bad = {-1, array.size(), array.size() + 1};
        for (int index : bad) {
            try {
                array.get(index);
                throw new AssertionError("Array get accepted " + index);
            } catch (IndexOutOfBoundsException correct) {  }
            try {
                list.get(index);
                throw new AssertionError("List get accepted " + index);
            } catch (IndexOutOfBoundsException correct) {  }
            try {
                array.remove(index);
                throw new AssertionError("Array remove accepted " + index);
            } catch (IndexOutOfBoundsException correct) {  }
            try {
                list.remove(index);
                throw new AssertionError("List remove accepted " + index);
            } catch (IndexOutOfBoundsException correct) {  }

            if (index == array.size()) continue;
            try {
                array.add(index, 1);
                throw new AssertionError("Array add accepted " + index);
            } catch (IndexOutOfBoundsException correct) {  }
            try {
                list.add(index, 1);
                throw new AssertionError("List add accepted " + index);
            } catch (IndexOutOfBoundsException correct) {  }
        }
    }

    static void testHeap() {
        MinHeap heap = new MinHeap();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        check(heap.isEmpty() && heap.isValidHeap(), "Empty heap");
        try {
            heap.peekMin();
            throw new AssertionError("Empty peek accepted");
        } catch (IllegalStateException correct) {  }
        try {
            heap.getMin();
            throw new AssertionError("Empty extract accepted");
        } catch (IllegalStateException correct) {  }

        int[] values = {10, 5, 20, 3, 5, -7, Integer.MIN_VALUE};
        for (int value : values) {
            heap.insert(value);
            expected.add(value);
            check(heap.isValidHeap(), "Heap after insert");
            check(heap.peekMin() == expected.peek(), "Heap minimum");
        }
        int previous = Integer.MIN_VALUE;
        while (!expected.isEmpty()) {
            int value = heap.getMin();
            check(value == expected.remove() && value >= previous, "Heap order");
            check(heap.isValidHeap(), "Heap after extract");
            previous = value;
        }
        check(heap.isEmpty(), "Heap must be empty");
        System.out.println("Heap: passed");
    }
    static void testLargeInput() {
        int n = 100000;
        int[] values = new int[n];
        Random random = new Random(42);
        DynamicArray array = new DynamicArray();
        LinkedList list = new LinkedList();
        MinHeap heap = new MinHeap();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt();
            array.add(values[i]);
            heap.insert(values[i]);
            expected.add(values[i]);
        }

        for (int i = n - 1; i >= 0; i--) list.add(0, values[i]);
        check(array.size() == n && list.size() == n && heap.size() == n, "Large size");
        check(array.get(n - 1) == values[n - 1], "Array last value");
        check(list.get(n - 1) == values[n - 1], "List last value");
        check(heap.isValidHeap(), "Large heap");
        for (int i = 0; i < n; i++) {
            check(array.remove(array.size() - 1) == values[n - 1 - i], "Large array");
            check(list.remove(0) == values[i], "Large list");
            check(heap.getMin() == expected.remove(), "Large heap order");
        }
        check(array.size() == 0 && list.size() == 0 && heap.isEmpty(), "Empty after test");
        System.out.println("Large input: passed");
    }
}