import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BeanstalkGame extends JFrame implements KeyListener {
    private char[][] maze;
    private final JLabel[][] gridLabels;
    private String[] levels;
    private int currentLevelIndex;
    private final int totalLevels;

    private BeanstalkGame() {
        super("Maze Game");

        GameSettingFileRead gameSettingFileRead = GameSettingFileRead.getInstance();
        levels = gameSettingFileRead.getLevels();

        currentLevelIndex = 0;
        totalLevels = levels.length;

        String currentLevel = levels[currentLevelIndex];
        String[] rows = currentLevel.split("!");
        maze = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            maze[i] = rows[i].toCharArray();
        }

        gridLabels = new JLabel[maze.length][maze[0].length];

        JPanel gridPanel = new JPanel(new GridLayout(maze.length, maze[0].length));
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                JLabel label = new JLabel();
                setLabelIcon(label, maze[i][j]);
                gridLabels[i][j] = label;
                gridPanel.add(label);
            }
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> previousLevel());

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> nextLevel());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int gridSize = 40;
        setSize(maze[0].length * gridSize, maze.length * gridSize);
        setVisible(true);
        setFocusable(true);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setResizable(false);
    }


    private void setLabelIcon(JLabel label, char object) {
        String imagePath = switch (object) {
            case '#' -> "resources/wall.png";
            case '-' -> "resources/grass.png";
            case '@' -> "resources/rock.png";
            case '0' -> "resources/jackfront.png";
            case '2' -> "resources/shovel.png";
            case '3' -> "resources/bean.png";
            case '4' -> "resources/fert.png";
            case '5' -> "resources/water.png";
            case '7' -> "resources/x.png";
            case 'x' -> "resources/empty.png";
            default -> "";
        };
        label.setIcon(new ImageIcon(imagePath));
    }

    private void moveObject(String direction) {
        int objectRow = -1;
        int objectCol = -1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '0') {
                    objectRow = i;
                    objectCol = j;
                    break;
                }
            }
        }

        int newRow = objectRow;
        int newCol = objectCol;
        int pushRow = -1;
        int pushCol = -1;
        switch (direction) {
            case "up" -> {
                newRow--;
                pushRow = newRow - 1;
                pushCol = newCol;
            }
            case "down" -> {
                newRow++;
                pushRow = newRow + 1;
                pushCol = newCol;
            }
            case "left" -> {
                newCol--;
                pushRow = newRow;
                pushCol = newCol - 1;
            }
            case "right" -> {
                newCol++;
                pushRow = newRow;
                pushCol = newCol + 1;
            }
        }

        if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length
                && maze[newRow][newCol] != '#') {
            if (maze[newRow][newCol] == '-') {
                // Move object to new position
                maze[objectRow][objectCol] = '-';
                maze[newRow][newCol] = '0';
                updateGrid();
            } else if (isPushable(maze[newRow][newCol]) && pushRow >= 0 && pushRow < maze.length && pushCol >= 0 && pushCol < maze[0].length
                    && maze[pushRow][pushCol] == '-') {
                // Move object and pushed object to new positions
                maze[pushRow][pushCol] = maze[newRow][newCol];
                maze[objectRow][objectCol] = '-';
                maze[newRow][newCol] = '0';
                updateGrid();
            }
        }
    }

    private boolean isPushable(char object) {
        return object == '2' || object == '3' || object == '4' || object == '5' || object == '@' || object == 'x' || object == '-';
    }

    private void updateGrid() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                setLabelIcon(gridLabels[i][j], maze[i][j]);
            }
        }
    }

    private void nextLevel() {
        currentLevelIndex = (currentLevelIndex + 1) % totalLevels;
        loadLevel(currentLevelIndex);
        requestFocus(); // Set focus back to the frame
    }

    private void previousLevel() {
        currentLevelIndex = (currentLevelIndex - 1 + totalLevels) % totalLevels;
        loadLevel(currentLevelIndex);
        requestFocus(); // Set focus back to the frame
    }

    private void loadLevel(int levelIndex) {
        String level = levels[levelIndex];
        String[] rows = level.split("!");
        maze = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            maze[i] = rows[i].toCharArray();
        }

        //resetObjectPosition();
        updateGrid();
    }

    private void resetObjectPosition() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '0') {
                    maze[i][j] = '-';
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BeanstalkGame::new);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            moveObject("up");
        } else if (keyCode == KeyEvent.VK_DOWN) {
            moveObject("down");
        } else if (keyCode == KeyEvent.VK_LEFT) {
            moveObject("left");
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            moveObject("right");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
