package view;

import logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mahdis on 7/7/2017.
 */
public class HitInfo {
    JPanel totalPanel;
    JLabel[] myLabel1 = new JLabel[4];
    JLabel[] othersLabel1 = new JLabel[4];
    JLabel[] myLabel2 = new JLabel[6];
    JLabel[] othersLabel2 = new JLabel[6];
    JLabel[] myLabel3 = new JLabel[6];
    JLabel[] othersLabel3 = new JLabel[6];
    JLabel[] myLabel4 = new JLabel[4];
    JLabel[] othersLabel4 = new JLabel[4];
    ArrayList<JLabel> allLabels;
    String myMessage, playmateMessage;
    int counter1 = -1, counter2 = -1, counter3 = -1, counter4 = -1;
    public static boolean playAgain;
    Point sendingCoord;
    boolean success;

    public HitInfo(JPanel totalPanel) {
        this.totalPanel = totalPanel;
        allLabels = new ArrayList<>();
    }

    public void listShipsInfo() {
        for (Component jc : totalPanel.getComponents()) {
            if (jc instanceof JLabel) {
                if (jc.getWidth() == 50 || jc.getHeight() == 50)
                    allLabels.add((JLabel) jc);
            }
        }
    }

    public void check(Point mousePressedLoc) {
        Point squareLoc = null;
        for (JLabel eachShip : allLabels) {
            for (int i = 1; i < eachShip.getWidth() / 50; i++) {
                if ((eachShip.getX() + (i - 1) * 50) < mousePressedLoc.getX() && mousePressedLoc.getX() < eachShip.getX() + i * 50)
                    for (int j = 1; j < eachShip.getWidth() / 50; j++) {
                        if ((eachShip.getY() + (j - 1) * 50) < mousePressedLoc.getY() && mousePressedLoc.getY() < eachShip.getY() + j * 50) {
                            squareLoc = new Point(eachShip.getX() + (i - 1) * 50, eachShip.getY() + (j - 1) * 50);
                            switch (eachShip.getWidth()) {
                                case 200:
                                    counter4++;
                                    break;
                                case 150:
                                    counter3++;
                                    break;
                                case 100:
                                    counter2++;
                                    break;
                                default:
                                    counter1++;
                            }
                        }
                    }
            }
        }

        result(totalPanel, mousePressedLoc, squareLoc,PlaymatePanel.playmatePanel);
    }

    public void sendData(boolean success) {
        GameBoard.manager.sendCoordinationConfirm("," + success);
    }

    private void result(JPanel myPanel, Point mousePressedLoc, Point squareLoc, JPanel otherPanel) {
        JLabel resultSquare = new JLabel();
        if (squareLoc != null) {
            resultSquare.setLocation(squareLoc);
            sendingCoord = squareLoc;
            success = true;
        } else {
            success = false;
            for (int i = 1; i < 10; i++) {
                if ((150 + (i - 1) * 50) < mousePressedLoc.getX() && mousePressedLoc.getX() < (150 + i * 50))
                    for (int j = 1; j < 10; j++) {
                        if ((150 + (j - 1) * 50) < mousePressedLoc.getY() && mousePressedLoc.getY() < 150 + j * 50) {
                            sendingCoord = new Point(150 + (i - 1) * 50, 150 + (j - 1) * 50);
                            resultSquare.setLocation(sendingCoord);
                            sendData(success);
                        }
                    }
            }
        }
        resultSquare.setSize(50, 50);
        if (squareLoc != null) {
            resultSquare.setBackground(Color.RED);
        } else resultSquare.setBackground(Color.lightGray);
        resultSquare.setOpaque(true);
        resultSquare.setBorder(BorderFactory.createLineBorder(Color.black));
        myPanel.add(resultSquare);
        changeSamples();
        if(squareLoc==null){
            waitTime(100000);
            myPanel.setVisible(false);
            otherPanel.setVisible(true);
        }


        if (checkForFinish()) {
            myMessage = "Game Over";
            showEndingMessage();
        }
    }

    public void changePlaymatePanel(boolean playmateSuccess,Point mousePressedLoc) {

        JLabel resultSquare = new JLabel();
        for (int i = 1; i < 10; i++) {
            if ((150 + (i - 1) * 50) < mousePressedLoc.getX() && mousePressedLoc.getX() < (150 + i * 50))
                for (int j = 1; j < 10; j++) {
                    if ((150 + (j - 1) * 50) < mousePressedLoc.getY() && mousePressedLoc.getY() < 150 + j * 50) {
                        resultSquare.setLocation(150 + (i - 1) * 50, 150 + (j - 1) * 50);
                    }
                }
        }
        resultSquare.setSize(50, 50);
        if (playmateSuccess) {
            resultSquare.setBackground(Color.RED);
        } else resultSquare.setBackground(Color.lightGray);
        resultSquare.setOpaque(true);
        resultSquare.setBorder(BorderFactory.createLineBorder(Color.black));
        PlaymatePanel.playmatePanel.add(resultSquare);
    }

