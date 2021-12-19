package main;

import gui.GuiKeyBinding;
import gui.GuiMouseListener;
import javax.swing.JButton;
import javax.swing.KeyStroke;

/**
 *
 * @author hugop
 */
public class Node implements Comparable<Node> {

    private final JButton node;
    private int f_cost;
    private int g_cost;
    private int h_cost;
    private final int row;
    private final int column;
    private boolean Obstacles;
    private boolean ClosedList;
    private boolean OpenList;
    private final GuiKeyBinding keyBinding;
    private Node parent;

    public Node(int row, int column) {
        node = new JButton();
        keyBinding = new GuiKeyBinding();
        g_cost = 0;
        h_cost = 0;
        this.f_cost = g_cost + h_cost;
        this.row = row;
        this.column = column;
        this.Obstacles = false;
        this.ClosedList = false;
        this.OpenList = false;
        node.addMouseListener(new GuiMouseListener());
        node.getInputMap().put(KeyStroke.getKeyStroke("S"), "allowDrawing");
        node.getActionMap().put("allowDrawing", keyBinding.getAllowDrawingAction());
        node.getInputMap().put(KeyStroke.getKeyStroke("D"), "stopDrawing");
        node.getActionMap().put("stopDrawing", keyBinding.getStopDrawingAction());
    }

    @Override
    public int compareTo(Node o) {
        // On trie la liste selon les f_cost
        return this.f_cost - o.f_cost;
    }

    @Override
    public String toString() {
        return "F_cost:" + f_cost + ", G_cost:" + g_cost + ", H_cost" + h_cost;
    }

    public JButton getNode() {
        return node;
    }

    public boolean isObstacles() {
        return Obstacles;
    }

    public void setObstacles(boolean Obstacles) {
        this.Obstacles = Obstacles;
    }

    public boolean isClosedList() {
        return ClosedList;
    }

    public void setClosedList(boolean ClosedList) {
        this.ClosedList = ClosedList;
    }

    public boolean isOpenList() {
        return OpenList;
    }

    public void setOpenList(boolean OpenList) {
        this.OpenList = OpenList;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public int getF_cost() {
        return f_cost;
    }

    public int getG_cost() {
        return g_cost;
    }

    public int getH_cost() {
        return h_cost;
    }

    public void setF_cost(int f_cost) {
        this.f_cost = f_cost;
    }

    public void setG_cost(int g_cost) {
        this.g_cost = g_cost;
    }

    public void setH_cost(int h_cost) {
        this.h_cost = h_cost;
    }

}
