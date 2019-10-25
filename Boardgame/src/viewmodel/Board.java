package viewmodel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board  implements ActionListener {
    private JFrame frame;
    private JLabel label;
    private JTextField rowsInput;
    private JButton newGame;
    private JButton reset;
    private JPanel controlPanel;
    private JScrollPane scrollPane;
    private JPanel gameZone;
    private JButton[][] buttons;
    private JButton[] boardSingle;
    private int rows = 4;

    public Board() {

        frame = new JFrame("Enjoy the game!");
        label = new JLabel("Input rows:");
        rowsInput = new JTextField(10);
        newGame = new JButton("New Game");
        reset = new JButton("Reset");
        controlPanel = new JPanel();

        //Add control zone
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(label);
        controlPanel.add(rowsInput);
        controlPanel.add(reset);
        controlPanel.add(newGame);

        //Add game zone
        gameZone = new JPanel();

        //Print all ordered buttons
        boardSingle = printBoardByArray(rows);

        reset.addActionListener(l -> {
                rows = Integer.parseInt(rowsInput.getText());
                //Remove all old buttons
                gameZone.removeAll();
                //Reprint all new buttons
                boardSingle = printBoardByArray(rows);
                gameZone.revalidate();

                System.out.println("rows:"+rows);
        });

        newGame.addActionListener(l -> {
                shuffleBoardByArray(boardSingle);
                //Store JButton array to JButton[][]
                storeInTwoDimensionalArray(boardSingle);
            gameZone.revalidate();
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
        gameZone.setLayout(new GridLayout(rows,rows));
        boardSingle = new JButton[rows*rows];
        System.out.println(boardSingle.length);
        //To make board repaint for reset
        gameZone.repaint();
        for (int i = 0; i < boardSingle.length-1 ; i++) {
            //if(i!= boardSingle.length-1)
                boardSingle[i] = new JButton(""+(i+1));
                //boardSingle[i].addActionListener(this);
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

        //For gameZone to refresh
        while (true) {
            //Make random order
            gameZone.revalidate();
            for (int i = boardSingle.length - 1 - 1; i >= 0; i--) {
                int index = (int) (Math.random() * i);
                JButton temp = boardSingle[i];
                boardSingle[i] = boardSingle[index];
                boardSingle[index] = temp;
                //System.out.println(i);
            }
            //Check the sum of inversion is even or odd
            int inversion = inversion(boardSingle)%2;
            //polarity in two dimensional system
            int polarity = ((rows-1)+(rows-1))%2;
            if(inversion==polarity){
                System.out.println("Get array[] in shuffle:");
                for (int i = 0; i < boardSingle.length-1; i++) {
                    System.out.print(boardSingle[i].getText()+" ");
                    gameZone.add(boardSingle[i]);
                }
                System.out.println();
                break;
            }
        }
        //System.out.println(boardSingle.length);
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

    public JButton[] getBoardSingle() {
        return boardSingle;
    }

    public int getRows() {
        return rows;
    }

    public JPanel getGameZone() {
        return gameZone;
    }

    public void storeInTwoDimensionalArray(JButton[] buttonArray){
        for(JButton button : buttonArray){
            if(button!=null)
                System.out.print(button.getText()+" ");
        }
        System.out.println();
        buttons = new JButton[rows][rows];
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j < rows; j++) {
                buttons[i][j] = buttonArray[rows*i+j];
                if(i!=(rows-1)||j!=(rows-1)) {
                    System.out.print("[" + i + "][" + j + "] " + buttons[i][j].getText()+" ");
                    buttons[i][j].addActionListener(this);
                }
            }
        }
        System.out.println();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i <buttons.length ; i++) {
            for (int j = 0; j <buttons.length ; j++) {
                if(e.getSource()==buttons[i][j]){
                    System.out.print("Listener: [" + i + "][" + j + "] " + buttons[i][j].getText()+" ");

                }
                if(i+1<=buttons.length-1 && j+1<=buttons.length-1){
                    if(buttons[i+1][j] == null){
                        JButton temp = buttons[i][j];
                        buttons[i][j] = null;
                        buttons[i+1][j] = temp;
                    }
                }
            }

        }
    }

    public JButton[][] getButtons() {
        return buttons;
    }


}

/*
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



    @Override
    public void actionPerformed(ActionEvent e) {
        for(JButton[] buttons : buttons){
            for (int i = 0; i < rows ; i++) {
                if(e.getSource()==buttons[i]){
                    buttons[i].setForeground(Color.RED);
                }
            }

        }
    }*/