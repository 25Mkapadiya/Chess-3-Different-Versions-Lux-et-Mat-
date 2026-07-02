import javax.swing.*;
import java.awt.*;

public class ChessBoardFrame extends JFrame {
    final static int WINDOW_SIZE = 600;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Constructor
    public ChessBoardFrame() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE + 200, WINDOW_SIZE + 200);
        setLocationRelativeTo(null);

        // Layout
        cardLayout = new CardLayout();

        // Set up Panel
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
        //menuPanel.setLayout(new FlowLayout());
        //add(menuPanel, BorderLayout.CENTER);

        // Initialize Scenes
        Scene menuScene = new Scene(this, "Menu");
        
        Scene gameScene1 = new Scene(this, "Game1");
        Scene gameScene2 = new Scene(this, "Game2");
        Scene gameScene3 = new Scene(this, "Game3");
        Scene gameScene4 = new Scene(this, "Game4");

        // Attach each scene to one another
        menuScene.setNextScene(gameScene1, gameScene2, gameScene3, gameScene4);
        gameScene1.setPreviousScene(menuScene);
        gameScene2.setPreviousScene(menuScene);
        gameScene3.setPreviousScene(menuScene);
        gameScene4.setPreviousScene(menuScene);

        // Add scenes to the panel
        mainPanel.add(menuScene, menuScene.getSceneName());
        mainPanel.add(gameScene1, gameScene1.getSceneName());
        mainPanel.add(gameScene2, gameScene2.getSceneName());
        mainPanel.add(gameScene3, gameScene3.getSceneName());
        mainPanel.add(gameScene4, gameScene4.getSceneName());

        switchScene(menuScene);

    }

    public void switchScene(Scene scene) {
        cardLayout.show(mainPanel, scene.getSceneName());
    }

    public static void main(String[] args) {
        ChessBoardFrame frame = new ChessBoardFrame();
        frame.setVisible(true);
    }
}
