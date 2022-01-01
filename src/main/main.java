package main;

import gui.GUI;
import render.MySwingWorker;

public class main {

    private static GUI gui;
    private static MySwingWorker swingWorker;
    private static int AStarInstanceNumber;

    public static void start() {
        gui.getWindowMenu().setEnabled(false);
        gui.getOptionGroup().clearSelection();
        gui.getGenerateObstacles().setEnabled(false);
        gui.getEraseObstacles().setEnabled(false);
        gui.getChooseStartPos().setEnabled(false);
        gui.getChooseEndPos().setEnabled(false);
        gui.getResetGrid().setEnabled(true);
        gui.getStartAlgorithmButton().setEnabled(false);
        swingWorker.getSw1().execute();
        swingWorker = new MySwingWorker();
    }

    public static void main(String[] args) {
        AStarInstanceNumber = -1;
        gui = new GUI();
        swingWorker = new MySwingWorker();
    }

    public static GUI getGui() {
        return gui;
    }

    public static MySwingWorker getSwingWorker() {
        return swingWorker;
    }

    public static int getAStarInstanceNumber() {
        return AStarInstanceNumber;
    }

    public static void setAStarInstanceNumber(int AStarInstanceNumber) {
        main.AStarInstanceNumber = AStarInstanceNumber;
    }

}
