import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Platformer implements KeyListener, Runnable {
    boolean[][] board = new boolean[20][20];
    //boolean[][] snake = new boolean[10][10];
    //SnakePanel panel = new SnakePanel(snake);

    int tailLength = 3;
    int headPositionX = 200;
    int headPositionY = 400;
    int direction = 1;
    //int[][] corner = new int[board.length][board[0].length];
    boolean running = false;
    boolean alive = true;

    double gravity = 0;
    double move = 0;

    int positionX = 0;
    int positionY = 0;


    JLabel youDie = new JLabel();
    JLabel apple = new JLabel();



    JFrame frame = new JFrame("Snake");
    JLabel label = new JLabel();
    JLabel ground = new JLabel();
    JLabel[] tailPieces = new JLabel[100000];



    int[][] lastPlaces = new int[2][100000];
    int place = tailLength;

    JLabel[][] background = new JLabel[board.length][board[0].length];

    public static void main(String[] args) {  //main method

        new Platformer();

    }

    public Platformer() {  //constructor
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


        label.setBounds(headPositionX, headPositionY, 50, 50);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);


        frame.add(label);

        ground.setBounds(0, 500, 1000, 100);
        ground.setBackground(Color.BLUE);
        ground.setOpaque(true);


        frame.add(ground);


        frame.setVisible(true);


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

        youDie.setText(null);

        label.setBounds((headPositionX * (frame.getWidth()/board.length)), (headPositionY * (frame.getHeight()/board[0].length)), (frame.getWidth()/board.length), (frame.getHeight()/board[0].length));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
    }

    public boolean touchingGround() {
        boolean touchingGround = false;
        if (positionY >= 450)
            touchingGround = true;

        return touchingGround;
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
            move = move - 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move = move + 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            gravity = -25;
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

        headPositionY = (int)(headPositionY + gravity);
        headPositionX = (int) (headPositionY * move);



        if (touchingGround()) {
            gravity = 0;
            //System.out.println("on ground");
            positionY = 450;
        }
            //System.out.println(touchingGround());

        label.setLocation(positionX + (int)move, positionY + (int)gravity);

            //System.out.println(headPositionX + ", " + (int)(headPositionY + gravity));


            positionX = positionX + (int)move;
            positionY = positionY + (int)gravity;

            gravity++;
            move = move / 1.1;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}