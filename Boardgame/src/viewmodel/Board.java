package viewmodel;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.tools.jconsole.Worker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Board implements ActionListener { //implements MouseListener{
    private JFrame frame;
    private JLabel label;
    private JLabel banner;
    private JTextField rowsInput;
    private JButton newGame;
    private JButton reset;
    private JPanel controlPanel;
    private JPanel bannerZone;
    private JPanel gameZone;
    private JScrollPane scrollPane;
    private JButton[] boardSingle;
    private int rows = 4;
    private int imageIndex = 0;
    private int indexOfEmpty;
    private Timer timer;
    //To valid and invalid listener added on printBoard
    private boolean gameOn;

    private URL[] urls;
    {
        try {
            urls = new URL[]{
                        new URL("https://photojournal.jpl.nasa.gov/jpeg/PIA18906.jpg"),
                        new URL("https://live.staticflickr.com/1948/44335552035_5d2afd2a74_b.jpg"),
                        new URL("https://upload.wikimedia.org/wikipedia/commons/a/aa/Polarlicht_2.jpg"),
                        new URL("http://www.google.com/logos/doodles/2015/googles-new-logo-5078286822539264.3-hp2x.gif")
                };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Board() {

        frame = new JFrame("Enjoy the game!");
        label = new JLabel("Input rows:");
        banner = new JLabel();
        rowsInput = new JTextField(5);
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
            playSoundEffect("Resources/Banana Peel Slip Zip-SoundBible.com-803276918.wav");
            try {
                rows = Integer.parseInt(rowsInput.getText());
                if(rows <2){

                    throw new Exception("Number too small");
                }
                //Remove all old buttons
                gameZone.removeAll();
                //Reprint all new buttons
                boardSingle = printBoardByArray(rows);
                gameZone.repaint();
                gameZone.revalidate();

                System.out.println("Reset, rows:" + rows + "boardSingle length: " + boardSingle.length);
            }catch (Exception e){
                playSoundEffect("Resources/Slap-SoundMaster13-49669815.wav");
                Timer timer = new Timer(300,soundListener->{
                    playSoundEffect("Resources/No Dear-SoundBible.com-223285016.wav");
                });
                timer.setRepeats(false);
                timer.start();
                JOptionPane.showMessageDialog(null,"Only an integer larger than 1 is accepted");
            }
        });

        newGame.addActionListener(l -> {
            playSoundEffect("Resources/Banana Peel Slip Zip-SoundBible.com-803276918.wav");
            shuffleBoardByArray(boardSingle);
        });

        scrollPane = new JScrollPane(gameZone);

        bannerZone = new JPanel();
        banner.setSize(100,50);
        bannerZone.add(banner,new FlowLayout(FlowLayout.CENTER));

        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bannerZone, BorderLayout.SOUTH);

        //Add banner
        timer = new Timer(5000, l -> {
                    showImages();
        });
        timer.setRepeats(true);
        timer.start();

        //Show all components
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Board();
    }

    public JButton[] printBoardByArray(int rows) {
        gameOn = false;
        gameZone.setLayout(new GridLayout(rows, rows));
        boardSingle = new JButton[rows * rows];
        System.out.println("Print, " + boardSingle.length);
        for (int i = 0; i < boardSingle.length; i++) {
            boardSingle[i] = new JButton("" + (i + 1));
            if (i == boardSingle.length - 1) {
                boardSingle[i] = new JButton();
                boardSingle[i].setOpaque(false);
                boardSingle[i].setContentAreaFilled(false);
                boardSingle[i].setBorderPainted(false);
            }
            boardSingle[i].addActionListener(this);
            gameZone.add(boardSingle[i]);
        }
        indexOfEmpty = boardSingle.length - 1;
        return boardSingle;
    }

    //Make the empty shows only on right bottom corner
    public void shuffleBoardByArray(JButton[] boardSingle) {

        //For gameZone to refresh
        gameZone.removeAll();
        while (true) {
            for (int i = boardSingle.length - 1 - 1; i >= 0; i--) {
                int index = (int) (Math.random() * i);
                JButton temp = boardSingle[i];
                boardSingle[i] = boardSingle[index];
                boardSingle[index] = temp;
            }
            //Check the sum of inversion is even or odd
            int inversion = inversion(boardSingle) % 2;
            //polarity in two dimensional system
            int polarity = ((rows - 1) + (rows - 1)) % 2;
            if (inversion == polarity) {
                gameOn = true;
                System.out.println("Get array[] in shuffle:");
               for (int i = 0; i < boardSingle.length - 1; i++) {
                    System.out.print(boardSingle[i].getText() + " ");
                    gameZone.add(boardSingle[i]);
                }
                gameZone.add(boardSingle[boardSingle.length - 1]);
                gameZone.validate();
                gameZone.repaint();
                System.out.println("boardSingle"+boardSingle.length);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOn) {
            for (int i = 0; i < boardSingle.length; i++) {
                if (e.getSource() == boardSingle[i]) {
                    if ((i == indexOfEmpty + 1 && (indexOfEmpty + 1) % rows != 0) || (i == indexOfEmpty - 1 && indexOfEmpty % rows != 0) || i == indexOfEmpty + rows || i == indexOfEmpty - rows) {
                        playSoundEffect("Resources/Banana Peel Slip Zip-SoundBible.com-803276918.wav");
                        //swap board
                        JButton temp = boardSingle[i];
                        boardSingle[i] = boardSingle[indexOfEmpty];
                        boardSingle[indexOfEmpty] = temp;
                        indexOfEmpty = i;
                        //Update the board
                        updateBoard(boardSingle);
                        System.out.println("event listener, rows: " + rows + ", board size: " + boardSingle.length + ",indexOfEmpty: " + indexOfEmpty);
                        break;
                    }else{
                        playSoundEffect("Resources/Beep-SoundBible.com-923660219.wav");
                    }
                }
            }
            if (checkWin(boardSingle)) {
                gameOn = false;
                playSoundEffect("Resources/Ta Da-SoundBible.com-1884170640.wav");
                JOptionPane.showMessageDialog(gameZone, "You win!", "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public boolean checkWin(JButton[] boardSingle) {
        boolean win = true;
        //Compare text and index
        for (int i = 0; i < boardSingle.length - 1; i++) {
            if (boardSingle[i].getText().compareTo("" + (i + 1)) != 0) {
                win = false;
                break;
            }
        }
        return win;
    }

    public void updateBoard(JButton[] boardSingle) {
        gameZone.removeAll();
        for (JButton button : boardSingle) {
            gameZone.add(button);
        }
        gameZone.repaint();
        gameZone.revalidate();
    }

    public int inversion(JButton[] boardSingle) {
        int inversion = 0;
        //The last element of boardSingle is null so end with <length-1,not <=length-1
        for (int i = 0; i < boardSingle.length - 1; i++) {
            //should compareTo ""+(i+1),as text on buttons are (i+1)
            if (boardSingle[i].getText().compareTo("" + (i + 1)) > 0) {
                inversion++;
            }
        }
        return inversion;
    }

    public void showImages(){
            SwingWorker<Icon,Void> imageLoader =new SwingWorker<Icon, Void>() {
            @Override
            protected Icon doInBackground() throws Exception {
                ImageIcon imageIcon = null;
                System.out.println("Load images");
                imageIndex = (imageIndex + 1) % urls.length;
                BufferedImage image = ImageIO.read(urls[imageIndex]);
                image = resizeImage(image,200,50);
                System.out.println(imageIndex);
                imageIcon = new ImageIcon(image);
                return imageIcon;
            }

            @Override
            protected void done() {
                try {
                    banner.setIcon(get());
                } catch (InterruptedException e) {
                    banner.setText("Failed loading pic"+imageIndex);
                } catch (ExecutionException e) {
                    banner.setText("Failed to load pic"+imageIndex);
                }
            }
        };
            imageLoader.execute();
    }

    public BufferedImage resizeImage(Image scr,int width,int height){
        BufferedImage resizedImage = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(scr, 0, 0, 200, 200, null);
        g2.dispose();
        return resizedImage;
    }

    public void playSoundEffect(String file){
        try {
            InputStream inputStream = new FileInputStream(file);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            System.out.println("Sound effect "+file+" not found.");
        }
    }
}