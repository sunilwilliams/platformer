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

    double ballMoveX = 5;
    double ballMoveY = 5;


    JLabel youDie = new JLabel();

    JFrame frame = new JFrame("Platformer");
    JLabel label = new JLabel();
    JLabel ball = new JLabel();
    JLabel[][] brick = new JLabel[1000][1000];
    int brickNum = 0;

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

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 5; y++) {
                brick[x][y] = new JLabel();
                brick[x][y].setBounds(x * 75 , y * 75, 50, 50);
                //System.out.println(x * 75 + ", " + y * 75);

                brick[x][y].setBackground(Color.ORANGE);
                brick[x][y].setOpaque(true);
                frame.add(brick[x][y]);
            }
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
        ballMoveX = -5;
        ballMoveY = -5;

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
            move = move - 15;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move = move + 15;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (running) {
            //System.out.println(touchingGround());

            label.setLocation(positionX, positionY);

            positionX = positionX + (int)move;
            move = move / 1.1;

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
                youDie.setText("You Lose");
                running = false;
            }

            if (ballX > positionX - 25 && ballX < positionX + 200 && ballY == positionY - 25) {
                ballMoveY = ballMoveY * (-1);
                ballMoveX = ballMoveX + (move / 2);
            }



            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 5; y++) {
                    if (brick[x][y].getWidth() == 50) {
                        if (ballX > (x * 75) - 25 && ballX < (x * 75) + 25 && ballY > (y * 75) - 25 && ballY < (y * 75) + 25) {

                            if (ballX > (x * 75) - 25 && ballX < (x * 75) + 25) {
                                brick[x][y].setSize(0, 0);
                                ballMoveY = ballMoveY * (-1);
                            }
                            if (ballY > (y * 75) - 25 && ballY < (y * 75) + 25) {
                                brick[x][y].setSize(0, 0);
                                ballMoveX = ballMoveX * (-1);
                            }
                        }
                    }
                }
            }


            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}