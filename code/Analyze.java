/*
Description: reads integer TIME from command line. Runs simulation for combinations of ERT and C up
to TIME, each simulation being run for TIME time steps. Reports progress through ERT combinations
to standard output. Writes data after each simulation run to file named output.csv, which is placed
in the same directory as the code. Code is threaded for efficiency.

Author: Julian Gottfried
 */

import static java.lang.Boolean.FALSE;

public class Analyze implements Runnable {

    // Simulation object
    Sim sim;

    // file to write to
    Out file;

    public Analyze(int ERT, int CONSUMPTION, int TIME, Out file) {
        // set output file
        this.file = file;
        // initialize new simulation run without animation
        sim = new Sim(ERT, CONSUMPTION, TIME, FALSE);
        // read in set random map
        sim.readData();
    }

    public void run() {
        // run simulation
        sim.run();
        // write data to file
        file.print(sim.toString());
    }

    public static void main(String[] args) {
        int TIME = Integer.parseInt(args[0]);
        String table = System.getProperty("user.dir") + "/output.csv";
        Out file = new Out(table);
        file.print("ERT, C, time step, p eaten, branching mean, branching sd\n");
        // Set random seed for simulation runs
        StdRandom.setSeed(System.currentTimeMillis());
        for (int ERT = 1; ERT <= TIME; ERT += 100) {
            // Progress report
            StdOut.printf("ERT: %d; %f%% ERTs complete; running C combinations\n",
                    ERT, 100 * (ERT / (double) TIME));
            // combinations of C are exponential for efficiency
            for (int C = 1; C <= TIME; C *= 2) {
                Analyze analyze = new Analyze(ERT, C, TIME, file);
                Thread obj = new Thread(analyze);
                obj.start();
            }
        }
        file.close();
    }
}
