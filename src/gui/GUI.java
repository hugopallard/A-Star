package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import main.Node;
import main.main;

/**
 *
 * @author hugop
 */
public class GUI {

    private final JFrame gui;
    private final JPanel contentPanel;
    private JPanel sidePanel;
    private JPanel controlPanel;
    private JPanel selectPanel;
    private JPanel actionPanel;
    private int matrixSize;
    private final ArrayList<Node> listOfAll;
    private boolean allowDrawing;
    private final GuiMenuBarListener guiMenuBarListener;
    private JButton startAlgorithmButton;
    private JButton resetGrid;
    private JLabel tittleLabel;
    private JRadioButton generateObstacles;
    private JRadioButton chooseStartPos;
    private JRadioButton chooseEndPos;
    private JRadioButton eraseObstacles;
    private ButtonGroup optionGroup;
    private JSlider widthSelectorSlider;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu recordMenu;
    private JMenu helpMenu;
    private JMenu windowMenu;
    private JMenuItem loadItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem howToDraw;
    private JMenuItem startRecord;
    private JMenuItem stopRecord;
    private JMenuItem changeMatrixSize;
    private JMenuItem screenShot;
    private JMenuItem showSteps;
    private JButton generateRandomGrid;
    private final ArrayList<Node> obstaclesList;

    public GUI() {

        this.listOfAll = new ArrayList<>();
        this.obstaclesList = new ArrayList<>();
        this.guiMenuBarListener = new GuiMenuBarListener();
        this.matrixSize = 50;
        this.allowDrawing = true;

        gui = new JFrame("Pathfinding A*");
        gui.setLayout(new BorderLayout());
        gui.setSize(new Dimension(1300, 800));
        gui.setLocationRelativeTo(null);
        gui.setMinimumSize(new Dimension(800, 600));

        contentPanel = new JPanel();

        menuBar();
        controlPanel();
        createNodes(matrixSize);
        gui.add(contentPanel, BorderLayout.CENTER);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public final void menuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);

        fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        recordMenu = new JMenu("Record");
        recordMenu.setForeground(Color.WHITE);
        windowMenu = new JMenu("Window");
        windowMenu.setForeground(Color.WHITE);
        helpMenu = new JMenu("Help");
        helpMenu.setForeground(Color.WHITE);

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this.guiMenuBarListener);
        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(this.guiMenuBarListener);
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this.guiMenuBarListener);
        startRecord = new JMenuItem("Start Recording");
        startRecord.addActionListener(this.guiMenuBarListener);
        stopRecord = new JMenuItem("Stop Recording");
        stopRecord.addActionListener(this.guiMenuBarListener);
        screenShot = new JMenuItem("Make a screenshot");
        screenShot.addActionListener(this.guiMenuBarListener);
        changeMatrixSize = new JMenuItem("Change Grid Size");
        changeMatrixSize.addActionListener(this.guiMenuBarListener);
        showSteps = new JMenuItem("Show the algorithm steps");
        showSteps.addActionListener(this.guiMenuBarListener);
        howToDraw = new JMenuItem("How to draw");
        howToDraw.addActionListener(this.guiMenuBarListener);

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        recordMenu.add(startRecord);
        recordMenu.add(stopRecord);
        recordMenu.add(screenShot);
        windowMenu.add(changeMatrixSize);
        windowMenu.add(showSteps);
        helpMenu.add(howToDraw);

        menuBar.add(fileMenu);
        menuBar.add(recordMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);

        gui.setJMenuBar(menuBar);

    }

    public final void controlPanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(300, gui.getHeight()));
        sidePanel.setBackground(Color.DARK_GRAY);

        tittleLabel = new JLabel("<HTML><u>Pathfinding<br>Algorithm A*</u>", SwingConstants.CENTER);
        tittleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        tittleLabel.setForeground(Color.WHITE);
        tittleLabel.setPreferredSize(new Dimension(sidePanel.getWidth(), 100));

        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(Color.DARK_GRAY);

        selectPanel = new JPanel();
        selectPanel.setBackground(Color.DARK_GRAY);
        selectPanel.setPreferredSize(new Dimension(sidePanel.getWidth(), 200));
        selectPanel.setLayout(new GridLayout(2, 2, 0, 0));
        selectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        generateObstacles = new JRadioButton("Generate obstacles");
        generateObstacles.setBackground(Color.DARK_GRAY);
        generateObstacles.setFocusPainted(false);
        generateObstacles.setForeground(Color.WHITE);

        eraseObstacles = new JRadioButton("Erase obstacles");
        eraseObstacles.setBackground(Color.DARK_GRAY);
        eraseObstacles.setFocusPainted(false);
        eraseObstacles.setForeground(Color.WHITE);

        chooseStartPos = new JRadioButton("Choose starting pos");
        chooseStartPos.setBackground(Color.DARK_GRAY);
        chooseStartPos.setFocusPainted(false);
        chooseStartPos.setForeground(Color.WHITE);

        chooseEndPos = new JRadioButton("Choose ending pos");
        chooseEndPos.setBackground(Color.DARK_GRAY);
        chooseEndPos.setFocusPainted(false);
        chooseEndPos.setEnabled(false);
        chooseEndPos.setForeground(Color.WHITE);

        optionGroup = new ButtonGroup();
        optionGroup.add(this.generateObstacles);
        optionGroup.add(this.eraseObstacles);
        optionGroup.add(this.chooseStartPos);
        optionGroup.add(this.chooseEndPos);

        selectPanel.add(generateObstacles);
        selectPanel.add(eraseObstacles);
        selectPanel.add(chooseStartPos);
        selectPanel.add(chooseEndPos);

        actionPanel = new JPanel();
        actionPanel.setBackground(Color.DARK_GRAY);
        actionPanel.setLayout(new GridLayout(4, 1, 0, 10));
        actionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        widthSelectorSlider = new JSlider(1, 3, 1);
        widthSelectorSlider.setBackground(Color.DARK_GRAY);
        widthSelectorSlider.setPaintTicks(true);
        widthSelectorSlider.setMajorTickSpacing(1);
        widthSelectorSlider.setPaintLabels(true);
        widthSelectorSlider.setForeground(Color.WHITE);
        widthSelectorSlider.setToolTipText("Choose a width for obstacles drawing");

        generateRandomGrid = new JButton("Generate a random grid");
        generateRandomGrid.setFocusPainted(false);
        generateRandomGrid.addMouseListener(new GuiMouseListener());

        startAlgorithmButton = new JButton("Start Algorithm");
        startAlgorithmButton.setFocusPainted(false);
        startAlgorithmButton.addMouseListener(new GuiMouseListener());

        resetGrid = new JButton("Reset Grid");
        resetGrid.setEnabled(false);
        resetGrid.setFocusPainted(false);
        resetGrid.addMouseListener(new GuiMouseListener());

        actionPanel.add(this.widthSelectorSlider);
        actionPanel.add(this.startAlgorithmButton);
        actionPanel.add(this.generateRandomGrid);
        actionPanel.add(this.resetGrid);

        controlPanel.add(this.selectPanel, BorderLayout.NORTH);
        controlPanel.add(this.actionPanel, BorderLayout.CENTER);

        sidePanel.add(controlPanel, BorderLayout.CENTER);
        sidePanel.add(tittleLabel, BorderLayout.NORTH);

        gui.add(sidePanel, BorderLayout.WEST);
    }

    public final void createNodes(int matrixSize) {
        contentPanel.setLayout(new GridLayout(matrixSize, matrixSize));
        contentPanel.setBackground(Color.DARK_GRAY);
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                Node node = new Node(i, j);
                listOfAll.add(node);
                contentPanel.add(node.getPathEntity());
            }
        }
    }

    public void paintNode(Node startButton, Color color) {
        startButton.getPathEntity().setBackground(color);
    }

    public void paintObstacles(Node obstacle, int width, Color color) {
        if (width == 1) {
            obstacle.getPathEntity().setBackground(color);
        }
        Node currentNode;
        int potentialRow;
        int potentialCol;
        for (int i = 0; i < this.listOfAll.size(); i++) {
            currentNode = main.getGui().getListOfAll().get(i);
            potentialRow = Math.abs(obstacle.getRow() - currentNode.getRow());
            potentialCol = Math.abs(obstacle.getColumn() - currentNode.getColumn());

            if (width == 2 && ((potentialRow == 1 && potentialCol == 0) || (potentialRow == 0 && potentialCol == 1))) {
                currentNode.getPathEntity().setBackground(color);
                obstaclesList.add(currentNode);
                obstacle.getPathEntity().setBackground(color);
            }

            if (width == 3 && ((potentialRow == 2 && potentialCol == 0)
                    || (potentialRow == 0 && potentialCol == 2)
                    || (potentialRow == 1 && potentialCol == 0)
                    || (potentialRow == 0 && potentialCol == 1)
                    || (potentialRow == 1 && potentialCol == 1))) {
                obstaclesList.add(currentNode);
                currentNode.getPathEntity().setBackground(color);
                obstacle.getPathEntity().setBackground(color);
            }
        }
    }

    public void generateObstacles() {
        Random rd = new Random();
        int selected;
        int numberOfObstacles = rd.nextInt(listOfAll.size() - 50) + 10;  //between 10 and gridsize-50 obstacles
        System.out.println("Nombre d'obstacles:" + numberOfObstacles);
        for (int i = 0; i < numberOfObstacles; i++) {
            selected = rd.nextInt(listOfAll.size());
            obstaclesList.add(listOfAll.get(selected));
            paintObstacles(listOfAll.get(selected), 1, Color.GRAY);
        }
    }

    // ---------- Getters ------------- \\
    public ArrayList<Node> getListOfAll() {
        return listOfAll;
    }

    public JButton getStartAlgorithmButton() {
        return startAlgorithmButton;
    }

    public JButton getResetGrid() {
        return resetGrid;
    }

    public JFrame getGui() {
        return gui;
    }

    public JRadioButton getGenerateObstacles() {
        return generateObstacles;
    }

    public JRadioButton getEraseObstacles() {
        return eraseObstacles;
    }

    public JRadioButton getChooseStartPos() {
        return chooseStartPos;
    }

    public JRadioButton getChooseEndPos() {
        return chooseEndPos;
    }

    public ButtonGroup getOptionGroup() {
        return optionGroup;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JSlider getWidthSelectorSlider() {
        return widthSelectorSlider;
    }

    public boolean isAllowDrawing() {
        return allowDrawing;
    }

    public void setAllowDrawing(boolean allowDrawing) {
        this.allowDrawing = allowDrawing;
    }

    public JMenuItem getLoadItem() {
        return loadItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

    public JMenuItem getHowToDraw() {
        return howToDraw;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public JMenuItem getStartRecord() {
        return startRecord;
    }

    public JMenuItem getStopRecord() {
        return stopRecord;
    }

    public JMenuItem getChangeMatrixSize() {
        return changeMatrixSize;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public JMenu getWindowMenu() {
        return windowMenu;
    }

    public JMenuItem getShowSteps() {
        return showSteps;
    }

    public JButton getGenerateRandomGrid() {
        return generateRandomGrid;
    }

    public ArrayList<Node> getObstaclesList() {
        return obstaclesList;
    }

}
