package app;

import java.awt.CardLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The application window: a card panel hosting the views, which the
 * AppBuilder registers one by one under their view names (as in
 * CAWithBuilder, where the builder owns the card panel). The window itself
 * knows nothing about the todo list: no view models, controllers, or
 * rendering.
 */
public final class MainFrame extends JFrame {

    private static final int WINDOW_WIDTH = 480;
    private static final int WINDOW_HEIGHT = 560;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    public MainFrame() {
        super("Todo");
        cardPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        setContentPane(cardPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    public void addView(JPanel view, String viewName) {
        cardPanel.add(view, viewName);
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}