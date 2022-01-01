package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import main.AStar;
import main.Node;
import main.main;

/**
 *
 * @author hugop
 */
public class GuiMouseListener implements MouseListener {

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == main.getGui().getStartAlgorithmButton() && main.getGui().getStartAlgorithmButton().isEnabled()) {
            main.start();
        } else if (e.getSource() == main.getGui().getGenerateRandomGrid() && main.getGui().getGenerateRandomGrid().isEnabled()) {
            main.getGui().generateObstacles();
            main.getGui().getGui().repaint();
            main.getGui().getGui().revalidate();
            main.getGui().getResetGrid().setEnabled(true);
            main.getGui().getGenerateRandomGrid().setEnabled(false);
        } else if (e.getSource() == main.getGui().getResetGrid() && main.getGui().getResetGrid().isEnabled()) {
            // Reset differents parameters to keep the validty of our code
            main.getGui().getContentPanel().removeAll();
            main.getGui().getListOfAll().clear();
            main.setAStarInstanceNumber(0);
            main.getGui().getGenerateObstacles().setEnabled(true);
            main.getGui().getEraseObstacles().setEnabled(true);
            main.getGui().getChooseStartPos().setEnabled(true);
            main.getGui().getChooseEndPos().setEnabled(true);
            main.getGui().getGenerateRandomGrid().setEnabled(true);
            main.getGui().getStartAlgorithmButton().setEnabled(true);
            main.getGui().getWindowMenu().setEnabled(true);
            main.getGui().getResetGrid().setEnabled(false);
            main.getGui().getGui().repaint();
            main.getGui().createNodes(main.getGui().getMatrixSize());
            main.getGui().getGui().revalidate();
        } else {
            // Iterate trough all the buttons
            Node clickedNode;
            for (int i = 0; i < main.getGui().getListOfAll().size(); i++) {
                clickedNode = main.getGui().getListOfAll().get(i);
                if (main.getGui().getChooseStartPos().isSelected() && e.getSource() == clickedNode.getPathEntity()) {
                    main.getSwingWorker().getAStarList().add(new AStar());
                    clickedNode.getPathEntity().setText(String.valueOf(main.getAStarInstanceNumber()));
                    main.setAStarInstanceNumber(main.getAStarInstanceNumber() + 1);
                    main.getSwingWorker().getAStarList().get(main.getAStarInstanceNumber()).setStartingNode(clickedNode);
                    main.getSwingWorker().getAStarList().get(main.getAStarInstanceNumber()).getOpenSet().add(clickedNode);
                    main.getGui().paintNode(clickedNode, Color.RED);
                    main.getGui().getChooseStartPos().setSelected(false);
                    main.getGui().getChooseEndPos().setEnabled(true);
                    if (main.getGui().getChooseStartPos().isSelected()) {
                        main.getGui().getChooseEndPos().setSelected(true);
                    }
                } else if (main.getGui().getChooseEndPos().isSelected() && e.getSource() == clickedNode.getPathEntity() && !main.getGui().getObstaclesList().contains(clickedNode)) {
                    main.getSwingWorker().getAStarList().get(main.getAStarInstanceNumber()).getListOfEndNodes().add(clickedNode);
                    main.getSwingWorker().getAStarList().get(main.getAStarInstanceNumber()).getRunAlgoList().add(false);
                    main.getGui().paintNode(clickedNode, Color.BLUE);
                }

            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Node node;
        for (int i = 0; i < main.getGui().getListOfAll().size(); i++) {
            node = main.getGui().getListOfAll().get(i);
            if (main.getGui().getGenerateObstacles().isSelected()
                    && main.getGui().isAllowDrawing()
                    && e.getSource() == node.getPathEntity()) {
                main.getGui().getObstaclesList().remove(node);
                main.getGui().paintObstacles(node, main.getGui().getWidthSelectorSlider().getValue(), Color.GRAY);
            }
            if (main.getGui().getEraseObstacles().isSelected()
                    && main.getGui().isAllowDrawing()
                    && e.getSource() == node.getPathEntity()
                    && (node == main.getSwingWorker().getAStarList().get(0).getStartingNode()) == false
                    && (node == main.getSwingWorker().getEndNode()) == false) {

                main.getGui().getObstaclesList().remove(node);
                main.getGui().paintObstacles(node, main.getGui().getWidthSelectorSlider().getValue(), Color.WHITE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
