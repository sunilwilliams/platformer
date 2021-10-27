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
    boolean left = false;
    boolean right = false;

    JLabel youDie = new JLabel();

    JLabel[] triangle = new JLabel[1000000];
    int triangleNum = 0;
    double sharpness = 1;
    int[] triangleX = new int[100000];
    int[] triangleY = new int[100000];

    JFrame frame = new JFrame("Platformer");
    JLabel label = new JLabel();
    JLabel ground = new JLabel();
    JLabel[] platform = new JLabel[10];
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

        for (int i = 0; i < platform.length; i++) {
            platform[i] = new JLabel();
            platform[i].setBounds(600, 700, 200, 50);

        }

        platform[0].setBounds(300, 350, 200, 25);
        platform[0].setBackground(Color.GREEN);
        platform[0].setOpaque(true);
        frame.add(platform[0]);

        platform[1].setBounds(0, 150, 200, 25);
        platform[1].setBackground(Color.GRAY);
        platform[1].setOpaque(true);
        frame.add(platform[1]);


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
        if (positionY >= ground.getY() - 55)
            touchingGround = true;

        return touchingGround;
    }

    public int[] touchingPlatform() {
        boolean touchingPlatform = false;
        int platformNum = 1000;
        for (int i = 0; i < platform.length; i++) {
            if (positionX > platform[i].getX() - 50 && positionX < platform[i].getX() + platform[i].getWidth() && positionY >= platform[i].getY() - 60 && positionY <= platform[i].getY()) {
                touchingPlatform = true;
                //System.out.println(true);
                platformNum = i;

            }
        }

        int[] pos = new int[3];

        if (touchingPlatform) {
            pos[0] = 1;
            pos[1] = platform[platformNum].getX();
            pos[2] = platform[platformNum].getY();
        } else {
            pos[0] = 0;
            pos[1] = 0;
            pos[2] = 0;
        }



        return pos;
    }

    int touchingNum = 0;

    public int[] touchingPlatformBottom() {
        boolean touchingPlatform = false;
        int platformNum = 1000;
        if (touchingNum > 5) {
            for (int i = 0; i < platform.length; i++) {
                if (positionX > platform[i].getX() - 50 && positionX < platform[i].getX() + platform[i].getWidth() && positionY > platform[i].getY() && positionY <= platform[i].getY() + platform[i].getHeight()) {
                    touchingPlatform = true;
                    //System.out.println(true);
                    platformNum = i;
                    touchingNum = 0;

                }
            }
        }

        int[] pos = new int[3];

        if (touchingPlatform) {
            pos[0] = 1;
            pos[1] = platform[platformNum].getX();
            pos[2] = platform[platformNum].getY();
        } else {
            pos[0] = 0;
            pos[1] = 0;
            pos[2] = 0;
        }

        return pos;
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
                if (touchingGround() || touchingPlatform()[0] == 1) {

                    jump = true;
                }
            }
            break;
            case 'w': {
                if (touchingGround() || touchingPlatform()[0] == 1) {
                    jump = true;
                }
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

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (touchingGround() || touchingPlatform()[0] == 1) {

                jump = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        while (running && alive) {

        if (touchingGround() && !jump) {
            gravity = gravity * -1;
            gravity = 0;
            //System.out.println("on ground");
            positionY = 450;
        }

            if (touchingPlatform()[0] == 1 && !jump) {
                gravity = 0;
                positionY = touchingPlatform()[2] - 50;
            }

            if (touchingPlatformBottom()[0] == 1 && !jump) {
                gravity = 0;
            }

            touchingNum++;

            if (jump) {
                gravity = -25;
                //System.out.println(true);
            }

            if (left)
                move = move - 15;

            if (right)
                move = move + 15;



        label.setLocation(positionX + (int)move, positionY + (int)gravity);

        left = false;
        right = false;

            jump = false;

            positionX = positionX + (int)move;
            positionY = positionY + (int)gravity;

            gravity++;
            move = move / 1.1;

            for (int i = 0; i < 10000; i++) {
                if (triangleX[i] >= positionX && triangleX[i] <= positionX + 50 && triangleY[i] >= positionY && triangleY[i] <= positionY + 50) {
                    //System.out.println("you die");
                    //youDie();
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