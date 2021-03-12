package logic;

import java.nio.ByteBuffer;

/**
 * Created by mahdis on 7/10/2017.
 */
public class CoordinationConfirm extends BaseMessage {
    private String confirm;

    public CoordinationConfirm(String confirm) {
        this.confirm = confirm;
        serialize();
    }

    public CoordinationConfirm(byte[] serialized) {
        mSerialized = serialized;
        deSerialize();
    }

    @Override
    protected void serialize() {
        int acceptLength = confirm.getBytes().length;
        int wholeMessageLength = 4 + 1 + 1 + 4 + acceptLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(wholeMessageLength);
        byteBuffer.putInt(wholeMessageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.ACCEPT_CONNECTION);
        byteBuffer.putInt(acceptLength);
        byteBuffer.put(confirm.getBytes());
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
        confirm = new String(nameBytes);
    }


    public String getConfirm() {
        return confirm;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.COORDINATION_CONFIRM;
    }
}
