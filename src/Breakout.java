import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout implements KeyListener, Runnable {

    boolean running = false;

    double move = 0;

    int positionX = 0;
    int positionY;

    int ballX = 0;
    int ballY = 0;

    double ballMoveX = 2;
    double ballMoveY = 2;


    JLabel youDie = new JLabel();

    JFrame frame = new JFrame("Platformer");
    JLabel label = new JLabel();
    JLabel ball = new JLabel();
    JLabel[][] brick = new JLabel[1000][1000];
    int brickNum = 0;

    JLabel[] shadow = new JLabel[1000000];

    public static void main(String[] args) {  //main method

        new Breakout();

    }

    public Breakout() {  //constructor
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.addKeyListener(this);

        positionY = frame.getHeight() - 100;

        youDie.setLocation((frame.getWidth()/2) - 125, (frame.getHeight()/2) - 80);
        youDie.setSize((500), (100));
        youDie.setFont(new Font("SansSerif", Font.BOLD, 50));
        frame.add(youDie);


        label.setBounds(positionX, positionY, 200, 25);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);


        frame.add(label);

        ball.setBounds(50, 50, 25, 25);
        ball.setBackground(Color.GREEN);
        ball.setOpaque(true);
        frame.add(ball);

        int rows = 5;
        int columns = 8;
        int spacing = frame.getWidth() / columns;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                brick[x][y] = new JLabel();
                brick[x][y].setBounds(x * spacing + 10, y * spacing + 10, 50, 50);
                //System.out.println(x * 75 + ", " + y * 75);

                brick[x][y].setBackground(Color.ORANGE);
                brick[x][y].setOpaque(true);
                frame.add(brick[x][y]);
            }
        }

        for (int i = 0; i < 100; i++) {
            shadow[i] = new JLabel();
            shadow[i].setSize(200, 25);
            frame.add(shadow[i]);
        }

        int i = 0;

            for (int j = 0; j < 100; j++) {

                shadow[i].setLocation(j + positionX, j + positionY);
                //System.out.println(h);
                System.out.println((j) + ", " + j);

                shadow[i].setBackground(Color.LIGHT_GRAY);
                shadow[i].setOpaque(true);
                i++;
            }


        frame.setVisible(true);


    }

    public void reset() {

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 5; y++) {
                brick[x][y].setSize(50, 50);
            }
        }

        running = true;

        ballX = positionX + 100;
        ballY = positionY - 25;
        ballMoveX = -2;
        ballMoveY = -2;

        youDie.setText(null);

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
                move = move - 15;
            }
            break;
            case 'd': {
                move = move + 15;
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
            move = move - 5;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move = move + 5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (running) {
            //System.out.println(touchingGround());

            int i = 0;

                for (int j = 0; j < 100; j++) {

                    shadow[i].setLocation(j + positionX, j + positionY);
                    i++;
                }



            label.setLocation(positionX, positionY);
            if (positionX < 0)
                move = 1;
            if (positionX > frame.getWidth() - 200)
                move = -1;

            positionX = positionX + (int)move;
            move = move / 1.03;

            ballX = ballX + (int)ballMoveX;
            ballY = ballY + (int)ballMoveY;

            ball.setLocation(ballX, ballY);

            if (ballX <= 0)
                ballMoveX = ballMoveX * (-1);

            if (ballY <= 0)
                ballMoveY = ballMoveY * (-1);

            if (ballX >= frame.getWidth() - 25)
                ballMoveX = ballMoveX * (-1);

            if (ballY >= frame.getHeight() - 25) {
                //youDie.setText("You Lose");
                //running = false;
                ballMoveY = ballMoveY * (-1);
            }

            if (ballX > positionX - 25 && ballX < positionX + 200 && ballY == positionY - 25) {
                ballMoveY = ballMoveY * (-1);
                //ballMoveX = ballMoveX + (move / 8);
            }





            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 5; y++) {
                    if (brick[x][y].getWidth() == 50) {
                        if (ballX >= brick[x][y].getX() - 25 && ballX <= brick[x][y].getX() + brick[x][y].getWidth()) {
                            if (ballY == brick[x][y].getY() + brick[x][y].getHeight() || ballY == brick[x][y].getY() + brick[x][y].getHeight() + 1 || ballY == brick[x][y].getY() - 25 || ballY == brick[x][y].getY() - 24) {
                                ballMoveY = ballMoveY * (-1);
                                brick[x][y].setSize(0, 0);
                            }
                        }

                        if (ballY >= brick[y][y].getY() - 25 && ballY <= brick[x][y].getY() + brick[x][y].getHeight()) {
                            if (ballX == brick[x][y].getX() + brick[x][y].getWidth() || ballX == brick[x][y].getX() + brick[x][y].getWidth() + 1 || ballX == brick[x][y].getX() - 25 || ballX == brick[x][y].getX() - 24) {
                                ballMoveX = ballMoveX * (-1);
                                brick[x][y].setSize(0, 0);
                            }
                        }
                    }
                }
            }




            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test() {
        int x = 0;
        int y = 0;
        if (ballX >= brick[x][y].getX() - 25 && ballX <= brick[x][y].getX() + 50 && ballY >= brick[x][y].getY() - 25 && ballY <= brick[x][y].getY() + 50) {


            ballMoveY = ballMoveY * (-1);


            ballMoveX = ballMoveX * (-1);
            brick[x][y].setSize(0, 0);

        }
    }

}