package view;

import logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StartMenu extends JFrame{
    private JTextField name;
    private static JTextField hPort;
    private JTextField ip;
    private JTextField gPort;
    private JRadioButton hostButton;
    private JRadioButton guestButton;
    private JButton exitButton;
    private JButton startButton;
    private ButtonGroup radioGroup;
    private JLabel nam;
    private JLabel hp;
    private JLabel gp;
    private JLabel iP;

    public StartMenu(){
        super("Select connection mode");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360,300);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        nam = new JLabel();
        nam.setText("Name");
        nam.setLocation(10,10);
        nam.setSize(35,35);
        name = new JTextField("",25);
        name.setBackground(Color.gray);
        name.setForeground(Color.red);
        name.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        name.setLocation(50,10);
        name.setSize(260,30);
        panel.add(nam);
        panel.add(name);
        hostButton = new JRadioButton("Host", true);
        hostButton.setLocation(150,45);
        hostButton.setSize(60,20);
        panel.add(hostButton);
        hp = new JLabel();
        hp.setText("Port");
        hp.setLocation(10,70);
        hp.setSize(35,35);
        panel.add(hp);
        hPort = new JTextField("", 25);
        hPort.setBackground(Color.gray);
        hPort.setForeground(Color.red);
        hPort.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        hPort.setLocation(50,70);
        hPort.setSize(260,30);
        panel.add(hPort);
        guestButton = new JRadioButton("Guest", false);
        guestButton.setLocation(150,105);
        guestButton.setSize(60,20);
        panel.add(guestButton);
        iP = new JLabel();
        iP.setText("ip");
        iP.setLocation(10,130);
        iP.setSize(35,35);
        panel.add(iP);
        ip = new JTextField("", 25);
        ip.setBackground(Color.gray);
        ip.setForeground(Color.red);
        ip.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        ip.setLocation(50,130);
        ip.setSize(260,30);
        panel.add(ip);
        gp = new JLabel();
        gp.setText("Port");
        gp.setLocation(10,165);
        gp.setSize(35,35);
        panel.add(gp);
        gPort = new JTextField("", 25);
        gPort.setBackground(Color.gray);
        gPort.setForeground(Color.red);
        gPort.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        gPort.setLocation(50,165);
        gPort.setSize(260,30);
        panel.add(gPort);
        radioGroup = new ButtonGroup();
        radioGroup.add(hostButton);
        radioGroup.add(guestButton);
        exitButton = new JButton("Exit");
        exitButton.setLocation(90,200);
        exitButton.setSize(70,30);
        panel.add(exitButton);
        startButton = new JButton("Start");
        startButton.setLocation(170,200);
        startButton.setSize(70,30);
        panel.add(startButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
                if (guestButton.isSelected()) {
                    GuestMode guestMode = new GuestMode(gPort.getText(), ip.getText(),name.getText());
                }
                else if (hostButton.isSelected()){
                    MessageManager manager = new MessageManager(Integer.parseInt(hPort.getText()));
                    HostMode hostMode = new HostMode();
                }
            }
        });
        add(panel);
        setVisible(true);
    }
    public String getName(){
        return name.getText();
    }
    public String getIp(){
        return ip.getText();
    }
    public static String getHPort(){
        return hPort.getText();
    }
    public String getGPort(){
        return gPort.getText();
    }

}
