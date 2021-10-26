import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Platformer implements KeyListener, Runnable {
    boolean[][] board = new boolean[20][20];
    //boolean[][] snake = new boolean[10][10];
    //SnakePanel panel = new SnakePanel(snake);

    int tailLength = 3;
    int direction = 1;
    boolean running = false;
    boolean alive = true;

    double gravity = 0;
    double move = 0;

    int positionX = 0;
    int positionY = 0;

    boolean jump = false;


    JLabel youDie = new JLabel();

    JLabel[] triangle = new JLabel[1000000];
    int triangleNum = 0;
    double sharpness = 1;
    int[] triangleX = new int[100000];
    int[] triangleY = new int[100000];

    JFrame frame = new JFrame("Platformer");
    JLabel label = new JLabel();
    JLabel ground = new JLabel();
    JLabel[] tailPieces = new JLabel[100000];


    public static void main(String[] args) {  //main method

        new Platformer();

    }

    public Platformer() {  //constructor
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.addKeyListener(this);


        triangle(450, 450, 1);

        youDie.setLocation((frame.getWidth()/2) - 125, (frame.getHeight()/2) - 80);
        youDie.setSize((500), (100));
        youDie.setFont(new Font("SansSerif", Font.BOLD, 50));
        frame.add(youDie);


        label.setBounds(positionX, positionY, 50, 50);
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

        running = false;

        youDie.setText(null);

        label.setBounds(50, 50, 50, 50);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
    }

    public void youDie() {
        running = false;

        youDie.setText("You Die");

    }

    public void triangle(int x, int y, double sharpness) {
        int color = 0;
        double colorDouble = 0;
        for (int h = 0; h < 50; h++) {
            for (int i = -50; i <= 50; i++) {
                int j = i;
                if (i < 0)
                    j = i * (-1);

                if (j + h < 50) {
                    triangle[triangleNum] = new JLabel();
                    triangle[triangleNum].setBounds(x + i, y + (int)(sharpness * (j + h)), 1, 1);
                    triangleX[triangleNum] = x + i;
                    triangleY[triangleNum] = y + (int)(sharpness * (j + h));
                    triangle[triangleNum].setBackground(new Color(50, color, 255));
                    triangle[triangleNum].setOpaque(true);
                    frame.add(triangle[triangleNum]);
                    triangleNum++;
                    if (color < 255) {
                        colorDouble = (colorDouble + .05);
                        color = (int) (colorDouble);
                    }
                    //System.out.println(color);

                }
            }
        }
    }

    public void rotate() {



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
                move = move - 10;
            }
            break;
            case 'd': {
                move = move + 10;
            }
            break;
            case ' ': {
                if (touchingGround()) {
                    gravity = -25;
                    jump = true;
                }
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
            move = move - 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move = move + 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (touchingGround()) {
                gravity = -25;
                jump = true;
            }
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





        if (touchingGround() && !jump) {
            gravity = 0;
            //System.out.println("on ground");
            positionY = 450;
        }
            //System.out.println(touchingGround());

        label.setLocation(positionX + (int)move, positionY + (int)gravity);

            //System.out.println(headPositionX + ", " + (int)(headPositionY + gravity));

            jump = false;




            positionX = positionX + (int)move;
            positionY = positionY + (int)gravity;

            gravity++;
            move = move / 1.1;

            for (int i = 0; i < 10000; i++) {
                if (triangleX[i] >= positionX && triangleX[i] <= positionX + 50 && triangleY[i] >= positionY && triangleY[i] <= positionY + 50) {
                    System.out.println("you die");
                    youDie();
                }
            }


            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test() {
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 100; y++) {
                triangle[triangleNum] = new JLabel();
                triangle[triangleNum].setBounds (50 + x, 50 + y, 1, 1);
                triangle[triangleNum].setBackground(Color.GREEN);
                triangle[triangleNum].setOpaque(true);
                frame.add(triangle[triangleNum]);
                triangleNum++;
            }
        }
    }
}