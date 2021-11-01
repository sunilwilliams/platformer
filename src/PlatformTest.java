import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlatformTest implements MouseListener, Runnable {


    int[] platformNum = new int[4];

    int[] barrierNum = new int[4];
    boolean running = false;

    double gravity = 0;

    JFrame frame = new JFrame();



    PlatformPanel platform;

    PlatformPanel barriers;




    public static void main(String[] args) {
        new PlatformTest();
    }

    public PlatformTest() {
        platformNum[0] = 50;
        platformNum[1] = 50;
        platformNum[2] = 50;
        platformNum[3] = 50;

        barrierNum[0] = 500;
        barrierNum[1] = 50;
        barrierNum[2] = 50;
        barrierNum[3] = 50;

        platform = new PlatformPanel(platformNum);

        barriers = new PlatformPanel(barrierNum);


        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.add(platform, BorderLayout.CENTER);
        frame.add(barriers, BorderLayout.NORTH);
        frame.addMouseListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.repaint();

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!running) {
            running = true;
            Thread t = new Thread(this);
            t.start();
        }

        gravity = -10;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void run() {
        while (running) {

            platformNum[1] = platformNum[1] + (int) gravity;

            barrierNum[1] = barrierNum[1] - 1;

            gravity = gravity + .5;

            frame.repaint();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
