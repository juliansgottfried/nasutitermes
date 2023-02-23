/*
Description: custom resizing array implementation.

Author: Julian Gottfried
*/

public class ResizingArray<Item> {
    Item[] items;
    private int size;

    public ResizingArray() {
        int INITIAL_CAPACITY = 8;
        items = (Item[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return (size);
    }

    // appends a spine. Sets spine index to index within resizing array.
    public void append(Item spine) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = spine;
        ((Spine) spine).index = size++;
    }

    // appends a number.
    public void appendNum(Item number) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size++] = number;
    }

    // removes a spine
    public void remove(int index) {
        // decrement size
        size--;
        // place item at back of queue into newly de-queued slot
        items[index] = items[size];
        // update index of spine that has been re-positioned
        ((Spine) items[index]).index = index;
        // set the item at back, now also in de-queued slot, to null
        items[size] = null;
        // resize by 1/2 if number of elements is 25% the queue capacity
        if (size > 0 && size < items.length / 4) {
            resize(items.length / 2);
        }
    }

    // return a random item (but do not remove it)
    public Spine sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("no items to sample");
        }
        int rand = StdRandom.uniformInt(size);
        return (Spine) items[rand];
    }

    // resizes array
    private void resize(int capacity) {
        Item[] resized = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(items, 0, resized, 0, size);
        items = resized;
    }

    // for array of numbers; returns csv string of all numbers in array
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            str.append(items[i]).append(",");
        }
        return str.toString();
    }

    // mean of numbers in array
    public double mean() {
        double val = 0;
        for (int i = 0; i < size; i++) {
            val += (double) items[i];
        }
        return val / size;
    }

    // sd of numbers in array
    public String stats() {
        double mean = this.mean();
        double val = 0;
        for (int i = 0; i < size; i++) {
            val += (((double) items[i]) - mean) * (((double) items[i]) - mean);
        }
        return mean + "," + Math.sqrt(val / size);
    }

}
