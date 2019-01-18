public class BinaryHeap {
    /**
     * implements a binary heap of where the heap rule is the value in the parent node is less than
     * or equal to the values in the child nodes
     */
    private Item items[];
    private int locations[];
    private int size;

    private static class Item implements Comparable {

        private int node;
        /**
         * the priority
         */
        private int distance;

        private Item(int n, int d) {
            node = n;
            distance = d;
        }

        @Override
        public int compareTo(Object z) {
            return distance - ((Item) z).distance;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "node=" + node +
                    ", distance=" + distance +
                    '}';
        }
    }


    public BinaryHeap(int s) {
        items = new Item[s + 1];
        //initially no nodes have been inserted
        locations = new int[s];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = -1;
        }
        size = 0;
    }

    public void removeMin() {
        //PRE: getSize() != 0
        // removes the highest priority item in the queue
        if (size > 0) {
            if (size == 1) {
                size = 0;
            } else {
                items[0] = items[size - 1];
                items[size - 1] = null;
                size--;
                heapSort();
            }
        }
    }

    public int getMinNode() {
        //PRE: getSize() != 0
        // returns the highest priority node
        if (size > 0) {
            return items[0].node;
        }
        return -1;
    }

    public int getMinDistance() {
        //PRE: getSize() != 0
        // returns the distance of highest priority node
        if (size > 0) {
            return items[0].distance;
        }
        return -1;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    private void percDown(int left, int right) {
        int childIndex = -1;
        Item temp = items[left];
        for (; left * 2 + 1 < right; left = childIndex) {
            childIndex = left * 2 + 1;
            if (childIndex + 1 < right && items[childIndex + 1].compareTo(items[childIndex]) > 0) {
                childIndex++;
            }
            if (items[childIndex].compareTo(temp) > 0) {
                items[left] = items[childIndex];
            } else {
                break;
            }
        }
        items[left] = temp;
    }

    public void addItem(int code, int distance) {
        items[size++] = new Item(code, distance);
        heapSort();
    }

    public void heapSort() {
        for (int i = size - 1; i >= 0; i--) {
            percDown(i, size);
        }
        for (int i = size - 1; i > 0; i--) {
            Item temp = items[0];
            items[0] = items[i];
            items[i] = temp;
            percDown(0, i);
        }
    }
}