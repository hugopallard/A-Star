package render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public MySwingWorker() {
        AStarList = new ArrayList<>();
        runAlgoList = new ArrayList<>();
        openSet = new ArrayList<>();
        this.sw1 = new SwingWorker() {

            @Override
            protected String doInBackground() {
                // define what thread will do here
                try {
                    start = System.nanoTime();
                    System.out.println("Nombre d'instance: " + AStarList.size());
                    System.out.println("Taille runAlgoList: " + runAlgoList.size());
                    int cpt = 0;
                    while ((Collections.frequency(runAlgoList, false) == runAlgoList.size()) == false) {
                        try {
                            Thread.sleep((long) (0.01 * 1000));
                            endNode = AStarList.get(cpt).getEndNode();
                            runAlgoList.set(cpt, AStarList.get(cpt).aStarAlgorithm(startNode, endNode, openSet));
                            // We give a maximum time for the algorithm to find a path,
                            // It takes 0.15 sec for i to go from 0 to 1;
                            // So 0.15*100 for i to reach 15.xxx sec
                            // Safe switch is around 15 sec before stopping purposefully the program
                            if (runAlgoList.get(cpt) == false) {
                                openSet.clear();
                                startNode = AStarList.get(cpt).getEndNode();
                                AStarList.get(cpt).setStartNode(startNode);
                                openSet.add(startNode);
                                AStarList.get(cpt).getStartNode().setOpenList(true);
                                startNode.getNode().setBackground(Color.PINK);
                                cpt++;
                                if (cpt < AStarList.size()) {
                                    endNode = AStarList.get(cpt).getEndNode();
                                    endNode.getNode().setBackground(Color.ORANGE);
                                }
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        runAlgo = false;
                    }
                    while (runAlgo == true) {
                        try {
                            Thread.sleep((long) (0.01 * 1000));
                            runAlgo = AStar.aStarAlgorithm();
                            // We give a maximum time for the algorithm to find a path,
                            // It takes 0.15 sec for i to go from 0 to 1;
                            // So 0.15*100 for i to reach 15.xxx sec
                            // Safe switch is around 15 sec before stopping purposefully the program
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println(AStar.getListOfParent().size());
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

}
