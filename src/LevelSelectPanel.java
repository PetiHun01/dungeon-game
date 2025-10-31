import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LevelSelectPanel extends JPanel {

    private LevelSelector selector;
    private JFrame frame;

    public LevelSelectPanel(JFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);
        setFocusable(true);

        try {
            selector = new LevelSelector("levels/approved.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> selector.moveUp();
                    case KeyEvent.VK_DOWN -> selector.moveDown();
                    case KeyEvent.VK_ENTER -> startSelectedLevel();
                    case KeyEvent.VK_ESCAPE -> Main.showMenu();
                }
                repaint();
            }
        });
    }

    private void startSelectedLevel() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.startGame();

        // Pass the selected level filename to GamePanel
        gamePanel.loadLevel(selector.getSelectedLevelFile());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Consolas", Font.BOLD, 36));
        g.setColor(Color.GREEN);
        g.drawString("Select a Level", 100, 50);

        selector.drawMenu(g);
    }
}
