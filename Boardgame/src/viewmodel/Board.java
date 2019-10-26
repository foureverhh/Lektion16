package viewmodel;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board implements ActionListener{ //implements MouseListener{
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

    private int indexOfEmpty;


    public Board(){

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
                gameZone.repaint();
                gameZone.revalidate();

                System.out.println("Reset, rows:"+rows+"boardSingle length: "+boardSingle.length);
        });

        newGame.addActionListener(l -> {
                gameZone.removeAll();
                shuffleBoardByArray(boardSingle);
                //Store JButton array to JButton[][]
                //storeInTwoDimensionalArray(boardSingle);
                gameZone.revalidate();
                gameZone.repaint();
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
        System.out.println("Print, "+boardSingle.length);
        //To make board repaint for reset
        //gameZone.repaint();
        //for (int i = 0; i < boardSingle.length-1 ; i++) {
        for (int i = 0; i < boardSingle.length ; i++) {
            boardSingle[i] = new JButton(""+(i+1));
            if(i==boardSingle.length-1){
                boardSingle[i] = new JButton();
                boardSingle[i].setOpaque(false);
                boardSingle[i].setContentAreaFilled(false);
                boardSingle[i].setBorderPainted(false);
            }
            gameZone.add(boardSingle[i]);
        }
        indexOfEmpty =boardSingle.length-1;
        return boardSingle;
    }

    //Make the empty shows only on right bottom corner
    public void shuffleBoardByArray(JButton[] boardSingle){

        //For gameZone to refresh
        while (true) {
            //Make random order
            //gameZone.revalidate();
            //for (int i = boardSingle.length - 1 - 1; i >= 0; i--) {
            for (int i = boardSingle.length - 1-1; i >= 0; i--) {
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
                //for (int i = 0; i < boardSingle.length-1; i++) {
                for (int i = 0; i < boardSingle.length-1; i++) {
                    System.out.print(boardSingle[i].getText()+" ");
                    boardSingle[i].addActionListener(this);
                    gameZone.add(boardSingle[i]);
                }
                //boardSingle[boardSingle.length-1].addActionListener(this);
                gameZone.add(boardSingle[boardSingle.length-1]);
                System.out.println();
                break;
            }
        }
        //System.out.println(boardSingle.length);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < boardSingle.length ; i++) {
            if(e.getSource() == boardSingle[i]){
                if((i==indexOfEmpty+1 && (indexOfEmpty+1)%rows!=0) || (i==indexOfEmpty-1 && indexOfEmpty%rows!=0) || i==indexOfEmpty+rows|| i==indexOfEmpty-rows){
                    //swap board
                    JButton temp = boardSingle[i];
                    boardSingle[i] = boardSingle[indexOfEmpty];
                    boardSingle[indexOfEmpty]=temp;
                    indexOfEmpty = i;
                    //Update the
                    updateBoard(boardSingle);
                    System.out.println("event listener, rows: "+rows+", board: "+boardSingle.length+",indexOfBoard: "+indexOfEmpty);
                    break;
                }
            }
        }
        if(checkWin(boardSingle)){
            JOptionPane.showMessageDialog(gameZone,"You win!","Congratulation",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public boolean checkWin(JButton[] boardSingle){
        boolean win = true;
        for (int i = 0; i < boardSingle.length-1 ; i++) {
            if(boardSingle[i].getText().compareTo(""+(i+1))!=0){
                win = false;
                break;
            }
        }
        return win;
    }

    public void updateBoard(JButton[] boardSingle){
        gameZone.removeAll();
        for(JButton button:boardSingle){
            //Add listener again
            //button.addActionListener(this);
            //Reload buttons
            gameZone.add(button);
        }
        gameZone.repaint();
        gameZone.revalidate();
        System.out.println("Update runs");

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
/*

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
                    //buttons[i][j].addMouseListener(this);
                }
            }
        }
        //gameZone.addMouseListener(this);
        System.out.println();
    }
*/


/*
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i <buttons.length ; i++) {
            for (int j = 0; j <buttons.length ; j++) {
                if(e.getSource()==buttons[i][j]) {
                    if ((i == nullX - 1 && j == nullY) || (i == nullX + 1 && j == nullY) || (i == nullX && j == nullY + 1) || (i == nullX && j == nullY - 1)) {
                        JButton temp = buttons[i][j];
                        buttons[i][j] = null;
                        buttons[nullX][nullY] = temp;
                        nullX = i;
                        nullY = j;
                        System.out.println("Inside: nullX: " + nullX + " " + "nullY: " + nullY);
                    }
                }
            }
        }
        System.out.println("Outside: nullX: " + nullX + " " + "nullY: " + nullY);
    }
*/



/*
    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i <buttons.length ; i++) {
            for (int j = 0; j <buttons.length ; j++) {
                if(e.getSource()==buttons[i][j]){
                    System.out.print("Listener: [" + i + "][" + j + "] " + buttons[i][j].getText()+" ");
                }
            }
        }
        if(buttons[3][3] == null){
            JButton temp = buttons[2][3];
            buttons[2][3] = null;
            buttons[3][3] = temp;
            gameZone.revalidate();
            System.out.println("I am here");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }*/
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