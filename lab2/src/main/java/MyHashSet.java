import java.util.Collection;
import java.util.Set;
import java.util.Iterator;

public class MyHashSet<T> implements Set<T>, Iterable<T> {

    private int numberOfElements = 0;
    private int lenght = 2;
    private Object[] data;

    public MyHashSet()   {
        data=  new Object[lenght];
    }

    public MyHashSet(int _lenght){
       this.lenght=_lenght;
        data=  new Object[lenght];
    }

    public MyHashSet(Collection<T> c){
        lenght=(int)(c.size()/0.75)+2;
        data=  new Object[lenght];
        this.addAll(c);
    }

    protected int hashFunction(T value){
        int sum = 0;
        String valueString = value.toString();
        return valueString.hashCode() % lenght;
    }

    private void rebuilding(int newLenght){
        MyHashSet<T> set= new MyHashSet<T>(newLenght);
        for (T t: this) {
            set.add(t);
        }
        data=set.data;
        lenght=newLenght;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    @Override
    public boolean contains(Object o) {
        int h=hashFunction((T)o);
        Node<T> ptr=(Node<T>) data[h];
        while (ptr!=null){
            if(ptr.getValue().equals((T)o))
                return true;
            ptr=ptr.getNext();
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int index=-1;
            private Node<T> currentNode=(Node<T>) data[0];

            @Override
            public boolean hasNext() {
                if (index==-1) {
                    index++;
                    while ((currentNode == null)&&(index<lenght))
                    {
                        index++;
                        currentNode = (Node<T>)data[index];
                    }
                }
                else {
                    if (index==lenght - 1)
                        return currentNode.getNext() != null;
                    Node<T> nextNode=currentNode.getNext();
                    int nextIndex=index+1;
                    while ((nextNode == null) && (nextIndex< lenght))
                    {
                        nextNode= (Node<T>) data[nextIndex];
                        if (nextNode!= null)
                            break;
                        nextIndex++;
                    }
                    return nextIndex<lenght;
                }
                 return index < lenght;
            }

            @Override
            public T next() {
                T returnValue=currentNode.getValue();
                currentNode=currentNode.getNext();
                while ((currentNode == null) && (index < lenght)) {
                    index++;
                    currentNode= (Node<T>)data[index];
                    if (currentNode!= null)
                        break;
                }
                return returnValue;
            }

            @Override
            public void remove() {

            }
        };

    }

    @Override
    public Object[] toArray() {
        Object[] array=new Object[numberOfElements];

        int i=0;
        for (T element: this) {
            array[i]=element;
            i++;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if(a.length<numberOfElements)
            a=(T1[])new Object[numberOfElements];

        int i=0;
        for (T element: this) {
            a[i]=(T1)element;
            i++;
        }
        return a;
    }

    @Override
    public boolean add(T t) {

        if((float)(numberOfElements+1)/lenght>0.75)
            rebuilding((int) Math.floor(0.75 * numberOfElements) + 2);
        if(!this.contains(t))
            numberOfElements++;

        int h = hashFunction(t);
        Node<T> ptr = (Node<T>)data[h];
        if (ptr == null) {
            data[h]=new Node<T>(t);
            return true;
        }
        while (ptr != null) {
            if (ptr.getValue().equals(t))
                return false;
            if(ptr.getNext()==null) {
                ptr.setNext(new Node<T>(t));
                return true;
            }
            ptr=ptr.getNext();
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {

        int h = hashFunction((T)o);
        boolean finded = false, first = true;
        Node<T> ptr = (Node<T>)data[h];
        T value=null;

        if (ptr != null)
            if (ptr.getValue().equals((T)o))
                finded = true;
            else {
                first = false;
                while (ptr.getNext() != null) {
                    if (ptr.getNext().getValue().equals((T)o)) {
                        finded = true;
                        break;
                    }
                    ptr = ptr.getNext();
                }
            }

        if (finded) {
            numberOfElements--;
            if (first)
                data[h] = ptr.getNext();
            else
                ptr.setNext(ptr.getNext().getNext());
            return  true;

        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T element: c) {
            this.add(element);
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int removed=0;
        for (T t: this ){
            if(!c.contains(t)) {
                this.remove(t);
                removed++;
            }
        }
        return removed>0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int removed=0;
        for (Object obj: c ){
            if(this.contains(obj)) {
                this.remove(obj);
                removed++;
            }
        }
        return removed>0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < lenght; i++)
            data[i]=null;
        numberOfElements=0;

    }
}