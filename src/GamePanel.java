import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {

    private Thread gameThread;
    private boolean running = false;
    private boolean paused = false;

    private Player player;

    public static final int TILE_SIZE = 16;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyHandler());

        player = new Player(100, 100);
    }
    public void loadLevel(String levelPath) {
        System.out.println("Loading level from: " + levelPath);

        // Example: clear any previous entities, tiles, etc.
        // entityManager.clear();
        // tileManager.loadFromFile(levelPath);

        // You can put your real map loading logic here.
        // For now, this just confirms the level file is passed correctly.
    }

    public void startGame() {
        running = true;
        requestFocusInWindow();
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {
            update();
            repaint();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if(!paused){
            player.update();
        }
    }
    public void togglePause(){
        paused = !paused;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);

        if (paused) {
            Graphics2D g2 = (Graphics2D) g; //igen.
            g2.setColor(new Color(0, 0, 0, 150)); // felig atlatszo?
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Consolas", Font.BOLD, 36));
            g2.drawString("PAUSED", WIDTH / 2 - 80, HEIGHT / 2 - 50);

            g2.setFont(new Font("Consolas", Font.PLAIN, 20));
            g2.drawString("Press ESC to Resume", WIDTH / 2 - 110, HEIGHT / 2);
            g2.drawString("Press M for Main Menu", WIDTH / 2 - 120, HEIGHT / 2 + 40);
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                togglePause();
            } else if (e.getKeyCode() == KeyEvent.VK_M && paused) {
                running = false;
                Main.showMenu();
            } else {
                player.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}
