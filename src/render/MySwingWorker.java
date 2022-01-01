package render;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import main.AStar;
import main.Node;

import main.main;

public class MySwingWorker {

    private Node endNode;
    private final SwingWorker sw1;
    private boolean showAlgorithmSteps;
    private final ArrayList<AStar> AStarList;
    private int listOfEndNodesElement;
    private final DecimalFormat df;
    private HashMap<String, String> finishOrder;
    private int ActualAStarInstance;

    public MySwingWorker() {

        AStarList = new ArrayList<>();
        df = new DecimalFormat("###.##");
        showAlgorithmSteps = true;
        listOfEndNodesElement = 0;
        finishOrder = new HashMap<>();
        ActualAStarInstance = 0;

        this.sw1 = new SwingWorker() {

            @Override
            protected String doInBackground() {
                // define what thread will do here
                // Debug
//                System.out.println("AStarList.size(): " + AStarList.size());
//                for (int i = 0; i < AStarList.size(); i++) {
//                    System.out.println("RunAlgoList " + i + ": " + AStarList.get(i).getRunAlgoList());
//                }
                long totalStart = System.nanoTime();
                while (ActualAStarInstance < AStarList.size()) {
                    long start = System.nanoTime();
                    System.out.println(start);
                    endNode = AStarList.get(ActualAStarInstance).getListOfEndNodes().get(listOfEndNodesElement);
                    AStarList.get(ActualAStarInstance).getRunAlgoList().set(listOfEndNodesElement, AStarList.get(ActualAStarInstance).aStarAlgorithm(endNode, showAlgorithmSteps));
                    // If statement is true when partial path has been found
                    if (AStarList.get(ActualAStarInstance).getRunAlgoList().get(listOfEndNodesElement) == true) {
                        for (int s = 0; s < AStarList.size(); s++) {
                            System.out.println("RunAlgoList " + s + ": " + AStarList.get(s).getRunAlgoList());
                        }
                        AStarList.get(ActualAStarInstance).getOpenSet().clear();
                        AStarList.get(ActualAStarInstance).getNeighbours().clear();
                        AStarList.get(ActualAStarInstance).getOpenSet().add(AStarList.get(ActualAStarInstance).getListOfEndNodes().get(listOfEndNodesElement));
                        listOfEndNodesElement++;

                        // If statement is true when total path has been found (case of steps before final endNode)
                        if (listOfEndNodesElement == AStarList.get(ActualAStarInstance).getRunAlgoList().size()) {
                            AStarList.get(ActualAStarInstance).RetracePath(AStarList.get(ActualAStarInstance).getStartingNode(), endNode);
                            AStarList.get(ActualAStarInstance).setStartingNode(endNode);
                            listOfEndNodesElement = 0;
                            ActualAStarInstance++;
                            long end = System.nanoTime();
                            long exec = end - start;
                            double inSeconds = (double) exec / 1_000_000_000.0;
                            DecimalFormat df = new DecimalFormat("###.####");
                            finishOrder.put("Path N°" + ActualAStarInstance, " achieved in " + df.format(inSeconds) + " ms");
                            //Debug
//                            System.out.println("AStarList.size() after remove: " + AStarList.size());
//                            System.out.println("iterator value at begin: " + i);
//                            System.out.println("End node element: " + listOfEndNodesElement);
//                            for (int q = 0; q < AStarList.size(); q++) {
//                                System.out.println("RunAlgoList " + q + ": " + AStarList.get(q).getRunAlgoList());
//                            }
                        }
                    }
                }
                Map<String, String> finishOrderSorted = sortByValue(finishOrder);
                System.out.println(finishOrderSorted);
                long end = System.nanoTime();
                long exec = end - totalStart;
                double inSeconds = (double) exec / 1_000_000_000.0;
                String headLine = "Total time: " + df.format(inSeconds) + " ms\n";
                String result = "";
                int index = 1;
                for (Map.Entry<String, String> entry : finishOrderSorted.entrySet()) {
                    if (index == 1) {
                        result = result + "Fasted node: " + entry.getKey() + entry.getValue() + "\n";
                    } else {
                        result = result + "Position: " + index + " : " + entry.getKey() + entry.getValue() + "\n";
                    }

                    index++;
                }
                String totalResult = result + headLine;
                JOptionPane.showMessageDialog(main.getGui().getGui(), totalResult.replace("=", ""), "Info", JOptionPane.PLAIN_MESSAGE);
                listOfEndNodesElement = 0;

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

    // function to sort hashmap by values
    public static HashMap<String, String> sortByValue(HashMap<String, String> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String>> list = new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (Map.Entry<String, String> o1, Map.Entry<String, String> o2) -> (o1.getValue()).compareTo(o2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, String> temp = new LinkedHashMap<>();
        list.forEach(aa -> {
            temp.put(aa.getKey(), aa.getValue());
        });
        return temp;
    }

    public ArrayList<AStar> getAStarList() {
        return AStarList;
    }

    public SwingWorker getSw1() {
        return sw1;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void setShowAlgorithmSteps(boolean showAlgorithmSteps) {
        this.showAlgorithmSteps = showAlgorithmSteps;
    }

    public Node getEndNode() {
        return endNode;
    }

}
