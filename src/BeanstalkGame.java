import javax.swing.*;
import java.awt.*;


public class BeanstalkGame extends JFrame {
    public static void main(String[] args) {
        // Create an instance of the BeanGame class
        BeanstalkGame game = new BeanstalkGame();

        String maze = "xxxxxxxxxxxxxxxxxxxx!xxxxxx####xxxxxxxxxx!xxxxx##--#xxxxxxxxxx!xxxxx#---#xxxxxxxxxx!xxx###--5##xxxxxxxxx!xxx#--3-4-#xxxxxxxxx!xxx#-#-##-#xxx#####x!xxx#-#-##-#####---#x!xxx#--2---------7-#x!xxx###-###-#0##---#x!xxxxx#-----########x!xxxxx#######xxxxxxxx!xxxxxxxxxxxxxxxxxxxx!xxxxxxxxxxxxxxxxxxxx";

        // Split the maze string into rows
        String[] rows = maze.split("!");

        JPanel gridPanel = new JPanel(new GridLayout(rows.length, rows[0].length()));

        // Convert each row into a char array and add it to the grid array
        char[][] grid = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            grid[i] = rows[i].toCharArray();
        }

        // Add a JLabel for each character in the grid array
        for (char[] chars : grid) {
            for (char aChar : chars) {
                System.out.print(aChar);
                JLabel label = new JLabel();
                switch (aChar) {
                    case '#' -> label.setIcon(new ImageIcon("resources/wall.png"));
                    case '0' -> label.setIcon(new ImageIcon("resources/jackfront.png"));
                    case '2' -> label.setIcon(new ImageIcon("resources/shovel.png"));
                    case '3' -> label.setIcon(new ImageIcon("resources/bean.png"));
                    case '4' -> label.setIcon(new ImageIcon("resources/fert.png"));
                    case '5' -> label.setIcon(new ImageIcon("resources/water.png"));
                    case '7' -> label.setIcon(new ImageIcon("resources/x.png"));
                    case '8' -> label.setIcon(new ImageIcon("resources/hole.png"));
                    case '9' -> label.setIcon(new ImageIcon("resources/planted.png"));
/*
                    case "10" -> label.setIcon(new ImageIcon("fertilized.png"));
                    case "11" -> label.setIcon(new ImageIcon("grass.png"));
*/
                    case 'x' -> label.setIcon(new ImageIcon("resources/empty.png"));
                    case '-' -> label.setIcon(new ImageIcon("resources/grass.png"));
                    case '@' -> label.setIcon(new ImageIcon("resources/rock.png"));
                    default -> {
                    }
                }
                gridPanel.add(label);
            }
            System.out.println();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        game.setContentPane(mainPanel); // Call setContentPane on the instance
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Call setDefaultCloseOperation on the instance
        int gridSize = 40; // Define a grid size value
        game.setSize(20 * gridSize, 14 * gridSize); // Call setSize on the instance
        game.setLocationRelativeTo(null);
        game.setVisible(true); // Call setVisible on the instance
    }
}
