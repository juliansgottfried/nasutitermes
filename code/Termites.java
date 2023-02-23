/*
Description: Termite data structure

Author: Julian Gottfried
 */

public class Termites {

    // current coordinates of termites
    double[] coords;
    // spine where termites came from
    Spine progenitor;
    // previous termites in doubly-linked list
    Termites prev;
    // next termites in doubly-linked list
    Termites next;

    public Termites(Spine spine) {
        progenitor = spine;
        coords = new double[]{spine.coords[0], spine.coords[1]};
        prev = null;
        next = null;
    }

    // update coordinates
    public void walk(double n) {
        // random walk
        int direction = StdRandom.uniformInt(4);
        // wrap termite x-coords – map is a flattened cylinder
        if (direction == 0) coords[0] = (coords[0] + 1) % n;
        else if (direction == 1) coords[0] = (((coords[0] - 1) % n) + n) % n;
        else if (direction == 2) coords[1]++;
        else coords[1]--;
    }

    // remove termites from doubly-linked list, and return first termites in list
    public Termites remove(Termites first) {
        if (prev == null) {
            if (next != null) next.prev = null;
            return next;
        } else {
            if (next != null) next.prev = prev;
            prev.next = next;
        }
        return first;
    }

    // add termites to doubly-linked list, and return first termites in list
    public Termites insert(Termites first) {
        if (first != null) {
            this.next = first;
            first.prev = this;
        }
        return this;
    }

    // string of coordinates of termite and coordinates of progenitor spine
    public String toString() {
        return "x = " + coords[0] +
                "\ny = " + coords[1] +
                "\nprogenitor x = " + progenitor.coords[0] +
                "\nprogenitor y = " + progenitor.coords[1] + "\n\n";
    }
}
