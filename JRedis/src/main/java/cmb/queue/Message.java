package cmb.queue;

/**
 * Created by Jun on 2018/8/6.
 */
public class Message<T> {
    public String id;
    public T msg;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", msg=" + msg +
                '}';
    }
}
