import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

public class LevelSelector {
    private List<String> levelFiles = new ArrayList<>();
    private List<String> levelNames = new ArrayList<>();
    private int currentSelection = 0;

    public LevelSelector(String listFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(listFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", 2);
            levelFiles.add(parts[0].trim());
            levelNames.add(parts[1].trim());
        }
        br.close();
    }

    public void moveUp() {
        currentSelection = (currentSelection - 1 + levelFiles.size()) % levelFiles.size();
    }
    public void moveDown() {
        currentSelection = (currentSelection + 1) % levelFiles.size();
    }
    public String getSelectedLevelFile() {
        return levelFiles.get(currentSelection);
    }
    public String getSelectedLevelName() {
        return levelNames.get(currentSelection);
    }

    public int getLevelCount() {
        return levelNames.size();
    }
    public String getLevelName(int idx) {
        return levelNames.get(idx);
    }
    public int getCurrentIndex() {
        return currentSelection;
    }

    public void drawMenu(Graphics g) {
        for (int i = 0; i < levelNames.size(); i++) {
            if (i == currentSelection) g.setColor(Color.RED);
            else g.setColor(Color.WHITE);
            g.drawString(levelNames.get(i), 100, 100 + i*30);
        }
    }
}