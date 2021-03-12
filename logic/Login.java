package logic;

import java.nio.ByteBuffer;

/**
 * Created by mahdis on 7/9/2017.
 */
public class Login extends BaseMessage{
    private String mName;
    private String mId;

    public Login(String name, String id){
        mName = name;
        mId = id;
        serialize();
    }

    public Login(byte[] serialized){
        mSerialized = serialized;
        deSerialize();
    }

    @Override
    protected void serialize() {
        int nameLength = mName.getBytes().length;
        int idLength = mId.getBytes().length;
        int wholeMessageLength = 4 + 1 + 1 + 4 + nameLength + 4 + idLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(wholeMessageLength);
        byteBuffer.putInt(wholeMessageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.LOGIN);
        byteBuffer.putInt(nameLength);
        byteBuffer.put(mName.getBytes());
        byteBuffer.putInt(idLength);
        byteBuffer.put(mId.getBytes());
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
        mName = new String(nameBytes);
        int idLength = byteBuffer.getInt();
        byte[] idBytes = new byte[idLength];
        byteBuffer.get(idBytes);
        mId = new String(idBytes);
    }


    public String getmName(){
        return mName;
    }
    public String getmId(){
        return mId;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.LOGIN;
    }

}
