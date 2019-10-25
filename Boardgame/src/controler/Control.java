package controler;

import viewmodel.Board;

import javax.swing.*;
import java.awt.*;

public class Control {

    private Board board;
    private JButton[] buttons;
    private JButton[][] buttonsInDimensionalArray;


    public Control(){
        board = new Board();
        buttons = board.getBoardSingle();
        buttonsInDimensionalArray = board.getButtonsInDimensionalArray();
    }


    public static void main(String[] args) {
        Control control = new Control();
    }
}
