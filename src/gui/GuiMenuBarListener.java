package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import main.main;

public class GuiMenuBarListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == main.getGui().getExitItem()) {
            main.getGui().getGui().setVisible(false);
            main.getGui().getGui().dispose();
            System.exit(0);
        }
        if (e.getSource() == main.getGui().getShowSteps()) {
            int reply = JOptionPane.showConfirmDialog(null, "Show the algorithms steps ?", "Question", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                main.getSwingWorker().setShowAlgorithmSteps(true);
            } else {
                main.getSwingWorker().setShowAlgorithmSteps(false);
            }
        }
        if (e.getSource() == main.getGui().getChangeMatrixSize()) {
            try {
                main.getGui().getStartAlgorithmButton().setEnabled(false);
                int newGridSize = Integer.parseInt(JOptionPane.showInputDialog(main.getGui().getGui(), "Enter the new grid size:", main.getGui().getMatrixSize()));
                main.getGui().setMatrixSize(newGridSize);
                main.getGui().getContentPanel().removeAll();
                main.getGui().createNodes(newGridSize);
                main.getGui().getGui().revalidate();
                main.getGui().getGui().repaint();
                main.getGui().getStartAlgorithmButton().setEnabled(true);
            } catch (NumberFormatException n) {
                System.out.println("Entrez un entier");
            }

        }
        if (e.getSource() == main.getGui().getHowToDraw()) {
            JOptionPane.showMessageDialog(main.getGui().getGui(), "To stop drawing an obstacle, click on the grid, then press the s key.\n "
                    + "To start drawing again, click on the grid, then press the d key", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
