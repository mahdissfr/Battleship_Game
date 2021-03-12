package logic;

import java.nio.ByteBuffer;

public class MessageReceived extends BaseMessage{
    private String message;

    public MessageReceived(String message){
        this.message = message;
        serialize();
    }

    public MessageReceived(byte[] serialized){
        mSerialized = serialized;
        deSerialize();
    }

    @Override
    protected void serialize() {
        int messageLength = message.getBytes().length;
        int wholeMessageLength = 4 + 1 + 1 + 4 + messageLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(wholeMessageLength);
        byteBuffer.putInt(wholeMessageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.MESSAGE_RECEIVED);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(message.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deSerialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int wholeMessageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        int messageLength = byteBuffer.getInt();
        byte[] messageBytes = new byte[messageLength];
        byteBuffer.get(messageBytes);
        message = new String(messageBytes);
    }


    public String getMessage(){
        return message;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.MESSAGE_RECEIVED;
    }
}
