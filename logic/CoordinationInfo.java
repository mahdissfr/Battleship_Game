package logic;

import java.nio.ByteBuffer;

public class CoordinationInfo extends BaseMessage{
    private String mX;
    private String mY;

    public CoordinationInfo(String x, String y){
        mX = x;
        mY = y;
        serialize();
    }

    public CoordinationInfo(byte[] serialized){
        mSerialized = serialized;
        deSerialize();
    }

    @Override
    protected void serialize() {
        int xLength = mX.getBytes().length;
        int yLength = mY.getBytes().length;
        int wholeMessageLength = 4 + 1 + 1 + 4 + xLength + 4 + yLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(wholeMessageLength);
        byteBuffer.putInt(wholeMessageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.COORDINATION_INFO);
        byteBuffer.putInt(xLength);
        byteBuffer.put(mX.getBytes());
        byteBuffer.putInt(yLength);
        byteBuffer.put(mY.getBytes());
        mSerialized = byteBuffer.array();
    }

    @Override
    protected void deSerialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(mSerialized);
        int wholeMessageLength = byteBuffer.getInt();
        byte protocolVersion = byteBuffer.get();
        byte messageType = byteBuffer.get();
        int xLength = byteBuffer.getInt();
        byte[] xBytes = new byte[xLength];
        byteBuffer.get(xBytes);
        mX = new String(xBytes);
        int yLength = byteBuffer.getInt();
        byte[] yBytes = new byte[yLength];
        byteBuffer.get(yBytes);
        mY = new String(yBytes);
    }


    public String getX(){
        return mX;
    }
    public String getY(){
        return mY;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.COORDINATION_INFO;
    }
}
