package controler;

import viewmodel.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control implements ActionListener {

    private Board board;
    //private JButton[] buttonArray;
    private JButton[][] buttons ;
    JButton reset;
    JButton newGame;


    public Control(){
        board = new Board();
        //buttonArray = board.getBoardSingle();
        buttons = board.getButtons();
        //Without pressing "New game", then not shuffled yet two dimensional array not set up
        if(buttons!=null) {
         //addListener(buttons);
        }
    }

/*    public void addListener(JButton[][] buttonsTwoDimensional){
        for (int i = 0; i < buttonsTwoDimensional.length ; i++) {
            for (int j = 0; j < buttonsTwoDimensional.length ; j++) {

                if(buttons[i][j]!=null) {
                    System.out.println("["+i+"] ["+j+"]:"+buttons[i][j].getText());
                    buttons[i][j].addActionListener(this);
                }
            }
        }

    }*/

    public static void main(String[] args) {
        Control control = new Control();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== buttons[0][0]){
            JButton temp = buttons[0][0];
            buttons[0][0] = buttons[0][1];
            buttons[0][1] = temp;
            System.out.println("Event");
        }
    }
}
