package main.lab3;

import java.util.Iterator;

public interface SearchTree<Key, Value> extends Iterable<Key> {
    int size();
    Value get( Key key );
    void put( Key key, Value val );
    boolean contains( Key key );
    Iterator<Key> iterator();
}
