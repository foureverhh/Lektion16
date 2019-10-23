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
    JButton[][] board;

    Board(){

        frame = new JFrame("Enjoy the game!");
        label = new JLabel("Input rows:");
        rows = new JTextField(20);
        reStart = new JButton("restart");
        controlPanel = new JPanel();

        //Print game zone
        gameZone = new JPanel();
        board = printBoard(4);
        shuffleBoard(board);
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

    public JButton[][] printBoard(int rows){
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
                //to show shuffled board
                //gameZone.add(board[i][j]);
            }
        }
        return board;
    }


    public void shuffleBoard(JButton[][] board){
        for (int i = board.length-1; i >=0 ; i--) {
            for (int j =board.length-1; j >=0 ; j--) {
                int index1 = (int) (Math.random()*i);
                int index2 = (int) (Math.random()*j);
                System.out.println(i+" "+j+" "+index1+" "+index2);
                //String temp = board[i][j].getText();
                JButton temp = board[i][j];
                board[i][j] = board[index1][index2];
                board[index1][index2] = temp;
                gameZone.add(board[i][j]);
            }
        }
    }
}
