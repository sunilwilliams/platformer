import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlappyBird implements KeyListener, Runnable {
    boolean[][] board = new boolean[20][20];
    //boolean[][] snake = new boolean[10][10];
    //SnakePanel panel = new SnakePanel(snake);

    int tailLength = 3;
    int direction = 1;
    boolean running = false;
    boolean alive = true;

    double gravity = 0;
    double move = 0;

    int positionX = 225;
    int positionY = 275;

    boolean jump = false;
    boolean left = false;
    boolean right = false;


    JLabel[] triangle = new JLabel[1000000];
    int triangleNum = 0;
    double sharpness = 1;
    int[] triangleX = new int[100000];
    int[] triangleY = new int[100000];

    JFrame frame = new JFrame("Flappy Bird");
    JLabel label = new JLabel();
    JLabel ground = new JLabel();
    JLabel[] platform = new JLabel[10];

    JLabel score = new JLabel();


    public static void main(String[] args) {  //main method

        new FlappyBird();

    }

    public FlappyBird() {  //constructor
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.addKeyListener(this);


        label.setBounds(positionX, positionY, 50, 50);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);


        frame.add(label);

        ground.setBounds(0, 400, 1000, 200);
        ground.setBackground(Color.BLUE);
        ground.setOpaque(true);


        frame.add(ground);

        for (int i = 0; i < platform.length; i++) {
            platform[i] = new JLabel();
            platform[i].setBounds(600, 700, 200, 50);

            frame.add(platform[i]);

        }



        triangle(450, 350, 1);
        triangle(450, 449, -1);

        triangle(150, 50, 1);
        triangle(150, 149, -1);

        score.setLocation((frame.getWidth()/2) - 310, (frame.getHeight()/2) - 307);
        score.setSize((1000), (500));
        score.setForeground(Color.ORANGE);
        score.setFont(new Font("SansSerif", Font.BOLD, 500));
        //frame.setComponentZOrder(score, 5);
        frame.add(score);


        frame.setVisible(true);


    }

    public void addPlatform(int i, int x, int y, int width, int height, int r, int g, int b) {
        platform[i].setBounds(x, y, width, height);
        platform[i].setBackground(new Color(r, g, b));
        platform[i].setOpaque(true);
    }

    public void reset() {

        running = true;


        for (int i = 0; i < 10; i++) {
            platform[i].setLocation(600, 600);
        }
        //score.setText(null);

        //label.setBounds(275, 50, 50, 50);
    }

    public void triangle(int x, int y, double sharpness) {
        int triangleGroup;
        int color = 200;
        double colorDouble = 200;
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

    public boolean touchingGround() {
        boolean touchingGround = false;
        if (positionY >= ground.getY() - 50)
            touchingGround = true;

        return touchingGround;
    }

    public int[] touchingPlatform() {
        boolean touchingPlatform = false;
        int platformNum = 1000;
        for (int i = 0; i < platform.length; i++) {
            if (label.getX() > platform[i].getX() - 50 + (int) move && label.getX() < platform[i].getX() + platform[i].getWidth() + (int) move && positionY >= platform[i].getY() - 50 && positionY <= platform[i].getY()) {
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
                if (label.getX() > platform[i].getX() - 50 + (int) move && label.getX() < platform[i].getX() + platform[i].getWidth() + (int) move && positionY > platform[i].getY() && positionY <= platform[i].getY() + platform[i].getHeight()) {
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
                    jump = true;
            }
            break;
            case 'w': {
                    jump = true;
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

        if (e.getKeyCode() == KeyEvent.VK_UP) {
                jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        int counter = 100;
        int z = 0;
        int scoreNum = -1;

        while (running && alive) {

            if (touchingGround() || touchingPlatform()[0] == 1 || touchingPlatformBottom()[0] == 1) {
                score.setForeground(Color.BLACK);
                score.setFont(new Font("SansSerif", Font.BOLD, 10));
                running = false;
                score.setText("    You Die :(");
            }

            touchingNum++;

            if (jump) {
                gravity = -12;
                //System.out.println(true);
            }

            if (left)
                move = move + 15;

            if (right)
                move = move - 15;

            for (int i = 0; i < 10; i++) {
                platform[i].setLocation(platform[i].getX() - 3, platform[i].getY());
                //if (platform[i].getX() < -100)
            }


            label.setLocation(225, positionY + (int)gravity);




            if (counter == 100) {
                counter = 0;
                //System.out.println("spawn");



                if (z == 8) {
                    z = 0;
                }

                int y = (int) (Math.random() * 200);
                z++;

                addPlatform(z, 700, y * (-1), 100, 200, 250, 50, 50);
                z++;
                addPlatform(z, 700,  y * (-1) + 400, 100, 400, 250, 50, 50);

            }

            if (counter == 50) {
                scoreNum++;
                if (scoreNum > -1) {

                    score.setText(Integer.toString(scoreNum));
                    score.setForeground(Color.ORANGE);
                    score.setFont(new Font("SansSerif", Font.BOLD, 500));
                }
            }

            counter++;




            left = false;
            right = false;

            jump = false;

            positionX = positionX - 1;
            positionY = positionY + (int)gravity;

            gravity++;
            move = move / 1.1;

            for (int i = 0; i < 10000; i++) {
                if (triangle[i].getX() >= positionX && triangle[i].getX() <= positionX + 50 && triangle[i].getY() >= positionY && triangle[i].getY() <= positionY + 50) {
                    //System.out.println("you die");
                    //score();
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