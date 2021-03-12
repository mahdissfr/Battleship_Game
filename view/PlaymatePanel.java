package view;

import logic.MessageManager;
import model.ConversationGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static view.GameBoard.totalPanel;
import static view.HitInfo.playAgain;

/**
 * Created by mahdis on 7/9/2017.
 */
public class PlaymatePanel {
    private final JTextArea displayArea;
    public static JPanel playmatePanel;
    public static Point pressedLoc;

    public PlaymatePanel(final MessageManager manager) {
        playmatePanel = new JPanel();
        playmatePanel.setLayout(null);

        JTextField enterField = new JTextField("Type here :");
        enterField.setEditable(true);
        enterField.addActionListener(
                new ActionListener() {
                    // send message to client
                    public void actionPerformed(ActionEvent event) {
                        manager.sendMessageReceived(enterField.getText());
                        enterField.setText("Type here :");
                    }
                }
        );


        enterField.setLocation(900, 0);
        enterField.setSize(499, 40);
        playmatePanel.add(enterField);

        displayArea = new JTextArea();
        new JScrollPane(displayArea);
        displayArea.setLocation(900, 40);
        displayArea.setSize(499, 969);
        playmatePanel.add(displayArea);

        JTextArea condition = new JTextArea();
        condition.setBackground(Color.pink);
        condition.setLocation(0, 0);
        condition.setSize(899, 40);
        playmatePanel.add(condition);
        createGameField(playmatePanel);


        JLabel myName = new JLabel();
        myName.setText("You");
        myName.setLocation(150, 700);
        myName.setSize(90, 60);
        myName.setFont(new Font("Courier New", Font.ITALIC, 20));

        String name = "harif";
        JLabel otherName = new JLabel();
        otherName.setText(name);
        otherName.setLocation(400, 700);
        otherName.setSize(90, 60);
        otherName.setFont(new Font("Courier New", Font.ITALIC, 20));
        playmatePanel.add(myName);
        playmatePanel.add(otherName);


        playmatePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressedLoc = new Point(e.getX(), e.getY());
                manager.sendCoordinationInfo(e.getX() + "", e.getY() + "");
            }
        });


        JButton leaveButton = new JButton("Leave");
        leaveButton.setLocation(805, 880);
        leaveButton.setSize(70, 50);
        playmatePanel.add(leaveButton);

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        playmatePanel.setVisible(true);
//        add(playmatePanel);
//        setVisible(true);
    }

    public void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        displayArea.append(messageToDisplay);
                    }
                }
        );
    }

    private void createGameField(JPanel basePanel) {
        Square[][] board = new Square[10][10];
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10, 10, 0, 0));
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                board[row][column] = new Square(" ", row * 10 + column);
                boardPanel.add(board[row][column]);
            }
        }

        JPanel panel = new JPanel();
        panel.add(boardPanel);
        panel.setLocation(150, 150);
        panel.setSize(515, 515);
        basePanel.add(panel);
    }

}

