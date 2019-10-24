package utils;

import javax.swing.*;
import java.awt.*;

public class Board {
    private JFrame frame;
    private JLabel label;
    private JTextField rowsInput;
    private JButton newGame;
    private JPanel controlPanel;
    private JScrollPane scrollPane;
    private JPanel gameZone;
    private JButton[][] board;
    private JButton[] boardSingle;
    private int rows = 4;

    Board(){

        frame = new JFrame("Enjoy the game!");
        label = new JLabel("Input rows:");
        rowsInput = new JTextField(20);
        newGame = new JButton("New Game");
        controlPanel = new JPanel();
        //Print control zone
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(label);
        controlPanel.add(rowsInput);
        controlPanel.add(newGame);

        //Print game zone
        gameZone = new JPanel();
        gameZone.setLayout(new GridLayout(rows,rows));
        //board = printBoardByTwoDimensionalArray(4);
        //shuffleBoardTwoDimensional(board);



        boardSingle = printBoardByArray(rows);
        newGame.addActionListener(l -> {
            try {
                gameZone.revalidate();
                shuffleBoardByArray(boardSingle);

            }catch (Exception e){
                JOptionPane.showMessageDialog(null,
                        "Only integer allowed",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        scrollPane = new JScrollPane(gameZone);

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

    public JButton[] printBoardByArray(int rows){
       // gameZone.setLayout(new GridLayout(rows,rows));
        JButton[] boardSingle = new JButton[rows*rows];
        for (int i = 0; i < boardSingle.length-1 ; i++) {
            //if(i!= boardSingle.length-1)
                boardSingle[i] = new JButton(""+(i+1));
       /*     else {
                boardSingle[i] = new JButton();
                boardSingle[i].setOpaque(true);
            }*/
            gameZone.add(boardSingle[i]);
            //System.out.println(i);
        }
        return boardSingle;
    }

    public void shuffleBoardByArray(JButton[] boardSingle){
        //System.out.println();

        while (true) {
            for (int i = boardSingle.length - 1 - 1; i >= 0; i--) {
                int index = (int) (Math.random() * i);
                JButton temp = boardSingle[i];
                boardSingle[i] = boardSingle[index];
                boardSingle[index] = temp;
                //System.out.println(i);
                gameZone.add(boardSingle[i]);
            }
            //Check the sum of inversion is even or odd
            int inversion = inversion(boardSingle)%2;
            //polarity in two dimensional system
            int polarity = ((rows-1)+(rows-1))%2;
            if(inversion==polarity){
                break;
            }
        }

        System.out.println(boardSingle.length);
    }

    public int inversion (JButton[] boardSingle){
        int inversion =0;
        //The last element of boardSingle is null so end with <length-1,not <=length-1
        for (int i = 0; i < boardSingle.length-1 ; i++) {
            //should compareTo ""+(i+1),as text on buttons are (i+1)
            if(boardSingle[i].getText().compareTo(""+(i+1))>0){
                inversion++;
            }
        }
        return inversion;
    }
    public JButton[][] printBoardByTwoDimensionalArray(int rows){

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


    public void shuffleBoardTwoDimensional(JButton[][] board){
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
