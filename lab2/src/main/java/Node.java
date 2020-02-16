import java.io.Console;
import java.io.OutputStream;

public class Node<T>
{
    private final T data;
    private Node<T> next=null;

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node( T value)
    {
        data = value;
    }

    public T getValue()
    { return data; }


}
