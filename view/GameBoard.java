package view;


import logic.MessageManager;
import model.ConversationGui;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;

/**
 * Created by elahe ranjbari on 6/30/2017.
 */
public class GameBoard extends JFrame {
    private Square[][] board;
    private JPanel boardPanel;
    private JTextField enterField;
    private JTextArea displayArea;
    private JTextArea condition;
    private JMenuBar jMenuBar;
    private JMenu jMenu;
    private JMenu jMenu1;
    private JMenuItem jMenuItem;
    private JPanel jPanel;
    private JPanel jPanel1;
    private JButton leaveButton;
    private JButton resetButton;
    private JButton readyButton;
    JButton rotateButton;
    public static JPanel totalPanel;
    private JPanel conditionPanel;
    JPanel panel;
    public static PlaymatePanel playmate;
    public static MessageManager manager;

    public GameBoard(final MessageManager manager) {
        super("BattleShip");
        this.manager = manager;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        jMenu = new JMenu("File");
        jMenu.setMnemonic(KeyEvent.VK_F);
        jMenu1 = new JMenu("Help");
        jMenu1.setMnemonic(KeyEvent.VK_F);
        jMenuItem = new JMenuItem("Conversations History", KeyEvent.VK_N);
        jMenu.add(jMenuItem);
        jMenuBar.add(jMenu);
        jMenuBar.add(jMenu1);
        MenuItemHandler handler = new MenuItemHandler();
        jMenuItem.addActionListener(handler);
        totalPanel = new JPanel();
        totalPanel.setLayout(null);

        DragLabel dragLabel = new DragLabel(totalPanel);


        enterField = new JTextField("Type here :");
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
        totalPanel.add(enterField);

        displayArea = new JTextArea();
        new JScrollPane(displayArea);
        displayArea.setLocation(900, 40);
        displayArea.setSize(499, 969);
        totalPanel.add(displayArea);


        condition = new JTextArea();
        condition.setBackground(Color.pink);
        condition.setLocation(0, 0);
        condition.setSize(899, 40);
        totalPanel.add(condition);


        createGameField(totalPanel);

        totalPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (e.isControlDown() && e.getKeyChar() != 'r' && e.getKeyCode() == 82) {

                    dragLabel.setPermission(true);
                }
            }
        });

        totalPanel.setFocusable(true);
        totalPanel.requestFocusInWindow();

        rotateButton = new JButton("Rotate");
        rotateButton.setLocation(773, 820);
        rotateButton.setSize(100, 50);
        totalPanel.add(rotateButton);
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dragLabel.setPermission(true);

            }
        });


        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dragLabel.reset();
                totalPanel.remove(panel);
                createGameField(totalPanel);
                totalPanel.repaint();
                totalPanel.revalidate();
            }
        });
        resetButton.setLocation(730, 880);
        resetButton.setSize(70, 50);
        totalPanel.add(resetButton);
        readyButton = new JButton("Ready");
        readyButton.setLocation(805, 880);
        readyButton.setSize(70, 50);
        totalPanel.add(readyButton);
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dragLabel.ready();
                totalPanel.remove(rotateButton);
                totalPanel.remove(readyButton);
                totalPanel.remove(resetButton);
                totalPanel.repaint();
                totalPanel.revalidate();

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

                totalPanel.add(myName);
                totalPanel.add(otherName);


                HitInfo hitInfo = new HitInfo(totalPanel);
                hitInfo.listShipsInfo();

                leaveButton = new JButton("Leave");
                leaveButton.setLocation(805, 880);
                leaveButton.setSize(70, 50);
                totalPanel.add(leaveButton);
                totalPanel.repaint();
                totalPanel.revalidate();

                leaveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        System.exit(0);
                    }
                });
                while (!MessageManager.playmateReady)
                {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setLocation(730, 880);
                cancelButton.setSize(70, 50);

                totalPanel.add(cancelButton);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        dragLabel.editAgain();
                        manager.sendCancel("your playmate cancelled his preparation"); }
                });
                totalPanel.remove(cancelButton);
                totalPanel.repaint();
                totalPanel.revalidate();

                }
                playmate = new PlaymatePanel(manager);
                switchPanels(totalPanel, PlaymatePanel.playmatePanel);
            }
        });
        totalPanel.setVisible(true);
        add(totalPanel);
        setVisible(true);
    }

    public void switchPanels(JPanel previousPanel, JPanel otherPanel) {
        previousPanel.setVisible(false);
        add(otherPanel);
        otherPanel.setVisible(true);
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
        board = new Square[10][10];
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10, 10, 0, 0));
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                board[row][column] = new Square(" ", row * 10 + column);
                boardPanel.add(board[row][column]);
            }
        }

        panel = new JPanel();
        panel.add(boardPanel);
        panel.setLocation(150, 150);
        panel.setSize(515, 515);
        basePanel.add(panel);
    }

    public class MenuItemHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == jMenuItem) {
                ConversationGui gui = new ConversationGui();
                pack();
            }
        }
    }
}
