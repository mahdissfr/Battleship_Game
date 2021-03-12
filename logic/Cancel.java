package logic;

import java.nio.ByteBuffer;

/**
 * Created by mahdis on 7/10/2017.
 */
public class Cancel  extends BaseMessage {
    private String cancel;

    public Cancel(String cancel){
        this.cancel = cancel;
        serialize();
    }

    public Cancel(byte[] serialized){
        mSerialized = serialized;
        deSerialize();
    }

    @Override
    protected void serialize() {
        int acceptLength = cancel.getBytes().length;
        int wholeMessageLength = 4 + 1 + 1 + 4 + acceptLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(wholeMessageLength);
        byteBuffer.putInt(wholeMessageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ACCEPT_CONNECTION);
        byteBuffer.putInt(acceptLength);
        byteBuffer.put(cancel.getBytes());
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
        cancel = new String(nameBytes);
    }


    public String getCancel(){
        return cancel;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.CANCEL;
    }
}
