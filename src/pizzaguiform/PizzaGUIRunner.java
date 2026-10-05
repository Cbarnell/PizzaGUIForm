package pizzaguiform;

import javax.swing.SwingUtilities;

/**
 * Runner class to launch the PizzaGUIForm application.
 */
public class PizzaGUIRunner {

    /**
     * Main entry point for the application.
     *
     */
    static void main() {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}