package logic;

public abstract class BaseMessage  {
    protected byte [] mSerialized;

    protected abstract void serialize();

    protected abstract void deSerialize();

    public abstract byte getMessageType();

    public byte[] getSerialized(){
        return mSerialized;
    }
}
