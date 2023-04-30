import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;


public class BeanstalkGame extends JFrame implements KeyListener {
    private final char[][] maze;
    private int gridSize = 40; // pixel size of each grid cell
    private final JLabel[][] gridLabels;

    private BeanstalkGame() {
        super("Maze Game");

        String level = "xxxxxxxxxxxxxxxxxxxx!xxxxxx####xxxxxxxxxx!xxxxx##--#xxxxxxxxxx!xxxxx#---#xxxxxxxxxx!xxx###--5##xxxxxxxxx!xxx#--3-4-#xxxxxxxxx!xxx#-#-##-#xxx#####x!xxx#-#-##-#####---#x!xxx#--2---------7-#x!xxx###-###-#0##---#x!xxxxx#-----########x!xxxxx#######xxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx";

        // Split the maze string into rows
        String[] rows = level.split("!");

        // Convert each row into a char array and add it to the grid array
        maze = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            maze[i] = rows[i].toCharArray();
        }

        gridLabels = new JLabel[maze.length][maze[0].length];

        JPanel gridPanel = new JPanel(new GridLayout(maze.length, maze[0].length));
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                JLabel label = new JLabel();
                if (maze[i][j] == '#') {
                    label.setIcon(new ImageIcon("resources/wall.png"));
                } else if (maze[i][j] == '-') {
                    label.setIcon(new ImageIcon("resources/grass.png"));
                } else if (maze[i][j] == '0') {
                    label.setIcon(new ImageIcon("resources/jackfront.png"));
                } else if (maze[i][j] == '2') {
                    label.setIcon(new ImageIcon("resources/shovel.png"));
                } else if (maze[i][j] == '3') {
                    label.setIcon(new ImageIcon("resources/bean.png"));
                } else if (maze[i][j] == '4') {
                    label.setIcon(new ImageIcon("resources/fert.png"));
                } else if (maze[i][j] == '5') {
                    label.setIcon(new ImageIcon("resources/water.png"));
                } else if (maze[i][j] == '7') {
                    label.setIcon(new ImageIcon("resources/x.png"));
                }
                gridLabels[i][j] = label;
                gridPanel.add(label);
            }
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(maze[0].length * gridSize, maze.length * gridSize);
        setVisible(true);
        addKeyListener(this);
    }

    private void moveObject(String direction) {
        // Determine current position of object
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

        // Determine new position based on direction
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
        // Check if new position is valid and move object if it is
        if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length
                && maze[newRow][newCol] != '#') {
            if (maze[newRow][newCol] == '-') {
                // Move object to new position
                maze[objectRow][objectCol] = '-';
                maze[newRow][newCol] = '0';
                updateGrid();
            } else if (maze[newRow][newCol] == '0' && pushRow >= 0 && pushRow < maze.length && pushCol >= 0 && pushCol < maze[0].length
                    && maze[pushRow][pushCol] == '-') {
                // Move object and pushed object to new positions
                maze[pushRow][pushCol] = '0';
                maze[objectRow][objectCol] = '-';
                maze[newRow][newCol] = '-';
                updateGrid();
            }
        }
    }


    private void updateGrid() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '#') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/wall.png"));
                } else if (maze[i][j] == '-') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/grass.png"));
                } else if (maze[i][j] == '0') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/jackfront.png"));
                } else if (maze[i][j] == '2') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/shovel.png"));
                } else if (maze[i][j] == '3') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/bean.png"));
                } else if (maze[i][j] == '4') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/fert.png"));
                } else if (maze[i][j] == '5') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/water.png"));
                } else if (maze[i][j] == '7') {
                    gridLabels[i][j].setIcon(new ImageIcon("resources/x.png"));
                }
            }
        }
    }

    public static void main(String[] args) {
        new BeanstalkGame();
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

