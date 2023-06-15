import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BeanstalkGame extends JFrame implements KeyListener {
    private char[][] maze;
    private final JLabel[][] gridLabels;
    private final String[] levels;
    private int currentLevelIndex;
    private final int totalLevels;

    private BeanstalkGame() {
        super("Maze Game");
        levels = new String[]{
                "####################!#------------------#!#--------0---------#!#------------------#!#------------------#!#------------------#!#--------2---------#!#-------375--------#!#--------4---------#!#------------------#!#------------------#!#------------------#!#------------------#!####################",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxx####xxxxxxxxxx!xxxxx##--#xxxxxxxxxx!xxxxx#---#xxxxxxxxxx!xxx###--5##xxxxxxxxx!xxx#--3-4-#xxxxxxxxx!xxx#-#-##-#xxx#####x!xxx#-#-##-#####---#x!xxx#--2---------7-#x!xxx###-###-#0##---#x!xxxxx#-----########x!xxxxx#######xxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "#####xxxxxxxxxx#####!#0--############---#!#-------3----------#!##--############--##!x#-#xxxxxxxxxxxx#-#x!x#2#xxxxxxxxxxxx#-#x!x#-#xxxxxxxxxxxx#5#x!x#-#xxxxxxxxxxxx#-#x!x#-#xxxxxxxxxxxx#-#x!x#-#xxxxxxxxxxxx#-#x!##--############--##!#--------4---------#!#---############-7-#!#####xxxxxxxxxx#####",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxx###xxx###xxxxx!xxxxx#---#x#---#xxxx!xxxxx#-4--#--3-#xxxx!xxxxxx#-5-0-2-#xxxxx!xxxxxxx#-----#xxxxxx!xxxxxxxx#---#xxxxxxx!xxxxxxxxx#7#xxxxxxxx!xxxxxxxxxx#xxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxx###xxxxxx##xxxxx!xxx#---#xxxx#--#xxxx!xxx#----####---#xxxx!xxx#-04-----5-7#xxxx!xxx#-3--#####@@#xxxx!xxx#-2-#xxxx#@@#xxxx!xxx#---#xxxx#--#xxxx!xxxx###xxxxxx##xxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxx######xxxxxxx!xxxxxxx#0---#xxxxxxx!xxxxxxx##2-##xxxxxxx!xxxxxxx#--3-#xxxxxxx!xxxxxxx#-4--#xxxxxxx!xxxxxxx#--5-#xxxxxxx!xxxxxxx#---7#xxxxxxx!xxxxxxx######xxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxx#xxxxxxxx#xxxxx!xxxxxxx######xxxxxxx!xxxxxx##--0-##xxxxxx!xxxxxx#--3-2-#xxxxxx!xxxxxx#-3734-#xxxxxx!xxxxxx#--35--#xxxxxx!xxxxxx##----##xxxxxx!xxxxxxx######xxxxxxx!xxxxx#xxxxxxxx#xxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxx###########xxxxx!xxxx#72-------#xxxxx!xxxx#34-#555--#xxxxx!xxxx#--#------#xxxxx!xxxx#-5-55##--#xxxxx!xxxx#--#-----5#xxxxx!xxxx#5-5555555#xxxxx!xxxx#5-5--#-5-#xxxxx!xxxx#-#5-5--55#xxxxx!xxxx#-5---5-50#xxxxx!xxxx###########xxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "####################!#------#-----------#!#-##--###--##-##-###!#0#--#---#--#--#--##!###-4---5-2-#-3----#!#-#--#---#--#--#--##!#-##--#-#--##-##-###!#---#-----#----#---#!#----#-#---##-----##!#--#-----#-###--#--#!#-----------------7#!####################!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxx####xxxxxxxxx!xxxxxxx#--#xxxxxxxxx!xxxxxx##--###xxxxxxx!xxxxxx#---2-#xxxxxxx!xxxxxx#--#--##xxxxxx!xxxxxx#-3-4--#xxxxxx!xxxxxx#-5----#xxxxxx!xxxxxx###7####xxxxxx!xxxxxxxx#0#xxxxxxxxx!xxxxxxxx###xxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xx################xx!xx#--------------#xx!xx#-###-###-####-#xx!xx#----@--435----#xx!xx#####-###-######xx!xx#-----#-#-#xxxxxxx!xx#-###-###0#####xxx!xx#----2-------7#xxx!xx###############xxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxx###xxxxxx!xxxxxxxxxx##0#xxxxxx!xxxxx######-5#xxxxxx!xxxxx#-2-----#xxxxxx!xxxxx#-#-@#@-#xxxxxx!xxxxx#-3-###-##xxxxx!xxxxx#-4-@#@--#xxxxx!xxxxx#------7-#xxxxx!xxxxx##########xxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxx#####xxxxxxxxxx!xxxxx#7--######xxxxx!xxxxx#--------#xxxxx!xxxxx#--####--#xxxxx!xxxxx####--#-##xxxxx!xxxxx#---2-#-#xxxxxx!xxxxx#-3-405-#xxxxxx!xxxxx#---#---#xxxxxx!xxxxx#########xxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxx########xxxxxx!xxxxxx#--#---#xxxxxx!xxxxxx#-3245-#xxxxxx!xxxxxx#--#-#-#xxxxxx!xxxxxx#@-#0#-#xxxxxx!xxxxxx#-----7#xxxxxx!xxxxxx#--#-@-#xxxxxx!xxxxxx########xxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "####################!#0@-@----@@----@-@-#!#@-@---@-@---@@----#!#-@@@@@@@@@@@@-@@@@#!#@---------@----@--#!#-@@@@@@@@@-@@@@@4@#!#@---------@@---@--#!#--@@@@@@@@-@@@@@--#!#--@--@-@@-@-@-@@--#!#-@--@-@@--@@@--3-3#!#-@-@-@---@@-@-333-#!#-@--@-@-@-@-@--3--#!#-@@---@-----3-5-27#!####################",
                "-33333333-3-3-3#####!-3--3--3-3-3-3------!-33333333-3-3-3#----!-3--333333-3-3--3457!-3333--3--3-3-3#----!-3333--3-3-3-3------!-3--33333-3-3-3#####!03--3--3-3-3-3-3-3--!-33333333-3-3-3-2---!-3--3--3-3-3---3--3-!-33333333-3-3-3--3--!-3--3--3-3-3-3--3---!-33333333-3-3--3-3--!-3--3--3-3-3--3-3---",
                "xx#################x!xx#-73------#-----#x!xx######-----@@@@-#x!xxxxxxx#--##--5---#x!xxxxxxx#--####@3-@#x!xxxxxxx#######--@-#x!xxxxxxx##0-###-@-@#x!xxxxxxx#----##---@#x!xxxxxxx#--2-##4---#x!xxxxxx##-#####-@@##x!xxxxxx#--#---#----#x!xxxxxx#-----------#x!xxxxxx##-#######-##x!xxxxxxx#---------#xx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxx####xxxxxxxxx!xxxxxx##-0#####xxxxx!xxxxxx#-5-----#xxxxx!xxxxxx#-23457-#xxxxx!xxxxxx#-------#xxxxx!xxxxxx#########xxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxx#####xxxxxxxxxxx!xxxx#---########xxxx!xxxx#-#--------#xxxx!xxxx#-#3######-#xxxx!xxxx#-#--------#xxxx!xxxx#-#-###-#2##xxxx!xxxx#-#--0--#-#xxxxx!xxx##-#-###-#-#xxxxx!xxx#--4-----#-#xxxxx!xxx#-######5#-#xxxxx!xxx#-------7#-#xxxxx!xxx########---#xxxxx!xxxxxxxxxx#####xxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxx######xxxxxx!xxxxxxx##-27-#xxxxxx!xxxxxxx#-4-#-#xxxxxx!xxxxxxx#--5--#xxxxxx!xxxxxxx#--#3##xxxxxx!xxxxxxx##-0-#xxxxxxx!xxxxxxxx#####xxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxx###xx!xxxxxxxxxxxxxx##-#xx!xxxxxxxxxxxxxx#--#xx!xxxxxxxxxxxxxx#-@#xx!xxxxxxxxxx#####4-#xx!xxxxxxxxxx#--##--#xx!xxxxxxxxxx#-05--@#xx!xxxxxxxxxx#-@#-#7#xx!xxxxxx######-----#xx!xxxxxx#--###-#--##xx!xxxxx##-2###@-#-#xxx!xxxxx#--------3-#xxx!xxxxx########---#xxx!xxxxxxxxxxxx#####xxx",
                "xxx#####xxxxxxxxxxxx!x###---#xxxxxxxxxxxx!x#--5-0#####xxxxxxxx!x#----###--#xxxxxxxx!x#@#4@-----#xxxxxxxx!x#--3-###27#xxxxxxxx!x#----#x#--#xxxxxxxx!x######x####xxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xx##########xxxxxxxx!xx#---##7-##xxxxxxxx!xx#-5@----##xxxxxxxx!xx#--4@#---#xxxxxxxx!xx#-23-#---#xxxxxxxx!xx#--#-#####xxxxxxxx!xx##-0-#xxxxxxxxxxxx!xxx#####xxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxx####xx#####xxxxx!xxxx#--####---#xxxxx!xxxx#--0@-----#xxxxx!xxxx#--#2###5##xxxxx!xxxx##-7@-4--#xxxxxx!xxxx##-##-#-##xxxxxx!xxxx#-----3--#xxxxxx!xxxx#---##---#xxxxxx!xxxx##########xxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
                "x##xxx#xxx#x###xx#xx!x#2#x#4#xx#xx#xxx#xx!x#3#x#5#xx#xx#xxxxxx!x##xxx#xxx#xx#xxx#xx!xxxxxxxxxxxxxxxxxxxx!xx#####xxxxxxxxxxxxx!xx#---#######xxxxxxx!xx#-2-#---@-##xxxxxx!xx#-#@#---@7-###xxxx!xx#--@-34-@-@--##xxx!xx#---#--###-@--#xxx!xx#####--####-50#xxx!xxxxxx####xx##--#xxx!xxxxxxxxxxxxx####xxx",
                "xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxx###xxxxxx!xxxxxxxxxxx#-####xxx!xxxxxxxx####@@--##xx!xxxxxx###--5--#--#xx!xxxxxx#-2--4-3-@-#xx!xxxxx##-#@#@#@#--#xx!xxxxx#----@-7-@--#xx!xxxxx######0######xx!xxxxxxxxxx###xxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx",
        };
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
