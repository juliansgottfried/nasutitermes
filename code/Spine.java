/*
Description: spine data structure.

Author: Julian Gottfried
 */

public class Spine implements Comparable<Spine> {

    // time at which spine began to be consumed
    double time;
    // index within resizing array
    int index;
    // whether or not spine has been removed
    int removed;
    // count of number of children spines this spine connects to
    int numChildren;
    // parent spine
    Spine parent;
    // coordinates of spine
    double[] coords;
    // number of branches that lead from root to this spine. Root spine has 0 branch lengths; children
    // spine branch lengths are updated later.
    double numBranch = 0;

    public Spine(double[] coords) {
        this.coords = new double[]{coords[0], coords[1]};
        this.time = 0;
        numChildren = 0;
        parent = null;
        removed = 0;
    }

    // decrease parent's children count
    public void removeAsChild() {
        if (parent != null) parent.numChildren--;
    }

    // add spine
    public void addAsChild(Spine parent) {
        // set parent spine
        this.parent = parent;
        // if not root, then increase parent spine's children count, and set number of branches
        // as one more than parent's
        if (parent != null) {
            parent.numChildren++;
            this.numBranch = parent.numBranch + 1;
        }
    }

    // string to validate fields
    public String toString() {
        String str = "x = " + coords[0] +
                "\ny = " + coords[1] +
                "\ntime = " + time +
                "\nremoved = " + removed +
                "\nnum children = " + numChildren;
        if (parent != null) str += "\nparent index = " + parent.index;
        else str += "\nparent = null";
        return str + "\n\n";
    }

    // compare spines based on time, for priority queue functionality.
    public int compareTo(Spine that) {
        return Double.compare(this.time, that.time);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Spine)) return false;
        Spine that = (Spine) other;
        return (Double.compare(this.time, that.time) == 0);
    }

    public int hashCode() {
        assert (false);
        return 42;
    }
}
