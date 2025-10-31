import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Dungeon Explorer?");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setPreferredSize(new Dimension(1280, 720));

            showMenu();

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void showMenu() {
        frame.getContentPane().removeAll();
        frame.add(new MenuPanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    public static void startGame() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.startGame();
    }
}
