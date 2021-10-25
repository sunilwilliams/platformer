import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyPressTest implements KeyListener, Runnable {
    boolean[][] board = new boolean[20][20];
    //boolean[][] snake = new boolean[10][10];
    //SnakePanel panel = new SnakePanel(snake);

    int tailLength = 3;
    int headPositionX = 3;
    int headPositionY = 3;
    int direction = 1;
    //int[][] corner = new int[board.length][board[0].length];
    boolean running = false;
    boolean alive = true;

    int difficulty = 150;



    JLabel youDie = new JLabel();



    JLabel apple = new JLabel();
    int appleX;
    int appleY;



    JFrame frame = new JFrame("Snake");
    JLabel label = new JLabel();
    JLabel[] tailPieces = new JLabel[100000];



    int[][] lastPlaces = new int[2][100000];
    int place = tailLength;

    JLabel[][] background = new JLabel[board.length][board[0].length];

    public static void main(String[] args) {  //main method

        new KeyPressTest();

    }

    public KeyPressTest() {  //constructor
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.addKeyListener(this);



        youDie.setLocation((frame.getWidth()/2) - 125, (frame.getHeight()/2) - 80);
        youDie.setSize((500), (100));
        youDie.setFont(new Font("SansSerif", Font.BOLD, 50));
        //youDie.setSize(600, 600);
        //youDie.setBounds(100, 100, 100, 100);
        frame.add(youDie);


        label.setBounds((headPositionX * (frame.getWidth()/board.length)), (headPositionY * (frame.getHeight()/board[0].length)), (frame.getWidth()/board.length), (frame.getHeight()/board[0].length));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);

        appleX = ((int) (Math.random() * (board.length -1)) * (frame.getWidth()/board.length));
        appleY = ((int) (Math.random() * (board[0].length -1))  * (frame.getHeight()/board[0].length));

        //System.out.println(appleX + ", " + appleY);

        apple.setBounds(appleX, appleY, (frame.getWidth()/board.length), (frame.getHeight()/board[0].length));
        apple.setBackground(Color.RED);
        apple.setOpaque(true);
        frame.add(apple);

        frame.add(label);






        //lastPlaces[0][0] = 3;
        //lastPlaces[1][0] = 2;

        //lastPlaces[0][1] = 3;
        //lastPlaces[1][1] = 1;

        for (int i = 0; i < 10000; i++) {
            tailPieces[i] = new JLabel();
            tailPieces[i] = new JLabel();
            frame.add(tailPieces[i]);
        }

        for (int a = 0; a < board.length; a++) {
            for (int b = 0; b < board[0].length; b++) {
                if ((a + b) % 2 == 0) {
                    background[a][b] = new JLabel();
                    background[a][b].setSize((frame.getWidth() / board.length), (frame.getHeight() / board[0].length));
                    background[a][b].setLocation((a * (frame.getWidth() / board.length)), (b * (frame.getHeight() / board[0].length)));
                    background[a][b].setBackground(Color.LIGHT_GRAY);
                    background[a][b].setOpaque(true);
                    frame.add(background[a][b]);
                }
                else {
                    background[a][b] = new JLabel();
                    background[a][b].setSize((frame.getWidth() / board.length), (frame.getHeight() / board[0].length));
                    background[a][b].setLocation((a * (frame.getWidth() / board.length)), (b * (frame.getHeight() / board[0].length)));
                    background[a][b].setBackground(Color.WHITE);
                    background[a][b].setOpaque(true);
                    frame.add(background[a][b]);
                }
            }
        }


        //for (int i = 0; i < 100; i++) {
            //tailPieces[i] = new JLabel();
            //frame.add(tailPieces[i]);
            //tailPieces[i].setLocation(lastPlaces[0][place - i], lastPlaces[0][place - i]);
            //System.out.println(lastPlaces[0][place - i] + ", " + lastPlaces[0][place - i]);
            //tailPieces[i].setBackground(Color.BLUE);
            //tailPieces[i].setOpaque(true);
        //}

        //snake[5][5] = true;

        //frame.add(panel, BorderLayout.CENTER);
        //panel.addKeyListener(this);
        frame.setVisible(true);


    }

    public void apples(int x, int y) {

        if (x == appleX && y == appleY) {
            tailLength++;
            appleX = ((int) (Math.random() * (board.length -1)) * (frame.getWidth()/board.length));
            appleY = ((int) (Math.random() * (board[0].length -1))  * (frame.getHeight()/board[0].length));
            apple.setLocation(appleX, appleY);
            difficulty = (int) (difficulty * .95);
            //System.out.println(appleX + ", " + appleY);
        }

    }

    public void reset() {

        for (int i = 0; i < 10000; i++) {
            tailPieces[i].setOpaque(false);
        }

        tailLength = 3;
        headPositionX = 3;
        headPositionY = 3;
        direction = 1;
        running = false;
        alive = true;
        difficulty = 150;

        youDie.setText(null);

        label.setBounds((headPositionX * (frame.getWidth()/board.length)), (headPositionY * (frame.getHeight()/board[0].length)), (frame.getWidth()/board.length), (frame.getHeight()/board[0].length));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);

        appleX = ((int) (Math.random() * (board.length -1)) * (frame.getWidth()/board.length));
        appleY = ((int) (Math.random() * (board[0].length -1))  * (frame.getHeight()/board[0].length));

        //System.out.println(appleX + ", " + appleY);

        apple.setBounds(appleX, appleY, (frame.getWidth()/board.length), (frame.getHeight()/board[0].length));
        apple.setBackground(Color.RED);
        apple.setOpaque(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!running) {
            running = true;
            Thread t = new Thread(this);
            t.start();
        }

        switch (e.getKeyChar()) {
            case 'a': {
                if (direction != 1)
                direction = 0;
            }
                break;
            case 'd': {
                if (direction != 0)
                direction = 1;
            }
                break;
            case 'w': {
                if (direction != 3)
                direction = 2;
            }
                break;
            case 's': {
                if (direction != 2)
                direction = 3;
            }
                break;
            case 'r': {
                reset();
            }


        }




    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!running) {
            running = true;
            Thread t = new Thread(this);
            t.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (direction != 1)
                direction = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (direction != 0)
                direction = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (direction != 3)
                direction = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (direction != 2)
                direction = 3;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (running && alive) {


            lastPlaces[0][place] = (headPositionX * (frame.getWidth()/board.length));
            lastPlaces[1][place] = (headPositionY * (frame.getHeight()/board[0].length));
            //System.out.println(lastPlaces[0][place] + ", " + lastPlaces[1][place]);





            if (direction == 0) {
                headPositionX--;
            }
            if (direction == 1) {
                headPositionX++;
            }
            if (direction == 2) {
                headPositionY--;
            }
            if (direction ==3) {
                headPositionY++;
            }

            label.setLocation((headPositionX * (frame.getWidth()/board.length)), (headPositionY * (frame.getHeight()/board[0].length)));

            apples((headPositionX * (frame.getWidth()/board.length)), (headPositionY * (frame.getHeight()/board[0].length)));

            if (headPositionX == board.length || headPositionY == board[0].length || headPositionX == -1 || headPositionY == -1) {
                youDie.setText("YOU DIED");
                alive = false;
            }

            for (int i = 0; i < tailLength; i++) {

                //System.out.println(lastPlaces[0][place - i] + ", " + lastPlaces[0][place - i]);
                tailPieces[i].setBounds(lastPlaces[0][place - i], lastPlaces[1][place - i], (frame.getWidth()/board.length), (frame.getHeight()/board[0].length));
                tailPieces[i].setBackground(Color.BLUE);
                tailPieces[i].setOpaque(true);
                if (lastPlaces[0][place - i] == (headPositionX * (frame.getWidth()/board.length)) && lastPlaces[1][place - i] == (headPositionY * (frame.getHeight()/board[0].length))){
                    //System.out.println("you died");
                    youDie.setText("YOU DIED");
                    alive = false;

                }
            }

            place++;


            try {
                Thread.sleep(difficulty);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}