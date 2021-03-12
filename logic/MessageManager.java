package logic;

import view.GameBoard;
import view.HitInfo;
import view.HostMode;
import view.PlaymatePanel;

import javax.swing.*;
import java.awt.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
import static view.GameBoard.totalPanel;

public class MessageManager implements NetworkHandler.INetworkHandlerCallback, ServerSocketHandler.IServerSocketHandlerCallback {
    private ServerSocketHandler mServerSocketHandler;
    private List<NetworkHandler> mNetworkHandlerList = new ArrayList<>();
    private NetworkHandler networkHandler;
    private GameBoard board;
    private GameBoard board1;
    private HitInfo hitInfo;
    public static boolean playmateReady=false;

    public MessageManager(int port) {

        mServerSocketHandler = new ServerSocketHandler(port, this, this);
        mServerSocketHandler.start();
    }

    public MessageManager(String ip, int port) {
        InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
        networkHandler = new NetworkHandler(socketAddress, this);
        networkHandler.start();
    }


    public void consumeMessageReceived(MessageReceived message) {
        String s = message.getMessage();
        board.displayMessage(s);

    }

    public void sendMessageReceived(String message) {
        MessageReceived mR = new MessageReceived(message);
        networkHandler.sendMessage(mR);
    }

    public void consumeCoordinationInfo(CoordinationInfo message) {
        hitInfo = new HitInfo(totalPanel);
        hitInfo.listShipsInfo();
        hitInfo.check(new Point(Integer.parseInt(message.getX()), Integer.parseInt(message.getY())));

    }

    public void sendCoordinationInfo(String x, String y) {
        CoordinationInfo info = new CoordinationInfo(x, y);
        networkHandler.sendMessage(info);
    }

    public void sendAcceptConnection(String acceptC) {
        AcceptConnection acceptConnection = new AcceptConnection(acceptC);
        mNetworkHandlerList.get(0).sendMessage(acceptConnection);
        board1 = new GameBoard(this);

    }

    public void consumeAcceptConnection(AcceptConnection message) {
        board = new GameBoard(this);
    }
    public void consumeLogin(Login login){
        System.out.println("consume");
        try {
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HostMode mode = new HostMode();
        mode.addNewLabel(login.getmName(),login.getmId());
    }

    public void sendLogin(String name, String id){
        Login login = new Login(name, id);
        networkHandler.sendMessage(login);
    }
    public void consumeReady(Ready ready) {
playmateReady=true;
    }

    public void sendReady(String ready) {
        Ready ready1 = new Ready(ready);
        networkHandler.sendMessage(ready1);
    }

    public void consumeCancel(Cancel cancel) {
        JOptionPane.showMessageDialog(null, cancel.getCancel(),"cancelation report", JOptionPane.INFORMATION_MESSAGE);

    }

    public void sendCancel(String cancel) {
        Ready cancel1 = new Ready(cancel);
        networkHandler.sendMessage(cancel1);
    }

    public void consumeCoordinationConfirm(CoordinationConfirm confirm) {
        if(confirm.getConfirm().equals("true"))
            hitInfo.changePlaymatePanel(true, PlaymatePanel.pressedLoc);
        else {
            hitInfo.changePlaymatePanel(false, PlaymatePanel.pressedLoc);
            PlaymatePanel.playmatePanel.setVisible(false);
            totalPanel.setVisible(true);
        }
    }

    public void sendCoordinationConfirm(String confirm) {
        Ready confirm1 = new Ready(confirm);
        networkHandler.sendMessage(confirm1);
    }

    @Override
    public void onNewConnectionReceived(NetworkHandler networkHandler) {
        mNetworkHandlerList.add(networkHandler);

    }

    @Override
    public void onMessageReceived(BaseMessage baseMessage) {
        switch (baseMessage.getMessageType()) {
            case MessageTypes.PROTOCOL_VERSION:
                if (MessageTypes.PROTOCOL_VERSION != 1) {
                    System.out.println("Protocols don't match");
                }
                break;
            case MessageTypes.COORDINATION_INFO:
                consumeCoordinationInfo((CoordinationInfo) baseMessage);
                break;
            case MessageTypes.MESSAGE_RECEIVED:
                consumeMessageReceived((MessageReceived) baseMessage);
                break;
            case MessageTypes.ACCEPT_CONNECTION:
                consumeAcceptConnection((AcceptConnection) baseMessage);
                break;
            case MessageTypes.LOGIN:
                consumeLogin((Login) baseMessage);
                break;
            case MessageTypes.Ready:
                consumeReady((Ready) baseMessage);
                break;
            case MessageTypes.CANCEL:
                consumeCancel((Cancel) baseMessage);
                break;
            case MessageTypes.COORDINATION_CONFIRM:
                consumeCoordinationConfirm((CoordinationConfirm) baseMessage);
                break;
        }
    }

    @Override
    public void onSocketClosed() {
        JOptionPane.showMessageDialog(null,"Socket Closed");
    }

}
