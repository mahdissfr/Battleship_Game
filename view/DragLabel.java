package view;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

public class DragLabel extends JPanel {

    public Ships ships;
    public JPanel totalPanel;
    public static boolean rotate = false;

    public DragLabel(JPanel totalPanel) {
        this.totalPanel = totalPanel;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        ships = new Ships(totalPanel, rotate);
        setVisible(true);
        setOpaque(true);
        totalPanel.repaint();
        totalPanel.revalidate();
    }


    public void ready() {
        ships.removeMouseListeners();
    }
    public void editAgain() {
        ships.addMouseListeners();
    }

    public void reset() {
        ships.removeLables(totalPanel);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        ships = new Ships(totalPanel, rotate);
        setVisible(true);
        setOpaque(true);
    }

    public void setPermission(boolean permision){
        rotate=permision;
    }
    private JLabel createColoredLabel(int x, Point origin) {

        JLabel newLabel = new JLabel();
        newLabel.setLocation(origin);
        newLabel.setSize(x, 50);
        newLabel.setOpaque(true);
        newLabel.setBackground(Color.blue);
        newLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        return newLabel;

    }


    public class Ships {
        boolean rotate = false;
        JLabel label4;
        MoveMeMouseHandler handler4;
        JLabel[] label3 = new JLabel[2];
        MoveMeMouseHandler[] handler3;
        JLabel[] label2 = new JLabel[3];
        MoveMeMouseHandler[] handler2;
        JLabel[] label1 = new JLabel[4];
        MoveMeMouseHandler[] handler1;
        private void removeLables(JPanel panel) {
            totalPanel.remove(label4);
            for (int i = 0; i < 2; i++) {
                totalPanel.remove(label3[i]);
            }
            for (int i = 0; i < 3; i++) {
                totalPanel.remove(label2[i]);
            }
            for (int i = 0; i < 4; i++) {
                totalPanel.remove(label1[i]);
            }

            totalPanel.repaint();
            totalPanel.revalidate();
        }

        private void removeMouseListeners() {
            label4.removeMouseListener(handler4);
            for (int i = 0; i < 2; i++) {
                label3[i].removeMouseListener(handler3[i]);
            }
            for (int i = 0; i < 3; i++) {
                label2[i].removeMouseListener(handler2[i]);
            }
            for (int i = 0; i < 4; i++) {
                label1[i].removeMouseListener(handler1[i]);
            }
        }
        private void addMouseListeners() {
            label4.addMouseListener(handler4);
            for (int i = 0; i < 2; i++) {
                label3[i].addMouseListener(handler3[i]);
            }
            for (int i = 0; i < 3; i++) {
                label2[i].addMouseListener(handler2[i]);
            }
            for (int i = 0; i < 4; i++) {
                label1[i].addMouseListener(handler1[i]);
            }
        }

        Ships(JPanel panel, boolean rotate) {
            this.rotate = rotate;
            Point origin;


            handler4 = new MoveMeMouseHandler();
            origin = new Point(10, 710);
            label4 = createColoredLabel(200, origin);
            label4.addMouseListener(handler4);
            label4.addMouseMotionListener(handler4);
            panel.add(label4);


            handler3 = new MoveMeMouseHandler[2];
            for (int i = 0; i < 2; i++) {
                origin = new Point(10, 770);
                label3[i] = createColoredLabel(150, origin);
                handler3[i] = new MoveMeMouseHandler();
                label3[i].addMouseListener(handler3[i]);
                label3[i].addMouseMotionListener(handler3[i]);
                panel.add(label3[i]);
            }

            handler2 = new MoveMeMouseHandler[3];
            for (int i = 0; i < 3; i++) {
                origin = new Point(10, 830);
                label2[i] = createColoredLabel(100, origin);
                handler2[i] = new MoveMeMouseHandler();
                label2[i].addMouseListener(handler2[i]);
                label2[i].addMouseMotionListener(handler2[i]);
                panel.add(label2[i]);
            }

            handler1 = new MoveMeMouseHandler[4];
            for (int i = 0; i < 4; i++) {
                origin = new Point(10, 890);
                label1[i] = createColoredLabel(50, origin);
                handler1[i] = new MoveMeMouseHandler();
                label1[i].addMouseListener(handler1[i]);
                label1[i].addMouseMotionListener(handler1[i]);
                panel.add(label1[i]);
            }

        }
    }


    public class MoveMeMouseHandler extends MouseAdapter {

        private int xOffset;
        private int yOffset;
        private JLabel draggy;



        @Override

        public void mouseReleased(MouseEvent me) {

            if (draggy != null) {
                draggy.setSize(draggy.getWidth(), draggy.getHeight());

                draggy = null;
            }
        }

        public void mousePressed(MouseEvent me) {

            JComponent comp = (JComponent) me.getComponent();

            Component child = comp.findComponentAt(me.getPoint());

            if (child instanceof JLabel) {

                xOffset = me.getX() - child.getX();

                yOffset = me.getY() - child.getY();

                draggy = (JLabel) child;

                if (rotate) {
                    draggy.setSize(draggy.getHeight(), draggy.getWidth());
                    setPermission(false);
                } else draggy.setSize(draggy.getWidth(), draggy.getHeight());
            }
        }


        public void mouseDragged(MouseEvent me) {

            if (draggy != null) {

                draggy.setLocation(me.getX() - xOffset, me.getY() - yOffset);

            }

        }

    }
}
