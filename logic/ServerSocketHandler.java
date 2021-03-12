package logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketHandler extends Thread {
    ServerSocket serverSocket;
    NetworkHandler.INetworkHandlerCallback networkHandlerCallback;
    IServerSocketHandlerCallback serverSocketHandlerCallback;
    Socket socket;

    public ServerSocketHandler(int port, NetworkHandler.INetworkHandlerCallback iNetworkHandlerCallback, IServerSocketHandlerCallback iServerSocketHandlerCallback){
        networkHandlerCallback = iNetworkHandlerCallback;
        serverSocketHandlerCallback = iServerSocketHandlerCallback;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            NetworkHandler networkHandler = new NetworkHandler(socket, networkHandlerCallback);
            networkHandler.start();
            serverSocketHandlerCallback.onNewConnectionReceived(networkHandler);
        }
    }

    public void stopSelf(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface IServerSocketHandlerCallback {
        void onNewConnectionReceived(NetworkHandler networkHandler);
    }
}
