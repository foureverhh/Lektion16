import javax.swing.*;
import java.awt.*;

public class Board {
    JFrame frame;
    JLabel label;
    JTextField rows;
    JButton reStart;
    JPanel controlPanel;
    JScrollPane gameZone;

    Board(){
        frame = new JFrame("Enjoy the game!");
        label = new JLabel("Input rows:");
        rows = new JTextField(20);
        reStart = new JButton("restart");
        controlPanel = new JPanel();
        gameZone = new JScrollPane();

        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(label);
        controlPanel.add(rows);
        controlPanel.add(reStart);

        frame.add(controlPanel);
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Board();
    }
}
