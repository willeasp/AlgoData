import java.util.ArrayList;
import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    Item[] stack = new ArrayList<Item>[20];
    int index;

    public Stack() {
    }

    void push(Item item) {
        this.stack[index++] = item;
    }

    Item pop() {
        Item i = this.stack[--index];
        this.stack[index] = null;
        return i;
    }

    boolean isEmpty() {
        for(Item item : this.stack){
            if(item != null)
                return false;
        }
        return true;
    }

    int size() {
        return index;
    }
    //TODO implement the iterator
    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}
