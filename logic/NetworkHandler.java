package logic;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;


public class NetworkHandler extends Thread {
    private TcpChannel mTcpChannel;
    private Queue<byte[]> mSendQueue;
    private Queue<byte[]> mReceivedQueue;
    private ReceivedMessageConsumer mConsumerThread;
    private INetworkHandlerCallback networkHandlerCallback;
    private boolean stop = true;

    public NetworkHandler(SocketAddress socketAddress, INetworkHandlerCallback iNetworkHandlerCallback) {
        networkHandlerCallback = iNetworkHandlerCallback;
        mTcpChannel = new TcpChannel(socketAddress, 300);
        mSendQueue = new LinkedList<>();
        mReceivedQueue = new LinkedList<>();
        mConsumerThread = new ReceivedMessageConsumer();
        mConsumerThread.start();

    }

    public NetworkHandler(Socket socket, INetworkHandlerCallback iNetworkHandlerCallback) {
        networkHandlerCallback = iNetworkHandlerCallback;
        mTcpChannel = new TcpChannel(socket, 300);
        mSendQueue = new LinkedList<>();
        mReceivedQueue = new LinkedList<>();
        mConsumerThread = new ReceivedMessageConsumer();
        mConsumerThread.start();
    }

//    @Override
//    public synchronized void start() {
//        super.start();
//        mConsumerThread.start();
//    }

    public void sendMessage(BaseMessage baseMessage) {
        mSendQueue.add(baseMessage.getSerialized());
    }

    @Override
    public void run() {
        byte[] b;
        while (mTcpChannel.isConnected()&&!isInterrupted()&&stop) {
            if (!mSendQueue.isEmpty()) {
                mTcpChannel.write(mSendQueue.poll());
            } else if ((b=readChannel())!=null){
                mReceivedQueue.add(b);
            }
        }
    }

    public void stopSelf() {
        stop = false;
        mTcpChannel.closeChannel();
        this.interrupt();
        //networkHandlerCallback.onSocketClosed();

    }

    private byte[] readChannel() {
        byte[] lenBytes = mTcpChannel.read(4);
        if (lenBytes!=null) {
            int len = ByteBuffer.wrap(lenBytes).getInt();
            byte[] bytes = mTcpChannel.read(len - 4);
            ByteBuffer byteBuffer = ByteBuffer.allocate(len);
            byteBuffer.put(lenBytes);
            byteBuffer.put(bytes);
            return byteBuffer.array();
        }
        return null;
    }

    private class ReceivedMessageConsumer extends Thread {
        @Override
        public void run() {
            while (mTcpChannel.isConnected()&&!interrupted()&&stop) {
                if (!mReceivedQueue.isEmpty()) {
                    byte[] message = mReceivedQueue.poll();
                    switch (message[5]) {
                        case MessageTypes.MESSAGE_RECEIVED:
                            MessageReceived mR = new MessageReceived(message);
                            networkHandlerCallback.onMessageReceived(mR);
                            break;
                        case MessageTypes.ACCEPT_CONNECTION:
                            AcceptConnection aC = new AcceptConnection(message);
                            networkHandlerCallback.onMessageReceived(aC);
                            break;
                        case MessageTypes.COORDINATION_INFO:
                            CoordinationInfo cI = new CoordinationInfo(message);
                            networkHandlerCallback.onMessageReceived(cI);
                            break;
                        case MessageTypes.LOGIN:
                            Login login = new Login(message);
                            networkHandlerCallback.onMessageReceived(login);
                            break;
                        case MessageTypes.Ready:
                            Ready ready = new Ready(message);
                            networkHandlerCallback.onMessageReceived(ready);
                            break;
                        case MessageTypes.CANCEL:
                            Cancel cancel = new Cancel(message);
                            networkHandlerCallback.onMessageReceived(cancel);
                            break;
                        case MessageTypes.COORDINATION_CONFIRM:
                            CoordinationConfirm confirm = new CoordinationConfirm(message);
                            networkHandlerCallback.onMessageReceived(confirm);
                            break;
                    }
                }
                else
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public interface INetworkHandlerCallback {
        void onMessageReceived(BaseMessage baseMessage);

        void onSocketClosed();
    }
}
