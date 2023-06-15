import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameSettingFileRead {
    private static GameSettingFileRead single_instance = null;
    private String[] levels;

    public GameSettingFileRead() {
        try {
            levels = loadLevelsFromFile();
        } catch (IOException e) {
            System.err.println("Failed to load levels from file: " + e.getMessage());
            levels = new String[]{GameConstants.DEFAULT_GAME_MAP};
        }
    }

    private String[] loadLevelsFromFile() throws IOException {
        Path filePath = Paths.get(GameConstants.LEVELS_FILE_NAME);
        List<String> lines = Files.readAllLines(filePath);
        List<String> levelList = new ArrayList<>();

        for (String line : lines) {
            String[] levelObjects = line.split(","); // Split objects within the line by comma
            String level = String.join("", levelObjects); // Concatenate the objects into a single string
            levelList.add(level);
        }

        return levelList.toArray(new String[0]);
    }

    // Method
    // Static method to create instance of Singleton class
    public static GameSettingFileRead getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new GameSettingFileRead();
        }
        return single_instance;
    }

    public String[] getLevels() {
        return levels;
    }
}
