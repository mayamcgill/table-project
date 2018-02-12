//Conway's Game of Life code for Java swing
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Life implements MouseListener, ActionListener, Runnable {
    //Determines board dimensions:
    int boardWidth = 20;
    int boardHeight = 20;

    boolean[][] cells = new boolean[boardHeight][boardWidth];
    JFrame frame = new JFrame("Conway's Game of Life");
    LifePanel panel = new LifePanel(cells);
    Container buttons = new Container();
    JButton step = new JButton("Step");
    JButton run = new JButton("Run");
    JButton stop = new JButton("Stop");
    boolean running =  false;


    public static void main(String[] args) {
        new Life();
    }

    public Life() {
        //Actual window size:
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);

        buttons.setLayout(new GridLayout(1,3));
        buttons.add(step);
        step.addActionListener(this);
        buttons.add(run);
        run.addActionListener(this);
        buttons.add(stop);
        stop.addActionListener(this);

        frame.add(buttons, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //What the code does when the step button is pushed
    public void step(){
        int neighborCount[][] = new int[boardHeight][boardWidth];
        boolean change[][] = new boolean[boardHeight][boardWidth];
        for (int column = 0; column < cells[0].length; column++) {
            for (int row = 0; row < cells.length; row++) {
                change[row][column] = cells[row][column];
            }
        }
        //counts and saves the number of neighbors each cell has
        for (int column = 0; column < cells[0].length; column++) {
            for (int row = 0; row < cells.length; row++) {
                int neighbors = 0;

                if (row-1 >= 0){
                    if (cells[row - 1][column]) neighbors++;
                    if (column-1 >= 0){
                        if (cells[row - 1][column - 1]) neighbors++;
                        if (cells[row][column - 1]) neighbors++;
                    }
                    if (column + 1 < cells[0].length){
                        if (cells[row - 1][column + 1]) neighbors++;
                    }
                }
                if ((row + 1) < cells.length){
                    if (cells[row + 1][column]) neighbors++;
                    if (column + 1 < cells[0].length){
                        if (cells[row + 1][column + 1]) neighbors++;
                        if (cells[row][column + 1]) neighbors++;
                    }
                    if (column - 1 >= 0){
                        if (cells[row + 1][column - 1]) neighbors++;
                    }
                }
                neighborCount[row][column] = neighbors;
            }

        }
        //Changes the array for the next turn based on number of neighbors
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length ; column++) {
                if (cells[row][column] = true){
                   if (neighborCount[row][column] == 0) {
                        change[row][column] = false;
                    }
                    else if (neighborCount[row][column] == 1) {
                        change[row][column] = false;
                    }
 /*                   else if (neighborCount[row][column] == 2){
                        change[row][column] = true;
                    }*/
                    else if (neighborCount[row][column] == 3){
                        change[row][column] = true;
                    }
                    else if (neighborCount[row][column] == 4){
                        change[row][column] = false;
                    }
                    else if (neighborCount[row][column] > 4){
                        change[row][column] = false;
                    }
                }
                else if (cells[row][column] = false){
                    if (neighborCount[row][column] == 3){
                        change[row][column] = true;
                    }
                }
            }
        }
        for (int column = 0; column < cells[0].length; column++) {
            for (int row = 0; row < cells.length; row++) {
                cells[row][column] = change[row][column];
            }
        }
        frame.repaint();
    }




    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //draws cells on the board
        double width = (double)panel.getWidth() / cells[0].length;
        double height = (double)panel.getHeight() / cells.length;
        int column = Math.min((cells[0].length - 1),(int)(e.getX() / width));
        int row = Math.min((cells.length - 1),(int)(e.getY() / height));
        cells[row][column] = !cells[row][column];
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(step)){
            step();
        }
        else if (e.getSource().equals(run)){
            if (running == false) {
                running = true;
                Thread runThread = new Thread(this);
                runThread.start();
            }
        }
        else if (e.getSource().equals(stop)){
            running = false;
        }
    }

    @Override
    public void run() {
        while (running == true) {
            step();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
