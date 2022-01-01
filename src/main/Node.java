package main;

import gui.GuiKeyBinding;
import gui.GuiMouseListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.KeyStroke;

public class Node {

    private final JButton pathEntity;
    private int f_cost;
    private int g_cost;
    private int h_cost;
    private int row;
    private int column;
    private final GuiKeyBinding keyBinding;
    private Node parent;

    public Node(int row, int column) {
        pathEntity = new JButton();
        keyBinding = new GuiKeyBinding();
        this.row = row;
        this.column = column;
        pathEntity.addMouseListener(new GuiMouseListener());
        pathEntity.setBackground(Color.WHITE);
        pathEntity.setFocusPainted(false);
        pathEntity.setBorderPainted(false);
        pathEntity.getInputMap().put(KeyStroke.getKeyStroke("S"), "allowDrawing");
        pathEntity.getActionMap().put("allowDrawing", keyBinding.getAllowDrawingAction());
        pathEntity.getInputMap().put(KeyStroke.getKeyStroke("D"), "stopDrawing");
        pathEntity.getActionMap().put("stopDrawing", keyBinding.getStopDrawingAction());
    }

    @Override
    public String toString() {
        return "F_cost:" + f_cost + ", G_cost:" + g_cost + ", H_cost" + h_cost;
    }

    public void copy(Node o) {
        this.row = o.getRow();
        this.column = o.getColumn();
    }

    public JButton getPathEntity() {
        return pathEntity;
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
