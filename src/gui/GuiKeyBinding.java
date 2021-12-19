package gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import main.main;

/**
 *
 * @author hugop
 */
public class GuiKeyBinding {
    
    private final Action stopDrawingAction;
    private final Action allowDrawingAction;

    public GuiKeyBinding() {
        stopDrawingAction = new stopDrawingAction();
        allowDrawingAction = new allowDrawingAction();
    }

    public class stopDrawingAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            main.getGui().setAllowDrawing(false);
            System.out.println("Drawing disabled");
        }

    }

    public class allowDrawingAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            main.getGui().setAllowDrawing(true);
            System.out.println("Drawing enabled");
        }

    }

    public Action getStopDrawingAction() {
        return stopDrawingAction;
    }

    public Action getAllowDrawingAction() {
        return allowDrawingAction;
    }
    
}
