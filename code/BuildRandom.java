/*
Description: helper class. Makes a random map of 90 coordinate points on a 50 by 50 coordinate plane,
each coordinate separated by a space. The first value is 50, the size of the map. Writes map to file
named map.txt in same directory as code.

Author: Julian Gottfried
 */

public class BuildRandom {

    public static void main(String[] args) {

        Out file = new Out(System.getProperty("user.dir") + "/map.txt");

        file.print(50 + " ");

        for (int i = 0; i < 90; i++) {
            int x = StdRandom.uniformInt(0, 50);
            int y = StdRandom.uniformInt(0, 50);
            file.print(x + " ");
            file.print(y + " ");
        }
    }

}
