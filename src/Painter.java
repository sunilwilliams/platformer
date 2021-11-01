import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Painter implements MouseListener {


    JFrame frame = new JFrame("Painter");
    JLabel[][] pixels = new JLabel[10][10];


    public static void main(String[] args) {
        new Painter();
    }

    public Painter() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.addMouseListener(this);

        int width = (int)(frame.getWidth() / pixels.length);
        int height = (int)(frame.getHeight() / pixels[0].length);



        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                pixels[x][y] = new JLabel();
                pixels[x][y].setBackground(Color.RED);
                pixels[x][y].setOpaque(true);
                pixels[x][y].setBounds(x * width, y * height, width, height);
                pixels[x][y].addMouseListener(this);
                frame.add(pixels[x][y]);
            }
        }

        pixels[5][0].setBackground(Color.BLACK);

        frame.setVisible(true);



    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (int)(frame.getWidth() / e.getX());
        int y = (int)(frame.getHeight() / e.getY());
        //pixels[(int)(frame.getWidth() / e.getX())][(int)(frame.getHeight() / e.getY())].setBackground(null);

        System.out.println(e.getX() + ", " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
