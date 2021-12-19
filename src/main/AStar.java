package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author hugop
 */
public class AStar {

    private Node startNode;
    private Node endNode;
    private Node currentNeighbour;
    private final ArrayList<Node> openList;
    private final ArrayList<Node> closedList;
    private final ArrayList<Node> listOfNodes;
    private final ArrayList<Node> neighbours;
    private final ArrayList<Node> listOfParent;
    private int endNodeCount;
    private JButton pathMember;

    public AStar(Node endNode) {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        neighbours = new ArrayList<>();
        listOfParent = new ArrayList<>();
        listOfNodes = main.getGui().getListOfAll();
        this.endNode = endNode;
        this.endNodeCount = 0;
        endNodeCount++;
    }

    public AStar() {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        neighbours = new ArrayList<>();
        listOfParent = new ArrayList<>();
        listOfNodes = main.getGui().getListOfAll();
    }

    public final void generateRandomGrid() {
        // Generate the startNode and add it to the openList and paint it on the GUI
        Random rd = new Random();
        int startNodeSelection = rd.nextInt(listOfNodes.size());
        startNode = listOfNodes.get(startNodeSelection);
        openList.add(startNode);
        startNode.setOpenList(true);
        main.getGui().paintStartNode(startNode);

        // generate an endNode whom is different from the startNode and paint it on the GUI
        if (main.getGui().getChooseMode().isSelected() == false) {
            int endNodeSelection = rd.nextInt(listOfNodes.size());
            while (listOfNodes.get(endNodeSelection) == startNode) {
                endNodeSelection = rd.nextInt(listOfNodes.size());
            }
            endNode = listOfNodes.get(endNodeSelection);
            main.getGui().paintEndNode(endNode);
        }

//         generate all the obstacles randomly
        main.getGui().generateObstacles(startNode, endNode, rd);

    }

    public boolean aStarAlgorithm() {
        // Order the openList

        System.out.println("----------------------------");
        // Create a local variable called current, the node which is currently evaluated
        // the local variable is the node with the lowest f_cost, so the element 0 of our sorted openList
        Node current = openList.get(0);
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).getF_cost() < current.getF_cost() || (openList.get(i).getF_cost() == current.getF_cost() && openList.get(i).getH_cost() < current.getH_cost())) {
                current = openList.get(i);
            }
        }
        openList.remove(current);
        current.setOpenList(false);
        // Add the current node to the closedList
        closedList.add(current);
        current.setClosedList(true);
        System.out.println(current == endNode);
        // If current node is equal to our endNode, then the path has been found
        if (current == endNode) {
            RetracePath(startNode, endNode);
            return false;
        } else {
            // Create the neighbourS of the current node
            neighbourCreation(current);
            // Loop through the neighbours
            for (int i = 0; i < neighbours.size(); i++) {
                // Attribute to the field currentNeighbour the value of the neighbour from neighbours being evaluated
                currentNeighbour = neighbours.get(i);
                // If neighbour is not traversable of neighbour is in closedList we skip to the next iteration.
                if (closedList.contains(currentNeighbour) || currentNeighbour.isObstacles() == true) {
                    continue;
                }
                int new_path = current.getG_cost() + GetDistance(current, currentNeighbour);

                // If the new path to the neighbour is shorter or neighbour is not in openList then
                if (new_path < currentNeighbour.getG_cost() || !openList.contains(currentNeighbour)) {

                    // I set the f_cost, g_cost and h_cost of the currentNeighbour
                    currentNeighbour.setG_cost(new_path);
                    currentNeighbour.setH_cost(GetDistance(currentNeighbour, endNode));
                    currentNeighbour.setF_cost(currentNeighbour.getG_cost() + currentNeighbour.getH_cost());
                    currentNeighbour.setParent(current);
                    // For visual
//                    currentNeighbour.getNode().setText(String.valueOf(currentNeighbour.getF_cost() + "," + currentNeighbour.getG_cost() + "," + currentNeighbour.getH_cost()));
                    if (!openList.contains(currentNeighbour)) {
                        openList.add(currentNeighbour);
                    }
                    if (currentNeighbour != endNode && "".equals(currentNeighbour.getNode().getText())) {
                        currentNeighbour.getNode().setBackground(Color.green);
                    }
                    if (current != startNode && "".equals(current.getNode().getText())) {
                        current.getNode().setBackground(Color.yellow);
                    }

                }
            }
            neighbours.clear();
            return true;
        }
    }

    public void RetracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != startNode) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        ArrayList<Node> reversePath = new ArrayList<>();
        for (int i = path.size() - 1; i > 0; i--) {
            reversePath.add(path.get(i));
        }
        for (int i = 0; i < reversePath.size(); i++) {
            pathMember = reversePath.get(i).getNode();

            if ("".equals(pathMember.getText())) {
                try {
                    Thread.sleep((long) (0.05 * 1000));
                    pathMember.setBackground(Color.CYAN);

                } catch (InterruptedException ex) {
                    Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (endNode.getNode().getText().equals(pathMember.getText()) == false) {
                pathMember.setText(endNode.getNode().getText() + "," + pathMember.getText());
                pathMember.setToolTipText(pathMember.getText());
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

    public void neighbourCreation(Node current) {
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
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public ArrayList<Node> getOpenList() {
        return openList;
    }

    public ArrayList<Node> getClosedList() {
        return closedList;
    }

    public ArrayList<Node> getListOfNodes() {
        return listOfNodes;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public Node getCurrentNeighbour() {
        return currentNeighbour;
    }

    public ArrayList<Node> getListOfParent() {
        return listOfParent;
    }

    public int getEndNodeCount() {
        return endNodeCount;
    }
}
