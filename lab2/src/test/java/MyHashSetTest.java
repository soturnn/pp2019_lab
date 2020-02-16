import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyHashSetTest {

    @org.junit.jupiter.api.Test
    void size() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        hashSet.add(5);
        hashSet.add(5);
        hashSet.add(1);

        myHashSet.add(5);
        myHashSet.add(5);
        myHashSet.add(1);

        assertEquals(hashSet.size(), myHashSet.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        boolean h1=hashSet.isEmpty()==myHashSet.isEmpty();

        hashSet.add(5);
        myHashSet.add(1);

        boolean h2=hashSet.isEmpty()==myHashSet.isEmpty();
        assertTrue(h1&&h2);

    }

    @org.junit.jupiter.api.Test
    void contains() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        hashSet.add(5);
        hashSet.add(1);

        myHashSet.add(5);
        myHashSet.add(1);

        assertEquals(hashSet.contains(1), myHashSet.contains(1));
        assertEquals(hashSet.contains(4),myHashSet.contains(4));
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        for(int i=0; i<10;i++)
            myHashSet.add((int)(Math.random()*1234)+i);

        for (Integer i: myHashSet)
            hashSet.add(i);

        for (Integer i : myHashSet) {
            assertTrue(hashSet.contains(i));
        }
    }

    @org.junit.jupiter.api.Test
    void toArray() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(5);

        myHashSet.add(6);
        myHashSet.add(8);
        myHashSet.add(2);

        Object[] arrayList1=new Object[2];
        Object[] arrayList2=new Object[2];
        hashSet.toArray(arrayList1);
        myHashSet.toArray(arrayList2);

        assertEquals(arrayList1.length,arrayList2.length);
    }



    @org.junit.jupiter.api.Test
    void add() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);
    }

    @org.junit.jupiter.api.Test
    void remove() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(5);

        myHashSet.add(6);
        myHashSet.add(8);
        myHashSet.add(2);

        hashSet.remove(2);
        myHashSet.remove(2);

        assertEquals(hashSet.contains(2), myHashSet.contains(2));
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);

        myHashSet.add(1);
        myHashSet.add(2);
        myHashSet.add(3);

        ArrayList<Integer> set=new ArrayList<Integer>(3);
        set.add(1);
        set.add(2);
        set.add(3);

        assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));

        hashSet.remove(1);
        myHashSet.remove(2);

        assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));
    }

    @org.junit.jupiter.api.Test
    void addAll() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        ArrayList<Integer> set=new ArrayList<Integer>(3);
        set.add(1);
        set.add(2);
        set.add(3);

        hashSet.addAll(set);
        myHashSet.addAll(set);
        assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        ArrayList<Integer> set=new ArrayList<Integer>(3);
        set.add(1);
        set.add(2);
        set.add(3);

        hashSet.add(2);
        hashSet.add(4);

        myHashSet.add(2);
        myHashSet.add(4);

        assertEquals(hashSet.removeAll(set),myHashSet.removeAll(set));
        assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));
    }

}