    public void showEndingMessage() {
        JLabel myEndingMessage = new JLabel();
        myEndingMessage.setText(myMessage);
        myEndingMessage.setLocation(340, 340);
        myEndingMessage.setSize(200, 200);
        myEndingMessage.setBackground(Color.ORANGE);
        myEndingMessage.setFont(new Font("Courier New", Font.ITALIC, 40));
        myEndingMessage.setForeground(Color.DARK_GRAY);
        totalPanel.add(myEndingMessage);

    }

    public boolean checkForFinish() {
        return (counter1 == 3 && counter2 == 5 && counter3 == 5 && counter4 == 3);
    }

    public void changeSamples() {
        if (counter1 >= -1) {
            myLabel1[counter1].setBackground(Color.RED);
            myLabel1[counter1].setOpaque(true);
        } else if (counter2 >= -1) {
            myLabel2[counter2].setBackground(Color.RED);
            myLabel2[counter2].setOpaque(true);
        } else if (counter3 >= -1) {
            myLabel3[counter3].setBackground(Color.RED);
            myLabel3[counter3].setOpaque(true);
        } else if (counter4 >= -1) {
            myLabel4[counter4].setBackground(Color.RED);
            myLabel4[counter4].setOpaque(true);
        }
    }
    public static void waitTime ( long millisecond){
        long max = millisecond;
        for (long i = 0; i < max; i++) {
            for (long j = 0; j < max; j++) {

            }
        }

    }
    private void createSamples(JPanel jPanel) {
        for (int i = 0; i < 4; i++) {
            myLabel1[i] = new JLabel();
            myLabel1[i].setLocation(150 + i * 25 + i * 15, 750);
            myLabel1[i].setSize(25, 25);
            myLabel1[i].setBackground(Color.blue);
            myLabel1[i].setOpaque(true);
            myLabel1[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(myLabel1[i]);

            othersLabel1[i] = new JLabel();
            othersLabel1[i].setLocation(400 + i * 25 + i * 15, 750);
            othersLabel1[i].setSize(25, 25);
            othersLabel1[i].setBackground(Color.blue);
            othersLabel1[i].setOpaque(true);
            othersLabel1[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(othersLabel1[i]);
        }
        for (int i = 0; i < 6; i++) {
            myLabel2[i] = new JLabel();
            myLabel2[i].setLocation(150 + i * 25 + i * 5 + (i / 2) * 10, 780);
            myLabel2[i].setSize(25, 25);
            myLabel2[i].setBackground(Color.blue);
            myLabel2[i].setOpaque(true);
            myLabel2[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(myLabel2[i]);

            othersLabel2[i] = new JLabel();
            othersLabel2[i].setLocation(400 + i * 25 + i * 5 + (i / 2) * 10, 780);
            othersLabel2[i].setSize(25, 25);
            othersLabel2[i].setBackground(Color.blue);
            othersLabel2[i].setOpaque(true);
            othersLabel2[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(othersLabel2[i]);

        }
        for (int i = 0; i < 6; i++) {
            myLabel3[i] = new JLabel();
            myLabel3[i].setLocation(150 + i * 25 + i * 5 + (i / 3) * 10, 810);
            myLabel3[i].setSize(25, 25);
            myLabel3[i].setBackground(Color.blue);
            myLabel3[i].setOpaque(true);
            myLabel3[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(myLabel3[i]);

            othersLabel3[i] = new JLabel();
            othersLabel3[i].setLocation(400 + i * 25 + i * 5 + (i / 3) * 10, 810);
            othersLabel3[i].setSize(25, 25);
            othersLabel3[i].setBackground(Color.blue);
            othersLabel3[i].setOpaque(true);
            othersLabel3[i].setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.add(othersLabel3[i]);

        }
        for (int i = 0; i < 4; i++) {
            myLabel4[i] = new JLabel();
            myLabel4[i].setLocation(150 + i * 25 + i * 5, 840);
            myLabel4[i].setSize(25, 25);
            myLabel4[i].setBackground(Color.blue);
            myLabel4[i].setOpaque(true);
            myLabel4[i].setBorder(BorderFactory.createLineBorder(Color.black));
            totalPanel.add(myLabel4[i]);

            othersLabel4[i] = new JLabel();
            othersLabel4[i].setLocation(400 + i * 25 + i * 5, 840);
            othersLabel4[i].setSize(25, 25);
            othersLabel4[i].setBackground(Color.blue);
            othersLabel4[i].setOpaque(true);
            othersLabel4[i].setBorder(BorderFactory.createLineBorder(Color.black));
            totalPanel.add(othersLabel4[i]);

        }
    }
}
