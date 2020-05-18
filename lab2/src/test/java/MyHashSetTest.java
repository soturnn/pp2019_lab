import java.util.*;
import org.junit.Assert;
import org.junit.Test;

public class MyHashSetTest {

    @Test
    public void constructorTest(){
        MyHashSet<Integer> emptyhs= new MyHashSet<>();
        Object[] obj=emptyhs.toArray();
        Assert.assertEquals(obj.length,0);

        LinkedList<Integer> collection= new LinkedList<Integer>();
        for (int i = 0; i < 10; i++)
            collection.add(i);
        MyHashSet<Integer> integerMyHashSet=new MyHashSet<>(collection);
        integerMyHashSet.containsAll(collection);

    }

    @Test
    public void size() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        for (int i = 0; i < 10; i++) {
            int value=(int)(Math.random()*100);
            hashSet.add(value);
            myHashSet.add(value);
        }
        System.out.println(myHashSet.size());
        for (Integer i: myHashSet) {
            System.out.println(i);
        }
        Assert.assertEquals(hashSet.size(), myHashSet.size());
    }

    @Test
    public void isEmpty() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        boolean h1=hashSet.isEmpty()==myHashSet.isEmpty();

        hashSet.add(5);
        myHashSet.add(1);

        boolean h2=hashSet.isEmpty()==myHashSet.isEmpty();
        Assert.assertTrue(h1&&h2);

    }

    @Test
    public void contains() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        hashSet.add(5);
        hashSet.add(1);

        myHashSet.add(5);
        myHashSet.add(1);

        Assert.assertEquals(hashSet.contains(1), myHashSet.contains(1));
        Assert.assertEquals(hashSet.contains(4),myHashSet.contains(4));
    }

    @Test
    public void iterator() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        for(int i=0; i<25;i++)
            myHashSet.add(i);

        for (Integer i: myHashSet)
            hashSet.add(i);

        for (int i=0;i<25;i++)
            Assert.assertTrue(hashSet.contains(i));

    }

    @Test
    public void toArray() {
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

        Assert.assertEquals(arrayList1.length,arrayList2.length);
    }

    @Test
    public void remove() {
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

        Assert.assertEquals(hashSet.contains(2), myHashSet.contains(2));
    }

    @Test
    public void containsAll() {
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

        Assert.assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));

        hashSet.remove(1);
        myHashSet.remove(2);

        Assert.assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));
    }

    @Test
    public void addAll() {
        HashSet<Integer> hashSet=new HashSet<Integer>(4);
        MyHashSet<Integer> myHashSet=new MyHashSet<Integer>(4);

        ArrayList<Integer> set=new ArrayList<Integer>(3);
        set.add(1);
        set.add(2);
        set.add(3);

        hashSet.addAll(set);
        myHashSet.addAll(set);
        Assert.assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));
    }

    @Test
    public void removeAll() {
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

        Assert.assertEquals(hashSet.removeAll(set),myHashSet.removeAll(set));
        Assert.assertEquals(hashSet.containsAll(set),myHashSet.containsAll(set));
    }

}