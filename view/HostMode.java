package view;

import logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mahdis on 7/9/2017.
 */
public class HostMode  {
    private JFrame frame;
    private String port;

    public HostMode(){
        this.port = StartMenu.getHPort();
        frame = new JFrame("Waiting for connections...");
        frame.setSize(400, 800);
        frame.setVisible(true);
    }
    //
//    public void hostFrame() {
//    }
    public void addNewLabel( String name, String ip){
        System.out.println("label");
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(400,150);
        JLabel label = new JLabel();
        label.setSize(400,150);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setText(name+"         "+ip);
        JButton rejectButton = new JButton("Reject");
        rejectButton.setLocation(200,110);
        rejectButton.setSize(80,30);
        JButton acceptButton = new JButton("Accept");
        acceptButton.setLocation(285,110);
        acceptButton.setSize(80,30);
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MessageManager manager = new MessageManager(Integer.parseInt(port));
                manager.onSocketClosed();
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MessageManager manager = new MessageManager(Integer.parseInt(port));
                manager.sendAcceptConnection("accept");
            }
        });
        label.add(rejectButton);
        label.add(acceptButton);
        panel.add(label);
        frame.add(panel);
        frame.revalidate();
    }

    public JFrame getFrame() {
        return frame;
    }
}
