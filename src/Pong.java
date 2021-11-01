import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong implements KeyListener, Runnable {
    JFrame frame = new JFrame("Flappy Bird");

    JLabel ball = new JLabel();
    JLabel player1 = new JLabel();
    JLabel player2 = new JLabel();


    boolean running = false;



    public static void main(String[] args) {  //main method

        new Pong();

    }

    public Pong() {  //constructor
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.addKeyListener(this);

        ball.setBounds(300, 300, 10, 10);
        ball.setBackground(Color.BLACK);
        ball.setOpaque(true);


        frame.add(ball);

        player1.setBounds(50, 300, 10, 100);
        player1.setBackground(Color.BLACK);
        player1.setOpaque(true);


        frame.add(player1);

        player2.setBounds(525, 300, 10, 100);
        player2.setBackground(Color.BLACK);
        player2.setOpaque(true);


        frame.add(player2);



        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!running) {
            running = true;
            Thread t = new Thread(this);
            t.start();
        }

        switch (e.getKeyChar()) {
            case 'w': {
                move1 = - 10;
            }
            break;
            case 's': {
                move1 = 10;
            }
            break;
            case 'r': {
                //reset();
            }

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            move2 = -10;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move2 = 10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    int moveX = -1;
    int moveY = 1;
    double move1 = 0;
    double move2 = 0;

    @Override
    public void run() {
        while (running) {

            ball.setLocation(ball.getX() + moveX, ball.getY() + moveY);

            player1.setLocation(50, player1.getY() + (int)move1);
            player2.setLocation(525, player2.getY() + (int)move2);

            move1 = move1 / 1.1;
            move2 = move2 / 1.1;

            if (ball.getX() == 60 && ball.getY() > player1.getY() && ball.getY() < (player1.getY() + 100)) { //if touching player 1
                moveX = moveX * -1;
                //moveY = move
            }

            if (ball.getX() == 515 && ball.getY() > player2.getY() && ball.getY() < (player2.getY() + 100)) { //if touching player 2
                moveX = moveX * -1;
            }

            if (ball.getY() == 0) {
                moveY = moveY * -1;
            }

            if (ball.getY() == frame.getHeight() - 45) {
                moveY = moveY * -1;
            }

            if (ball.getX() < 60)
                System.out.println("player 2 wins");

            if (ball.getX() > 515)
                System.out.println("player 1 wins");





            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
