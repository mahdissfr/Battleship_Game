package view;

import logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mahdis on 7/9/2017.
 */
public class GuestMode extends JFrame{
    public GuestMode(String port, String ip, String name) {
        setSize(500, 300);
        JLabel label = new JLabel();
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(1);
            }
        });
        label.setOpaque(true);
        label.setForeground(Color.red);
        label.setText("     Waiting for the host to join...  ");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        FlowLayout layout = new FlowLayout();
        label.setLayout(layout);
        layout.setAlignment(FlowLayout.CENTER);
        add(label);
        setVisible(true);
        MessageManager manager = new MessageManager(ip, Integer.parseInt(port));
        manager.sendLogin(name,ip);
    }
}
