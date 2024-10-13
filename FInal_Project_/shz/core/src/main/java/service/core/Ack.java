package service.core;

public class Ack<T> {
    T object;
    boolean ack;

    public Ack(){};

    public Ack(T object, boolean ack){
        this.object = object;
        this.ack = ack;
    }

    public T getObject(){
        return object;
    }

    public void setObject(T object){
        this.object = object;
    }

    public boolean getAck(){
        return ack;
    }

    public void setAck(boolean ack){
        this.ack = ack;
    }
}
