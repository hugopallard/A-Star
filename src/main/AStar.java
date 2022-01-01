package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hugop
 */
public class AStar {
    
    private final ArrayList<Node> listOfNodes;
    private final ArrayList<Node> neighbours;
    private final ArrayList<Node> listOfEndNodes;
    private final ArrayList<Boolean> runAlgoList;
    private ArrayList<Node> openSet;
    private ArrayList<Node> closedSet;
    
    private Node startingNode;
    
    public AStar() {
        neighbours = new ArrayList<>();
        openSet = new ArrayList<>();
        closedSet = new ArrayList<>();
        runAlgoList = new ArrayList<>();
        listOfNodes = main.getGui().getListOfAll();
        listOfEndNodes = new ArrayList<>();
    }
    
    public boolean aStarAlgorithm(Node endingNode, boolean showAlgorithmSteps) {
        System.out.println("----------------------------");
        Node current = openSet.get(0);
        for (int i = 0; i < openSet.size(); i++) {
            if (openSet.get(i).getF_cost() < current.getF_cost() || (openSet.get(i).getF_cost() == current.getF_cost() && openSet.get(i).getH_cost() < current.getH_cost())) {
                current = openSet.get(i);
            }
        }
        // Create a local variable called current, the node which is currently evaluated
        // the local variable is the node with the lowest f_cost, so the element 0 of our sorted openSet
        openSet.remove(current);
        // Add the current node to the closedList
        closedSet.add(current);
        System.out.println(current == endingNode);
        // If current node is equal to our endingNode, then the path has been found
        if (current == endingNode) {
            neighbours.clear();
            return true;
        } else {
            // Creation of neighour node of our current node
            // They are the nodes with a distance of 1 of our current node.
            for (int i = 0; i < listOfNodes.size(); i++) {
                //System.out.println(listOfNodes.get(i).getRow());
                int rowGap = Math.abs(listOfNodes.get(i).getRow() - current.getRow());
                int columnGap = Math.abs(listOfNodes.get(i).getColumn() - current.getColumn());
                if ((rowGap == 1 && columnGap == 0) || (rowGap == 0 && columnGap == 1)
                        || (rowGap == 1 && columnGap == 1)) {
                    neighbours.add(listOfNodes.get(i));
                }
            }
            // Loop through the neighbours
            for (int i = 0; i < neighbours.size(); i++) {
                // Attribute to the field currentNeighbour the value of the neighbour from neighbours being evaluated
                Node currentNeighbour = neighbours.get(i);
                // If neighbour is not traversable of neighbour is in closedList we skip to the next iteration.
                if (closedSet.contains(currentNeighbour) || main.getGui().getObstaclesList().contains(currentNeighbour) == true) {
                    continue;
                }
                int new_path = current.getG_cost() + GetDistance(current, currentNeighbour);

                // If the new path to the neighbour is shorter or neighbour is not in openSet then
                if (new_path < currentNeighbour.getG_cost() || !openSet.contains(currentNeighbour)) {

                    // I set the f_cost, g_cost and h_cost of the currentNeighbour
                    currentNeighbour.setG_cost(new_path);
                    currentNeighbour.setH_cost(GetDistance(currentNeighbour, endingNode));
                    currentNeighbour.setParent(current);
                    // For visual
//                    currentNeighbour.getPathEntity().setText(String.valueOf(currentNeighbour.getF_cost() + "," + currentNeighbour.getG_cost() + "," + currentNeighbour.getH_cost()));
                    if (!openSet.contains(currentNeighbour)) {
                        openSet.add(currentNeighbour);
                    }
                    if (showAlgorithmSteps && currentNeighbour != endingNode && !listOfEndNodes.contains(currentNeighbour) && !listOfEndNodes.contains(current)) {
                        currentNeighbour.getPathEntity().setBackground(Color.green);
                    }
                    if (showAlgorithmSteps && current != startingNode && !listOfEndNodes.contains(current)) {
                        current.getPathEntity().setBackground(Color.yellow);
                    }
                }
            }
            return false;
        }
    }
    
    public void RetracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<>();
        while (endNode != startNode) {
            path.add(endNode);
            endNode = endNode.getParent();
        }
        ArrayList<Node> reversePath = new ArrayList<>();
        for (int i = path.size() - 1; i > 0; i--) {
            if (!listOfEndNodes.contains(path.get(i))) {
                path.get(i).getPathEntity().setBackground(Color.CYAN);
                reversePath.add(path.get(i));
            }
        }
        for (int i = 1; i < reversePath.size(); i++) {
            try {
                Thread.sleep(20);
                reversePath.get(i - 1).getPathEntity().setBackground(Color.CYAN);
                reversePath.get(i).getPathEntity().setBackground(Color.RED);
            } catch (InterruptedException ex) {
                Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public int GetDistance(Node nodeA, Node nodeB) {
        int dx = Math.abs(nodeA.getRow() - nodeB.getRow());
        int dy = Math.abs(nodeA.getColumn() - nodeB.getColumn());
        if (dx > dy) {
            return 14 * dy + 10 * (dx - dy);
        }
        return 14 * dx + 10 * (dy - dx);
    }
    
    public ArrayList<Node> getOpenSet() {
        return openSet;
    }
    
    public void setOpenSet(ArrayList<Node> openSet) {
        this.openSet = openSet;
    }
    
    public ArrayList<Node> getClosedSet() {
        return closedSet;
    }
    
    public void setClosedSet(ArrayList<Node> closedSet) {
        this.closedSet = closedSet;
    }
    
    public ArrayList<Node> getListOfNodes() {
        return listOfNodes;
    }
    
    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }
    
    public Node getStartingNode() {
        return startingNode;
    }
    
    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }
    
    public ArrayList<Boolean> getRunAlgoList() {
        return runAlgoList;
    }
    
    public ArrayList<Node> getListOfEndNodes() {
        return listOfEndNodes;
    }
    
}
