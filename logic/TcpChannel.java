package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;


public class TcpChannel  {
    private Socket mSocket;
    private OutputStream mOutputSteam;
    private InputStream mInputStream;

    public TcpChannel(SocketAddress socketAddress, int timeout){
        mSocket = new Socket();
        try {
            mSocket.connect(socketAddress);
            mSocket.setSoTimeout(timeout);
            mInputStream = mSocket.getInputStream();
            mOutputSteam = mSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("");
        }

    }

    public TcpChannel(Socket socket, int timeout){
        mSocket = socket;
        try {
            mSocket.setSoTimeout(timeout);
            mInputStream = mSocket.getInputStream();
            mOutputSteam = mSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("");
        }
    }

    public byte[] read(final int count){
        byte[] inputByte = new byte[count];
        if (count>0) {
            try {
                int read = mInputStream.read(inputByte);
            } catch (IOException e) {
                System.out.println("");
                return null;
            }
        }

        return inputByte;
    }

    public void write(byte[] data){
        try {
            mOutputSteam.write(data);
            mOutputSteam.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnected(){
        return  mSocket.isConnected();
    }

    public void closeChannel(){
        try {
            mSocket.close();
            mInputStream.close();
            mOutputSteam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
