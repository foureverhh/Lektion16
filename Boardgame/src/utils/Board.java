package utils;

import javax.swing.*;
import java.awt.*;

public class Board {
    JFrame frame;
    JLabel label;
    JTextField rows;
    JButton reStart;
    JPanel controlPanel;
    JScrollPane scrollPane;
    JPanel gameZone;

    Board(){

        frame = new JFrame("Enjoy the game!");
        label = new JLabel("Input rows:");
        rows = new JTextField(20);
        reStart = new JButton("restart");
        controlPanel = new JPanel();

        //Print game zone
        gameZone = new JPanel();
        printBoard(4);
        scrollPane = new JScrollPane(gameZone);

        //Print control zone
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(label);
        controlPanel.add(rows);
        controlPanel.add(reStart);

        frame.setLayout(new BorderLayout());
        frame.add(controlPanel,BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Board board = new Board();
    }

    public void printBoard(int rows){
        gameZone.setLayout(new GridLayout(rows,rows));
        int number = rows * rows;
        JButton[][] board = new JButton[rows][rows];
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < rows ; j++) {
                if(number > 1) {
                    board[i][j] = new JButton(("" + --number));
                }else {
                    board[i][j] = new JButton();
                }
                    board[i][j].setPreferredSize(new Dimension(100, 100));
                    gameZone.add(board[i][j]);

            }
        }
    }
}
