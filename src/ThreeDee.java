import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThreeDee implements KeyListener, Runnable {
    boolean running = false;
    boolean jump = false;
    boolean left = false;
    boolean right = false;

    JFrame frame = new JFrame("3D");

    JLabel[][] square = new JLabel[100][10000];


    public static void main(String[] args) {  //main method

        new ThreeDee();

    }

    public ThreeDee() {  //constructor
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.addKeyListener(this);

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 10000; y++) {
                square[x][y] = new JLabel();
                square[x][y].setSize(1, 1);
                frame.add(square[x][y]);
            }
        }

        //line(0, 50, 50, .5);

        for (int i = 0; i < 10; i++) {
            //line(0, 50, 50, .5, i);
        }




        frame.setVisible(true);


    }

    int lineNumber = 0;
    double pi = Math.PI;

    public void line(int lineGroup, int x, int y, double angle, int lineNum) {
        //System.out.println((Math.sin((pi / 2) - angle)) / pi);
        for (int h = -13; h < 14; h++) {
            double a = (Math.cos(angle) * h);
            double b = (Math.sin(angle) * h);
            double c = (Math.cos(angle - (pi / 2)) * lineNum);
            double d = (Math.sin(angle - (pi / 2)) * lineNum);

            //double c = (Math.cos((pi / 2) - angle) * lineNum);
            //double d = (Math.sin((pi / 2) - angle) * lineNum);

            square[lineGroup][lineNumber].setLocation(x + (int)(4 * (a + c)), y + (int)(4 * (b + d)));
            square[lineGroup][lineNumber].setBackground(Color.BLACK);
            square[lineGroup][lineNumber].setOpaque(true);
            lineNumber++;
        }




    }

    double angle = 0;

    @Override
    public void run() {

        while (running) {

            lineNumber = 0;

            for (int i = -13; i < 14; i++) {
                line(0, 300, 300, angle, i);
            }

            line(1, 30, 30, angle, 0);

            if (angle < (pi / 2))
                running = false;

            angle = angle + pi/64;

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
                left = true;
            }
            break;
            case 'd': {
                right = true;
            }
            break;
            case ' ': {
                angle = 0;

                    jump = true;
                //}
            }
            break;
            case 'w': {
                //if (touchingGround() || touchingPlatform()[0] == 1) {
                    jump = true;
                //}
            }
            break;
            case 'r': {
                //reset();
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

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}