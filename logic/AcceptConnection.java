package logic;

import java.nio.ByteBuffer;

/**
 * Created by mahdis on 7/8/2017.
 */
public class AcceptConnection extends BaseMessage{
    private String accept;

    public AcceptConnection(String accept){
        this.accept = accept;
        serialize();
    }

    public AcceptConnection(byte[] serialized){
        mSerialized = serialized;
        deSerialize();
    }

    @Override
    protected void serialize() {
        int acceptLength = accept.getBytes().length;
        int wholeMessageLength = 4 + 1 + 1 + 4 + acceptLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(wholeMessageLength);
        byteBuffer.putInt(wholeMessageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ACCEPT_CONNECTION);
        byteBuffer.putInt(acceptLength);
        byteBuffer.put(accept.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deSerialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int wholeMessageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        int nameLength = byteBuffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        byteBuffer.get(nameBytes);
        accept = new String(nameBytes);
    }


    public String getAccept(){
        return accept;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.ACCEPT_CONNECTION;
    }
}
