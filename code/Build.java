/*
Description: builds a user-generated spine map. Reads two command line arguments. The first is an
integer which specifies map edge length (n by n). The second is the name of the map file the user
wishes to create. Draws white n by n map. When user clicks on a box, fills box with black, and writes
coordinates of boxes clicked in file, separated by spaces. First number written in file is n, the
length of the map. When user presses q, build program closes file. Places file in same directory as
code.

Author: Julian Gottfried
 */

public class Build {

    public static void main(String[] args) {

        double n = Double.parseDouble(args[0]);

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n);
        StdDraw.filledSquare(n / 2, n / 2, n / 2);

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        String filename = args[1];
        filename = System.getProperty("user.dir") + "/" + filename;
        Out file = new Out(filename);

        file.print(n + " ");

        while (true) {
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_Q)) {
                file.close();
                break;
            }
            // detected mouse click
            if (StdDraw.isMousePressed()) {
                // screen coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                // convert to (row, col)
                int row = (int) (n - ((int) y) - 1);
                int col = (int) x;
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col + 0.5, n - row - 0.5, 0.5);
                file.print(x + " ");
                file.print(y + " ");
            }
            StdDraw.show();
        }
    }
}
