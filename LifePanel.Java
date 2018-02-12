import javax.swing.*;
import java.awt.*;

public class LifePanel extends JPanel{
    boolean[][] cells;
    double width;
    double height;

    public LifePanel(boolean[][] in){
        cells = in;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = (double)this.getWidth() / cells.length;
        height = (double)this.getHeight() / cells[0].length;

        //Draws rectangles!
        g.setColor(Color.BLUE);
        for (int column = 0; column < cells[0].length; column++) {
            for (int row = 0; row < cells.length; row++) {
                if (cells[row][column] == true){
                    g.fillRect((int)Math.round(column*width),(int)Math.round(row*height),(int)width + 1,(int)height + 1);
                }
            }
        }


        g.setColor(Color.BLACK);
        //Draws vertical lines:
        for (int x = 0; x < cells.length; x++) {
            g.drawLine((int)Math.round(x*width),0,(int)Math.round(x*width),this.getHeight());
        }
        //Draws horizontal lines
        for (int y = 0; y < cells.length; y++) {
            g.drawLine(0,(int)Math.round(y*height),this.getWidth(),(int)Math.round(y*height));
        }
    }


}
