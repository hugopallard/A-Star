package render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import main.AStar;
import main.Node;

import main.main;

public class MySwingWorker {

    private AStar AStar;
    private boolean runAlgo = true;
    private long start;
    private ArrayList<AStar> AStarList;
    private ArrayList<Boolean> runAlgoList;
    private ArrayList<Node> openSet;
    private final SwingWorker sw1;
    private Node startNode;
    private Node endNode;
    private boolean showAlgorithmSteps;

    public MySwingWorker() {
        AStarList = new ArrayList<>();
        runAlgoList = new ArrayList<>();
        openSet = new ArrayList<>();
        showAlgorithmSteps = false;
        this.sw1 = new SwingWorker() {

            @Override
            protected String doInBackground() {
                // define what thread will do here
                try {
                    start = System.nanoTime();
                    int cpt = 0;
                    while ((Collections.frequency(runAlgoList, false) == runAlgoList.size()) == false) {
                        endNode = AStarList.get(cpt).getEndNode();
                        runAlgoList.set(cpt, AStarList.get(cpt).aStarAlgorithm(startNode, endNode, openSet, showAlgorithmSteps));

                        if (runAlgoList.get(cpt) == false) {
                            openSet.clear();
                            startNode = AStarList.get(cpt).getEndNode();
                            AStarList.get(cpt).setStartNode(startNode);
                            openSet.add(startNode);
                            cpt++;
                            if (cpt < AStarList.size()) {
                                endNode = AStarList.get(cpt).getEndNode();
                            }
                        }
                        runAlgo = false;
                    }
                    while (runAlgo == true) {
                        runAlgo = AStar.aStarAlgorithm(showAlgorithmSteps);
                    }
                    JOptionPane.showMessageDialog(main.getGui().getGui(), "Path has been found !", "Info", JOptionPane.PLAIN_MESSAGE);
                } catch (IndexOutOfBoundsException d) {
                    JOptionPane.showMessageDialog(main.getGui().getGui(), "Path not found, please reset the grid", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                return null;
            }

            @Override
            protected void process(List chunks) {
                // define what the event dispatch thread
                // will do with the intermediate results received
                // while the thread is executing
            }

            @Override
            protected void done() {
                // this method is called when the background
                // thread finishes execution
            }
        };
    }

    public SwingWorker getSw1() {
        return sw1;
    }

    public long getStart() {
        return start;
    }

    public AStar getAStar() {
        return AStar;
    }

    public void setAStar(AStar AStar) {
        this.AStar = AStar;
    }

    public void setRunAlgo(boolean runAlgo) {
        this.runAlgo = runAlgo;
    }

    public ArrayList<AStar> getAStarList() {
        return AStarList;
    }

    public ArrayList<Boolean> getRunAlgoList() {
        return runAlgoList;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public ArrayList<Node> getOpenSet() {
        return openSet;
    }

    public void setShowAlgorithmSteps(boolean showAlgorithmSteps) {
        this.showAlgorithmSteps = showAlgorithmSteps;
    }

}
