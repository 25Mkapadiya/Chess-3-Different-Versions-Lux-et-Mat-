import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scene extends JPanel {
    private ChessBoardGUI chessBoardGUI;
    private NormalChess game;

    protected Scene menuScene;
    
    protected Scene gameScene1;
    protected Scene gameScene2;
    protected Scene gameScene3;
    protected Scene gameScene4;

    private JButton nextButton1;
    private JButton nextButton2;
    private JButton nextButton3;
    private JButton nextButton4;

    private JButton backButton;
    private Color myColor;
    private String sceneName;

    // just adding the scene and its name
    public Scene(ChessBoardGUI chessBoardGUI, String sceneName) {
        myColor = new Color(255, 255, 255);
        this.chessBoardGUI = chessBoardGUI;
        this.sceneName = sceneName;

        JLabel text = new JLabel(sceneName);

        setLayout(new FlowLayout());
        add(text);

        JLabel title = new JLabel("Lux et Mat");
        title.setFont(new Font("Georgia", Font.BOLD, 100));
        title.setForeground(new Color(3, 52, 106));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.add(title);
        add(top, BorderLayout.NORTH);

        JLabel subtitle = new JLabel("by Allen Huang, Milind Kapadiya, and Mason Mifflin");
        subtitle.setFont(new Font("Georgia", Font.BOLD, 18));
        subtitle.setForeground(new Color(153, 0, 0));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(subtitle);
        add(bottom, BorderLayout.NORTH);
    }

    public void setNextScene(Scene gameScene1, Scene gameScene2, Scene gameScene3, Scene gameScene4) {
        this.gameScene1 = gameScene1;
        this.gameScene2 = gameScene2;
        this.gameScene3 = gameScene3;
        this.gameScene4 = gameScene4;

        nextButton1 = new JButton("Play Normal Chess");
        nextButton1.addActionListener(e -> {
            if (gameScene1 != null) {
                game = new NormalChess();
                gameScene1.removeAll();
                gameScene1.add(game.getBoardPanel());
                gameScene1.repaint();

                chessBoardGUI.switchScene(gameScene1);
            }
        });
        add(nextButton1);

        nextButton2 = new JButton("Play Bomb Chess");
        nextButton2.addActionListener(e -> {
            if (gameScene2 != null) {
                game = new BombChess();
                gameScene2.removeAll();
                gameScene2.add(game.getBoardPanel());
                gameScene2.repaint();

                chessBoardGUI.switchScene(gameScene2);
            }
        });
        add(nextButton2);

        nextButton3 = new JButton("Play Full Capture Chess");
        nextButton3.addActionListener(e -> {
            if (gameScene3 != null) {
                game = new CaptureChess();
                gameScene3.removeAll();
                gameScene3.add(game.getBoardPanel());
                gameScene3.repaint();

                chessBoardGUI.switchScene(gameScene3);
            }
        });
        add(nextButton3);

        nextButton4 = new JButton("Play Bulldog Chess");
        nextButton4.addActionListener(e -> {
            if (gameScene4 != null) {
                game = new BulldogChess();
                gameScene4.removeAll();
                gameScene4.add(game.getBoardPanel());
                gameScene4.repaint();
                
                chessBoardGUI.switchScene(gameScene4);
            }
        });
        add(nextButton4);
    }

    // This doesn't really do anything since there is no Back button
    // but don't delete yet since need to update other code in order to do so
    public void setPreviousScene(Scene menuScene) {
        this.menuScene = menuScene;
        backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            if (menuScene != null) {
                chessBoardGUI.switchScene(menuScene);
                
                //chessBoardGUI.remove(game.getBoardPanel());
            }
        });
        add(backButton);
    }


    public String getSceneName() {
        return sceneName;
    }
}
