package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by mahdis on 7/8/2017.
 */
public class Square extends JPanel {
    private String mark;
    private int location;


    public Square(String mark, int location) {
        this.mark = mark;
        this.location = location;
    }

    public Dimension getPreferredSize() {
        return new Dimension(51, 51);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void setMark(String newMark) {
        mark = newMark;
        repaint();
    }

    public int getSquareLocation() {
        return location;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, 50, 50);
        g.drawString(mark, 11, 20);
    }
}
