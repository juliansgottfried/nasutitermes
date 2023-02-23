/*
Description: custom data structure to keep track of active spines.

Author: Julian Gottfried
*/

import java.util.Stack;

public class SpineTree {

    // length of time spines are under consumption for
    private int CONSUMPTION;

    // root spine
    private Spine root;

    // resizing array of spines under consumption for spine randomization
    private ResizingArray<Spine> randomArray;

    // priority queue to keep track of oldest spines, using spine time for comparison. A smaller
    // time value means the spine was inserted at an earlier time step, and thus is older. A
    // minimum priority queue is used instead of a maximum because we want to locate the oldest
    // spines, which are the smallest time steps.
    private MinPQ<Spine> priorityQueue;

    // number of spines in SpineTree
    int size;

    public SpineTree(int consumption) {
        root = null;
        randomArray = new ResizingArray<>();
        priorityQueue = new MinPQ<>();
        CONSUMPTION = consumption;
        size = 0;
    }

    // insert single spine
    public void insert(Spine parent, Spine child, double time) {
        // no parent if root
        if (root == null) {
            root = child;
        }
        // link child to parents
        else child.addAsChild(parent);
        // set child's time
        child.time = time;
        randomArray.append(child);
        priorityQueue.insert(child);
        size++;
    }

    // delete expired spines
    public Stack<Spine> delete(int time) {
        // stack of spines deleted
        Stack<Spine> trash = new Stack<>();
        // iterate through spines from oldest to newest
        for (Spine spine : priorityQueue) {
            // if time elapsed since a spine was inserted is greater than the length of time
            // it takes to consume a spine, check whether spine can be removed
            if (time - spine.time > CONSUMPTION) {
                // if spine has no children and if spine has not already been removed, remove
                // from data structures
                if (spine.numChildren == 0 && spine.removed == 0) {
                    randomArray.remove(spine.index);
                    spine.removeAsChild();
                    spine.removed = 1;
                    size--;
                    trash.push(spine);
                }
            }
            // if elapsed time less than consumption time, stop iterating
            else break;
        }
        // delete oldest spines from top of priority queue if they have now been removed
        while (priorityQueue.min().removed == 1) priorityQueue.delMin();
        return trash;
    }

    // return a random spine
    public Spine sample() {
        return randomArray.sample();
    }

    // draws spine, and draws line from parent spine to child spine
    public void draw(Spine child) {
        double x1 = child.coords[0];
        double y1 = child.coords[1];
        StdDraw.setPenColor(StdDraw.ORANGE);
        if (child.parent != null) {
            double x2 = child.parent.coords[0];
            double y2 = child.parent.coords[1];
            StdDraw.line(x1, y1, x2, y2);
        }
    }

    // erases spine, and erases line from parent spine to child spine
    public void erase(Spine child) {
        double x1 = child.coords[0];
        double y1 = child.coords[1];
        StdDraw.setPenColor(StdDraw.BLACK);
        if (child.parent != null) {
            double x2 = child.parent.coords[0];
            double y2 = child.parent.coords[1];
            StdDraw.line(x1, y1, x2, y2);
        }
    }

    // validation that spine tree is working correctly
    public static void main(String[] args) {
        SpineTree st = new SpineTree(1);
        StdOut.printf("Sizes before insertion: \n");
        StdOut.printf("SpineTree: %d\n", st.size);
        StdOut.printf("Randomized array: %d\n", st.randomArray.size());
        StdOut.printf("Priority queue: %d\n", st.priorityQueue.size());
        st.insert(null, new Spine(new double[]{StdRandom.uniformInt(100),
                StdRandom.uniformInt(100)}), Double.POSITIVE_INFINITY);
        for (int i = 0; i < 5; i++) {
            st.insert(new Spine(new double[]{StdRandom.uniformInt(100),
                            StdRandom.uniformInt(100)}),
                    new Spine(new double[]{StdRandom.uniformInt(100),
                            StdRandom.uniformInt(100)}), i);
        }
        StdOut.printf("Sizes after insertion: \n");
        StdOut.printf("SpineTree: %d\n", st.size);
        StdOut.printf("Randomized array: %d\n", st.randomArray.size());
        StdOut.printf("Priority queue: %d\n", st.priorityQueue.size());
        for (int i = 0; i < 7; i++) st.delete(i);
        StdOut.printf("Sizes after deletion: \n");
        StdOut.printf("SpineTree: %d\n", st.size);
        StdOut.printf("Randomized array: %d\n", st.randomArray.size());
        StdOut.printf("Priority queue: %d\n", st.priorityQueue.size());
    }
}
