import java.util.*;
import java.io.*;

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

    public void drawMenu(Graphics g) {
        for (int i = 0; i < levelNames.size(); i++) {
            if (i == currentSelection) g.setColor(Color.RED);
            else g.setColor(Color.WHITE);
            g.drawString(levelNames.get(i), 100, 100 + i*30);
        }
    }
}