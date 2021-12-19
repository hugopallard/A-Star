package main;

// Java program to illustrate
// working of MySwingWorker
import gui.GUI;
import render.MySwingWorker;

public class main {

    private static AStar AStar;
    private static GUI gui;
    private static int endNodeCount;
    private static MySwingWorker swingWorker;

    public static void start() {
        gui.getWindowMenu().setEnabled(false);
        gui.getOptionGroup().clearSelection();
        gui.getGenerateObstacles().setEnabled(false);
        gui.getEraseObstacles().setEnabled(false);
        gui.getChooseStartPos().setEnabled(false);
        gui.getChooseEndPos().setEnabled(false);
        gui.getResetGrid().setEnabled(true);
        gui.getStartAlgorithmButton().setEnabled(false);
        gui.getGenerateRandomGrid().setEnabled(false);
        gui.getChooseMode().setEnabled(false);

        swingWorker.getSw1().execute();
        swingWorker = new MySwingWorker();
    }

    public static void main(String[] args) {
        endNodeCount = 0;
        gui = new GUI();
        swingWorker = new MySwingWorker();
    }

    public static GUI getGui() {
        return gui;
    }

    public static int getEndNodeCount() {
        return endNodeCount;
    }

    public static void setEndNodeCount(int endNodeCount) {
        main.endNodeCount = endNodeCount;
    }

    public static MySwingWorker getSwingWorker() {
        return swingWorker;
    }

    public static AStar getAStar() {
        return AStar;
    }
}
