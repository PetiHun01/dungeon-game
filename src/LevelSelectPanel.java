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
            //selector = new LevelSelectorFallback(); //ez nem csin semmit
        }

        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");

        am.put("up", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                selector.moveUp();
                repaint();
            }
        });

        am.put("down", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                selector.moveDown();
                repaint();
            }
        });

        am.put("enter", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                startSelectedLevel();
            }
        });

        am.put("escape", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                Main.showMenu();
            }
        });
    }

    private void startSelectedLevel() {
        String lvl = selector.getSelectedLevelFile();
        if (lvl == null || lvl.isBlank()) {
            System.err.println("No level selected!");
            return;
        }

        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();

        // betöltés, aztán indítás, mert másként bugos
        gamePanel.loadLevel(lvl);
        gamePanel.startGame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Consolas", Font.BOLD, 36));
        g.setColor(Color.GREEN);

        // középső felirat
        FontMetrics fm = g.getFontMetrics();
        String title = "Select a Level";
        int tx = (getWidth() - fm.stringWidth(title)) / 2;
        g.drawString(title, Math.max(20, tx), 80);

        // selector
        g.setFont(new Font("Consolas", Font.PLAIN, 24));
        for (int i = 0; i < selector.getLevelCount(); i++) {
            String name = selector.getLevelName(i);
            int x = (getWidth() - g.getFontMetrics().stringWidth(name)) / 2;
            int y = 150 + i * 36;
            if (i == selector.getCurrentIndex()) g.setColor(Color.RED);
            else g.setColor(Color.WHITE);
            g.drawString(name, x, y);
        }
    }
}
