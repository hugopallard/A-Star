package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import main.main;

/**
 *
 * @author hugop
 */
public class GuiMenuBarListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == main.getGui().getSaveItem()) {

        }
        if (e.getSource() == main.getGui().getLoadItem()) {

        }
        if (e.getSource() == main.getGui().getExitItem()) {
            main.getGui().getGui().setVisible(false);
            main.getGui().getGui().dispose();
            System.exit(0);

        }
        if (e.getSource() == main.getGui().getStartRecord()) {

        }
        if (e.getSource() == main.getGui().getStopRecord()) {

        }
        if (e.getSource() == main.getGui().getChangeMatrixSize()) {
            try {
                main.getGui().getStartAlgorithmButton().setEnabled(false);
                main.getGui().getGenerateRandomGrid().setEnabled(false);
                int newGridSize = Integer.parseInt(JOptionPane.showInputDialog(main.getGui().getGui(), "Enter the new grid size:", main.getGui().getMatrixSize()));
                main.getGui().setMatrixSize(newGridSize);
                
                main.getGui().getContentPanel().removeAll();
                main.getGui().getGui().repaint();
                main.getGui().createNodes(newGridSize);
                main.getGui().getGui().revalidate();
                main.getGui().getStartAlgorithmButton().setEnabled(true);
                main.getGui().getGenerateRandomGrid().setEnabled(true);
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
