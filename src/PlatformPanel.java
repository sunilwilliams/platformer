import javax.swing.*;
import java.awt.*;

public class PlatformPanel extends JPanel {

    int[] platformNum;
    int x;
    int y;
    int width;
    int height;
    //Color color = Color.BLUE;



    public PlatformPanel(int[] in) {

        platformNum = in;

    }

    public void paintComponent(Graphics g) {
        x = platformNum[0];
        y = platformNum[1];
        width = platformNum[2];
        height = platformNum[3];

        super.paintComponent(g);

        g.setColor(Color.BLUE);

        g.fillRect(x, y, width, height);



        //g.fillRect(200, 200, 50, 50);


    }


}
